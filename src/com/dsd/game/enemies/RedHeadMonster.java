package com.dsd.game.enemies;

import com.dsd.game.core.Game;
import com.dsd.game.objects.items.Coin;
import com.dsd.game.objects.powerups.HealthPowerup;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.ShapeType;
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
 * @updated 11/12/19
 */
public class RedHeadMonster extends Enemy implements DeathListener {

    //
    //  Static bufferedimage array so the images aren't constantly loading in
    //  upon instantiation of a new monster
    //
    private static final BufferedImage[] WALK_FRAMES;

    //  Animation frame per second setting
    private final int walkingFPS = 16;

    //  Variables representing the angle and approach velocity
    private static final double APPROACH_VEL = -2.5f;
    private final double DAMAGE = 0.20;

    //  Health factor for this BasicMonster object.
    public static int originalHealth = 100;

    public RedHeadMonster (int _x, int _y, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, RedHeadMonster.APPROACH_VEL, RedHeadMonster.originalHealth, StandardID.Monster6, _game, _sch);
        this.setTarget(_game.getPlayer());
        //  Sets the walking/death frames for this monster
        super.initWalkingFrames(RedHeadMonster.WALK_FRAMES, this.walkingFPS);
        //  Sets the default animation
        super.setAnimation(super.getWalkingAnimation());
        //  The width/height of the model is set by the buffered image backing it.
        super.setDimensions();
        super.setDamage(this.DAMAGE);
        super.getHandler().addCollider(this.getId());
        super.getHandler().flagAlive(this.getId());
    }

    @Override
    public void tick () {
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }

    /**
     * This method is called once the basic monster dies.
     *
     * @TODO: Re-factor the magic numbers
     */
    @Override
    public void uponDeath () {
        this.explosionHandler = new StandardParticleHandler(50);
        this.explosionHandler.setCamera(this.getCamera());
        for (int i = 0 ; i < this.explosionHandler.getMaxParticles() ; i++) {
            this.explosionHandler.addEntity(new StandardBoxParticle(this.getX(), this.getY(),
                    StdOps.rand(1.0, 5.0), StdOps.randBounds(-10.0, -3.0, 3.0, 10.0),
                    StdOps.randBounds(-10.0, -3.0, 3.0, 10.0), Color.RED, 3f, this.explosionHandler,
                    this.getAngle(), ShapeType.CIRCLE, true));
        }
        this.generateCoins(StdOps.rand(0, 5));
        this.generateDeathSound(StdOps.rand(1, 2));
        this.generatePowerup();
        this.moveEntityToFront();
    }

    /**
     * Randomly generate one of the zombie sound effects.
     *
     * @param _sfx
     */
    @Override
    public void generateHurtSound (int _sfx) {
        StandardAudioController.play("src/resources/audio/sfx/zombies/zombie-" + _sfx + ".wav", StandardAudioType.SFX);
    }

    /**
     * Randomly plays one of the two sound effects for the monster.
     *
     * @param sfx either 1 or 2
     */
    private void generateDeathSound (int _sfx) {
        StandardAudioController.play("src/resources/audio/sfx/splat" + _sfx + ".wav", StandardAudioType.SFX);
    }

    /**
     * Generates _coinAmt randomly-dispersed coins around the monster's death
     * area.
     *
     * @param _coinAmt
     */
    private void generateCoins (int _coinAmt) {
        for (int i = 0 ; i < _coinAmt ; i++) {
            this.getHandler().addEntity(new Coin(this.getGame(), (int) this.getX(), (int) this.getY(), 0.7, 0.9, 1.0, this.getHandler()));
        }
    }

    /**
     * Generates a random powerup based on RNG (will definitely change).
     */
    private void generatePowerup () {
        int luck = StdOps.rand(1, 5);
        if (luck == 1) {
            this.getHandler().addEntity(new HealthPowerup((int) (this.getX() + this.getWidth() / 2),
                    (int) (this.getY() + this.getHealth() / 2),
                    this.getGame(), this.getHandler()));
        }
    }

    //  Static block for instantiating the images.
    static {
        WALK_FRAMES = Utilities.loadFrames("src/resources/img/enemies/monster9/walk/", 17);
    }
}
