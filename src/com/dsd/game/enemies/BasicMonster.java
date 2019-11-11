package com.dsd.game.enemies;

import com.dsd.game.Game;
import com.dsd.game.objects.Entity;
import com.dsd.game.objects.items.Coin;
import com.dsd.game.objects.powerups.HealthPowerup;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.ShapeType;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.apache.commons.math3.util.FastMath;

/**
 * Monster entity; follows the player around and (eventually) drains his health.
 *
 * @author Joshua, Ronald, Rinty
 */
public class BasicMonster extends Enemy implements DeathListener {

    //  Handler for particle explosions after the monster dies.
    private StandardParticleHandler explosionHandler;
    /**
     * Static bufferedimage array so the images aren't constantly loading in
     * upon instantiation of a new monster
     */
    private static final BufferedImage[] WALK_FRAMES;
    private static final BufferedImage[] DEATH_FRAMES;
    private static final BufferedImage[] ATTACK_FRAMES;
    //  Animation frame per second setting
    private final int walkingFPS;
    private final int WALKING_FPS_MIN = 7;
    private final int WALKING_FPS_MAX = 13;
    private static final int ATTACK_FPS = 9;
    private static final int DEATH_FPS = 5;
    //  One-time variable for tracking the "alive" to "death state" transition
    private boolean aliveFlag = true;
    //  Variables representing the angle and approach velocity
    private final double APPROACH_VEL = -1.5f;
    private final double DAMAGE = 0.20;
    //  AlphaComposite factor for when the BasicMonster dies
    private static final float DEATH_ALPHA_FACTOR = 0.001f;
    //  Health factor for this BasicMonster object.
    public static int originalHealth = 100;

    public BasicMonster (int _x, int _y, Game _game, StandardCollisionHandler _sch) {

        super(_x, _y, BasicMonster.originalHealth, StandardID.BasicMonster, _game, _sch);
        this.setTarget(_game.getPlayer());
        //  Randomly generates the walking frames per second for variability
        this.walkingFPS = StdOps.rand(this.WALKING_FPS_MIN, this.WALKING_FPS_MAX);
        //  Sets the walking/death frames for this monster
        super.initWalkingFrames(BasicMonster.WALK_FRAMES, this.walkingFPS);
        super.initAttackingFrames(BasicMonster.ATTACK_FRAMES, BasicMonster.ATTACK_FPS);
        super.initDeathFrames(BasicMonster.DEATH_FRAMES, BasicMonster.DEATH_FPS, 5);
        //  Sets the default animation
        super.setAnimation(super.getWalkingAnimation());
        //  The width/height of the model is set by the buffered image backing it.
        super.setDimensions();
        super.setDamage(this.DAMAGE);
        super.getHandler().addCollider(this.getId());
        super.getHandler().flagAlive(this.getId());
        super.setTransparentFactor((float) DEATH_ALPHA_FACTOR);
    }

    @Override
    public void tick () {

        super.tick();
        //  If the monster's health is less than 0, we can flag it as dead.
        this.setAlive(this.getHealth() > 0);
        this.getAnimationController().tick();
        this.getAnimationController().getStandardAnimation().setRotation(this.getAngle());

        if (this.isAlive()) {

            this.updatePosition();
            //  Save the target's position
            double tx = this.getTarget().getX();
            double ty = this.getTarget().getY();
            //  Causes the monster to follow the target wherever on the screen
            this.followPlayer((int) tx, (int) ty);
            //  Calculates the angle the monster needs to be in to face the player
            this.facePlayer((int) tx, (int) ty);
        }

        else {

            //  Do this only once.
            if (this.aliveFlag) {

                this.uponDeath();
                this.aliveFlag = false;
            }

            //  Creates the alpha composite object based off the object's current transparency.
            this.updateComposite();

            /**
             * If the size of the exphandler (MAX_PARTICLES - dead ones) == 0,
             * we can set this entity to be dead, and remove it from the
             * handler.
             */
            if (this.explosionHandler.size() == 0 || this.getTransparency() <= 0) {

                this.getHandler().removeEntity(this);
            }

            StandardHandler.Handler(this.explosionHandler);
        }

    }

    @Override
    public void render (Graphics2D _g2) {

        super.render(_g2);

        /**
         * We need to save the old alpha composition, apply the new one, render,
         * THEN set the old one back.
         */
        if (!this.isAlive() && this.explosionHandler != null) {

            StandardDraw.Handler(this.explosionHandler);
            /**
             * We need to save the old alpha composition, apply the new one,
             * render, THEN set the old one back.
             */
            AlphaComposite oldComposite = (AlphaComposite) _g2.getComposite();
            _g2.setComposite(this.deathTransparentComposite);
            this.getAnimationController().renderFrame(_g2);
            _g2.setComposite(oldComposite);
        }

        else {

            this.getAnimationController().renderFrame(_g2);
        }

    }

    /**
     * This method is called once the basic monster dies.
     *
     * @TODO: Re-factor the magic numbers
     */
    @Override
    public void uponDeath () {

        this.setAnimation(this.getDeathAnimation());
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

        StandardAudioController.play("src/resources/audio/sfx/basic_monster/zombie-" + _sfx + ".wav", StandardAudioType.SFX);
    }

    /**
     * Makes the monster move towards the player.
     *
     * @param _posX
     * @param _posY
     */
    private void followPlayer (int _posX, int _posY) {

        // Calculate the distance between the enemy and the player
        double diffX = this.getX() - _posX - Entity.APPROACH_FACTOR;
        double diffY = this.getY() - _posY - Entity.APPROACH_FACTOR;
        // Use the pythagorean theorem to solve for the hypotenuse distance
        double distance = (double) FastMath.sqrt(((this.getX() - _posX) * (this.getX() - _posX))
                + ((this.getY() - _posY) * (this.getY() - _posY)));
        // Sets the velocity according to how far away the enemy is from the player
        this.setVelX(((this.APPROACH_VEL / distance) * (int) diffX));
        this.setVelY(((this.APPROACH_VEL / distance) * (int) diffY));
    }

    /**
     * Makes the monster face the player.
     *
     * @param _posX
     * @param _posY
     */
    private void facePlayer (int _posX, int _posY) {

        /**
         * Calculates the angle using arctangent that the monster needs to face
         * so they are angled towards the player.
         */
        float xSign = (float) FastMath.signum(_posX - this.getX());
        double dx = FastMath.abs(_posX - this.getX());
        double dy = FastMath.abs(_posY - this.getY());
        this.setAngle((double) ((xSign) * (FastMath.atan((dx) / (dy)))));

        // If we're in Q1 (+x, -+y) or in Q2 (-x, +y)
        if ((_posX > this.getX() && _posY > this.getY()) || (_posX < this.getX() && _posY > this.getY())) {

            this.setAngle((double) ((FastMath.PI / 2) + (FastMath.PI / 2 - this.getAngle())));
        }

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

        WALK_FRAMES = Utilities.loadFrames("src/resources/img/enemies/monster1/walk/", 11);
        DEATH_FRAMES = Utilities.loadFrames("src/resources/img/enemies/monster1/death/", 6);
        ATTACK_FRAMES = Utilities.loadFrames("src/resources/img/enemies/monster1/attack/", 9);
    }

}
