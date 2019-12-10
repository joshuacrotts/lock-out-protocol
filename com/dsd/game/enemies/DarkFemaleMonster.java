package com.dsd.game.enemies;

import com.dsd.game.core.Game;
import com.dsd.game.objects.items.Coin;
import com.dsd.game.objects.powerups.HealthPowerup;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Monster entity; follows the player around and (eventually) drains his health.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 12/3/19
 */
public class DarkFemaleMonster extends Enemy implements DeathListener {

    //  Static bufferedimage array so the images aren't constantly loading in upon instantiation of a new monster
    private static final BufferedImage[] WALK_FRAMES;
    private static final BufferedImage[] DEATH_FRAMES;

    //  Animation frame per second setting
    private final int walkingFPS;
    private final int WALKING_FPS_MIN = 13;
    private final int WALKING_FPS_MAX = 16;
    private static final int DEATH_FPS = 5;

    //  Variables representing the angle and approach velocity
    private static final double APPROACH_VEL = -1.2f;
    private final double DAMAGE = 1.0;

    //  AlphaComposite factor for when the DarkFemaleMonster dies
    private static final float DEATH_ALPHA_FACTOR = 0.001f;

    //  Health factor for this DarkFemaleMonster object.
    public static int originalHealth = 250;

    //  Blood color RGB limits (for generating a random color. For this monster,
    //  we generate a random purple color).
    private static final int RED_BOUND = 120;
    private static final int BLUE_BOUND = 161;

    public DarkFemaleMonster(int _x, int _y, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, DarkFemaleMonster.APPROACH_VEL, DarkFemaleMonster.originalHealth,
                StandardID.Monster3, _game, _sch);
        super.setTarget(_game.getPlayer());

        //  Randomly generates the walking frames per second for variability
        this.walkingFPS = StdOps.rand(this.WALKING_FPS_MIN, this.WALKING_FPS_MAX);

        //  Sets the walking/death frames for this monster
        super.initWalkingFrames(DarkFemaleMonster.WALK_FRAMES, this.walkingFPS);
        super.initDeathFrames(DarkFemaleMonster.DEATH_FRAMES, DarkFemaleMonster.DEATH_FPS, 5);

        //  Sets the default animation
        super.setAnimation(super.getWalkingAnimation());

        //  The width/height of the model is set by the buffered image backing it.
        super.setDimensions();
        super.setDamage(this.DAMAGE);
        super.getHandler().addCollider(this.getId());
        super.getHandler().flagAlive(this.getId());
        super.setTransparentFactor((float) DEATH_ALPHA_FACTOR);

        this.bloodColor = this.generateRandomBloodColor();
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
     *
     * @TODO: Re-factor the magic numbers
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
     * Using the preset color values from the class, we generate a random shade
     * of purple for the blood of this monster.
     *
     * @return new Color object.
     */
    private Color generateRandomBloodColor() {
        return new Color(StdOps.rand(DarkFemaleMonster.RED_BOUND, DarkFemaleMonster.BLUE_BOUND), 0,
                StdOps.rand(DarkFemaleMonster.BLUE_BOUND, 0xFF));
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

    //  Static block for instantiating the images.
    static {
        WALK_FRAMES = Utilities.loadFrames("src/resources/img/enemies/monster3/walk/", 11);
        DEATH_FRAMES = Utilities.loadFrames("src/resources/img/enemies/monster3/death/", 6);
    }
}
