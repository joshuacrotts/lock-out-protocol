package com.dsd.game.enemies;

import com.dsd.game.core.Game;
import com.dsd.game.objects.items.Coin;
import com.dsd.game.objects.powerups.HealthPowerup;
import com.dsd.game.objects.weapons.projectiles.BossProjectileObject;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Boss monster entity; follows the player around and (eventually) drains their
 * health.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 12/7/19
 */
public class FemaleMonsterBoss extends Enemy implements DeathListener {

    //  Timer for spawning in projectiles.
    private final Timer bossProjectileTimer;
    //  Static bufferedimage array so the images aren't constantly loading in upon instantiation of a new monster.
    private static final BufferedImage[] WALK_FRAMES;
    private static final BufferedImage[] DEATH_FRAMES;
    /**
     * Animation frame per second settings. WALKING_FPS_MIN/MAX define the
     * possible speeds that the boss can animate at. It's randomly selected for
     * variety.
     */
    private final int walkingFPS;
    private final int WALKING_FPS_MIN = 13;
    private final int WALKING_FPS_MAX = 16;
    private static final int DEATH_FPS = 5;
    private static final int ANIMATION_FRAME_COUNT = 13;
    //  Variables representing the angle and approach velocity.
    private static final double APPROACH_VEL = -1.2f;
    //  Damage from interacting with the entity, and how much damage each bullet will do to the player.
    private final int entityDamage = 25;
    private int bulletDamage = 25;
    //  AlphaComposite factor for when the FemaleMonsterBoss dies.
    private static final float DEATH_ALPHA_FACTOR = 0.001f;
    //  Health factor for this FemaleMonsterBoss object.
    public static int originalHealth = 500;

    public FemaleMonsterBoss(int _x, int _y, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, FemaleMonsterBoss.APPROACH_VEL, FemaleMonsterBoss.originalHealth,
                StandardID.Monster4, _game, _sch);
        super.setTarget(_game.getPlayer());
        //  Randomly generates the walking frames per second for variability
        this.walkingFPS = StdOps.rand(this.WALKING_FPS_MIN, this.WALKING_FPS_MAX);
        //  Sets the walking/death frames for this monster.
        super.initWalkingFrames(FemaleMonsterBoss.WALK_FRAMES, this.walkingFPS);
        super.initDeathFrames(FemaleMonsterBoss.DEATH_FRAMES, FemaleMonsterBoss.DEATH_FPS,
                FemaleMonsterBoss.ANIMATION_FRAME_COUNT);
        //  Sets the default animation.
        super.setAnimation(super.getWalkingAnimation());
        //  The width/height of the model is set by the buffered image backing it.
        super.setDimensions();
        super.setDamage(this.entityDamage);
        super.getHandler().addCollider(this.getId());
        super.getHandler().flagAlive(this.getId());
        super.setTransparentFactor((float) DEATH_ALPHA_FACTOR);
        this.bossProjectileTimer = new Timer(true);
        this.bossProjectileTimer.scheduleAtFixedRate(new BossProjectileSpawner(this), 2000, 2000);
    }

    /**
     * Updates the animation, health, position and status of the monster.
     */
    @Override
    public void tick() {
        super.tick();
    }

    /**
     * Draws the current frame of animation for the monster. If they are dead,
     * it also draws the explosion handler particles.
     *
     * @param _g2
     */
    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }

    /**
     * This method is called once the basic monster dies.
     */
    @Override
    public void uponDeath() {
        super.uponDeath();
        this.generateCoins(StdOps.rand(0, 5));
        this.generateDeathSound(StdOps.rand(1, 2));
        this.generatePowerup();
    }

    /**
     * Randomly generate one of the zombie sound effects.
     *
     * @param _sfx
     */
    @Override
    public void generateHurtSound(int _sfx) {
        StandardAudioController.play("src/resources/audio/sfx/zombies/zombie-" + _sfx + ".wav", StandardAudioType.SFX);
    }

    /**
     * Randomly plays one of the two sound effects for the monster.
     *
     * @param sfx either 1 or 2
     */
    private void generateDeathSound(int _sfx) {
        StandardAudioController.play("src/resources/audio/sfx/splat" + _sfx + ".wav", StandardAudioType.SFX);
    }

    /**
     * Generates _coinAmt randomly-dispersed coins around the monster's death
     * area.
     *
     * @param _coinAmt
     */
    private void generateCoins(int _coinAmt) {
        for (int i = 0; i < _coinAmt; i++) {
            this.getHandler().addEntity(new Coin(this.getGame(), (int) this.getX(),
                    (int) this.getY(), super.smallCoinDrop, super.medCoinDrop,
                    super.largeCoinDrop, this.getHandler()));
        }
    }

    /**
     * Generates a random powerup based on RNG (will definitely change).
     */
    private void generatePowerup() {
        int luck = StdOps.rand(1, 10);
        if (luck == 1) {
            this.getHandler().addEntity(new HealthPowerup((int) (this.getX() + this.getWidth() / 2),
                    (int) (this.getY() + this.getHealth() / 2),
                    this.getGame(), this.getHandler()));
        }
    }

    /**
     * Adds several projectiles to the screen from a circular position on the
     * boss char (this will improve later).
     */
    private void addProjectiles() {
        int[] velX = {0, 4, 4, 4, 0, -4, -4, -4};
        int[] velY = {-4, -4, 0, 4, 4, 4, 0, -4};
        for (int i = 0; i < velX.length; i++) {
            this.getHandler().addEntity(new BossProjectileObject((int) this.getX() + this.getWidth() / 2,
                    (int) this.getY() + this.getHeight() / 2, velX[i], velY[i], this.bulletDamage, this.getGame(),
                    this.getHandler(), this));
        }
    }

    private class BossProjectileSpawner extends TimerTask {
        private final FemaleMonsterBoss boss;

        public BossProjectileSpawner(FemaleMonsterBoss _boss) {
            this.boss = _boss;
        }

        @Override
        public void run() {
            if (this.boss.isAlive()) {
                this.boss.addProjectiles();
            }
        }
    }
    //  Static block for instantiating the images.
    static {
        WALK_FRAMES = Utilities.loadFrames("src/resources/img/enemies/femaleboss/walk/", 10);
        DEATH_FRAMES = Utilities.loadFrames("src/resources/img/enemies/femaleboss/death/", 14);
    }
    
}
