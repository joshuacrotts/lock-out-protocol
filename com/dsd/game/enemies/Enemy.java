package com.dsd.game.enemies;

import com.dsd.game.handlers.BloodParticleHandler;
import com.dsd.game.core.Game;
import com.dsd.game.enemies.enums.EnemyState;
import com.dsd.game.objects.Entity;
import com.dsd.game.particles.BloodType;
import com.dsd.game.particles.SlowingBoxParticle;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.ShapeType;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.math3.util.FastMath;

/**
 * This class is a template for an enemy. Before, we had everything extending
 * Entity, but because the Player and other enemies act differently, we needed a
 * way to further distinguish between the two. Enemy has three animator
 * controllers per entity: walking, attacking, and death. Depending on the state
 * of the enemy, one of these will be set.
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/30/19
 */
public abstract class Enemy extends Entity implements DeathListener {

    //  Miscellaneous reference variables.
    private final StandardCamera sc;

    //  Information about the enemy's state in the game, and who they're
    //  moving towards.
    private EnemyState enemyState;
    private Entity target;

    //  Animation controllers.
    private StandardAnimatorController walkingController;
    private StandardAnimatorController attackingController;
    private StandardAnimatorController deathController;

    //  Variables for the disappearing effect when the monster dies.
    private float deathTransparencyFactor;
    private float deathTransparency = 1.0f;

    //  One-time variable for tracking the "alive" to "death state" transition.
    private boolean aliveFlag = true;

    //  Vector force factor (we may want to change this, but making it final for now).
    private final int pushFactor = 2;

    //  Determines how fast the monster approaches the player.
    private final double APPROACH_VEL;

    //  Max amount of particles that can be summoned in the particle handler
    private static final int BLOOD_PARTICLES = 10;

    //  Blood particle colors (on a per-monster basis).
    //  If none is selected, red is the default.
    protected Color bloodColor = Color.RED;

    //  Handler for particle explosions after the monster dies.
    protected StandardParticleHandler explosionHandler;

    //  How much damage the enemy does when running into the player.
    protected double damage;

    //  Alpha composition object for when the monster dies.
    protected AlphaComposite deathTransparentComposite;

    //  Power-up drop odds (should be between 0-1, increasing up to 1).
    protected double smallCoinDrop = 0.7;
    protected double medCoinDrop = 0.9;
    protected double largeCoinDrop = 1.0;

    //  Initial health factor (for changing difficulty).
    public final int INIT_HEALTH;
    
    public Enemy(int _x, int _y, double _approachVel, int _health, StandardID _id, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, _health, _id, _game, _sch);
        this.sc = this.getGame().getCamera();
        this.INIT_HEALTH = _health;
        this.APPROACH_VEL = _approachVel;
    }

    @Override
    public void tick() {
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
        } else {
            //  Do this only once.
            if (this.aliveFlag) {
                this.uponDeath();
                this.aliveFlag = false;
            }
            //  Creates the alpha composite object based off the object's current transparency.
            this.updateComposite();
            if (this.explosionHandler != null) {
                StandardHandler.Handler(this.explosionHandler);
            }

            /**
             * If the size of the exphandler (MAX_PARTICLES - dead ones) == 0,
             * we can set this entity to be dead, and remove it from the
             * handler.
             */
            if (this.getTransparency() <= 0) {
                this.getHandler().removeEntity(this);
            }
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        /**
         * We need to save the old alpha composition, apply the new one, render,
         * THEN set the old one back.
         */
        if (!this.isAlive() && this.explosionHandler != null) {
            StandardDraw.Handler(this.explosionHandler);
            /**
             * If there is no death animation for a specific enemy, we just
             * render the handler and continue until it's removed from the
             * handler (because the exphandler is out of particles).
             */
            if (this.deathController != null) {
                AlphaComposite oldComposite = (AlphaComposite) _g2.getComposite();
                _g2.setComposite(this.deathTransparentComposite);
                this.getAnimationController().renderFrame(_g2);
                _g2.setComposite(oldComposite);
            }
        } else {
            this.getAnimationController().renderFrame(_g2);
        }
    }

    /**
     * Sets the dimensions of the enemy to the animation's current frame
     * dimensions.
     */
    public void setDimensions() {
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
    }

    /**
     * Applys an x and y force to the enemy object.
     *
     * @param _forceX
     * @param _forceY
     */
    public void applyPushForce(double _forceX, double _forceY) {
        this.setX(this.getX() + (_forceX * this.pushFactor));
        this.setY(this.getY() + (_forceY * this.pushFactor));
    }

    /**
     * Generates several blood particles. Will probably make this more flexible
     * later.
     */
    public void generateBloodParticles() {
        BloodParticleHandler bph = this.getGame().getBloodHandler();

        for (int i = 0; i < BLOOD_PARTICLES; i++) {
            //  Generates the still, motionless particles.
            double centerX = this.getX() + this.getWidth() / 2;
            double centerY = this.getY() + this.getHeight() / 2;

            //  Generates the particle that is on the ground.
            bph.addBloodParticle(BloodType.SLOWING, centerX, centerY, this.getAngle(), this.bloodColor);

            //  Generates the particles that are more scattered.
            bph.addBloodParticle(BloodType.STANDARD, centerX, centerY, this.getAngle(), this.bloodColor);
            bph.addBloodParticle(BloodType.SCATTERED, centerX, centerY, this.getAngle(), this.bloodColor);
        }
    }

    /**
     * Generates a random track to play when the monster is hurt. _sfxTrack
     * should be a number between 1 and the number of sfx are available for that
     * monster.
     *
     * @param _sfxTrack
     */
    public abstract void generateHurtSound(int _sfxTrack);

    /**
     * When the monster's health is below 0, this method is called by the
     * subclass.
     */
    @Override
    public void uponDeath() {
        if (this.getDeathAnimation() != null) {
            this.setAnimation(this.getDeathAnimation());
        }

        this.explosionHandler = new StandardParticleHandler(50);
        this.explosionHandler.setCamera(this.getCamera());
        for (int i = 0; i < this.explosionHandler.getMaxParticles(); i++) {

            double centerX = this.getX() + this.getWidth() / 2;
            double centerY = this.getY() + this.getHeight() / 2;

            this.explosionHandler.addEntity(new StandardBoxParticle(centerX, centerY,
                    StdOps.rand(1.0, 5.0), StdOps.randBounds(-10.0, -3.0, 3.0, 10.0),
                    StdOps.randBounds(-10.0, -3.0, 3.0, 10.0), this.bloodColor, 3f, this.explosionHandler,
                    this.getAngle(), ShapeType.CIRCLE, false));
        }

        this.moveEntityToFront();
    }

    ;

    /**
     * Instantiates the walking animation controller.
     *
     * @param _frames
     * @param _fps
     */
    protected void initWalkingFrames(BufferedImage[] _frames, int _fps) {
        this.walkingController = new StandardAnimatorController(this, _frames, _fps);
    }

    /**
     * Instantiates the walking animation controller, but also sets the halt
     * frame in the StandardAnimation object to be _haltFrame.
     *
     * @param _frames
     * @param _fps
     * @param _haltFrame;
     */
    protected void initWalkingFrames(BufferedImage[] _frames, int _fps, int _haltFrame) {
        this.walkingController = new StandardAnimatorController(new StandardAnimation(this, _frames, _fps, _haltFrame));
    }

    /**
     * Instantiates the attacking animation controller.
     *
     * @param _frames
     * @param _fps
     */
    protected void initAttackingFrames(BufferedImage[] _frames, int _fps) {
        this.attackingController = new StandardAnimatorController(this, _frames, _fps);
    }

    /**
     * Instantiates the attacking animation controller, but also sets the halt
     * frame in the StandardAnimation object to be _haltFrame.
     *
     * @param _frames
     * @param _fps
     * @param _haltFrame;
     */
    protected void initAttackingFrames(BufferedImage[] _frames, int _fps, int _haltFrame) {
        this.attackingController = new StandardAnimatorController(new StandardAnimation(this, _frames, _fps, _haltFrame));
    }

    /**
     * Instantiates the death animation controller.
     *
     * @param _frames
     * @param _fps
     */
    protected void initDeathFrames(BufferedImage[] _frames, int _fps) {
        this.deathController = new StandardAnimatorController(this, _frames, _fps);
    }

    /**
     * Instantiates the death animation controller, but also sets the halt frame
     * in the StandardAnimation object to be _haltFrame.
     *
     * @param _frames
     * @param _fps
     * @param _haltFrame;
     */
    protected void initDeathFrames(BufferedImage[] _frames, int _fps, int _haltFrame) {
        this.deathController = new StandardAnimatorController(new StandardAnimation(this, _frames, _fps, _haltFrame));
    }

    /**
     * Moves the current entity to the front of the handler. Actually performs a
     * swap.
     */
    protected void moveEntityToFront() {
        ArrayList<StandardGameObject> entities = this.getHandler().getEntities();
        Collections.swap(entities, 0, entities.indexOf(this));
    }

    /**
     * Applies the composition factor to the actual transparency.
     */
    protected void updateComposite() {
        this.deathTransparentComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.deathTransparency);
        this.deathTransparency -= this.deathTransparencyFactor;
    }

    /**
     * Makes the monster move towards the player.
     *
     * @param _posX
     * @param _posY
     */
    private void followPlayer(int _posX, int _posY) {
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
    private void facePlayer(int _posX, int _posY) {
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

//================================ GETTERS ===================================//
    public StandardAnimatorController getWalkingAnimation() {
        return this.walkingController;
    }

    public StandardAnimatorController getAttackAnimation() {
        return this.attackingController;
    }

    public StandardAnimatorController getDeathAnimation() {
        return this.deathController;
    }

    public Entity getTarget() {
        return this.target;
    }

    public StandardCamera getCamera() {
        return this.sc;
    }

    public double getDamage() {
        return this.damage;
    }

    public float getTransparency() {
        return this.deathTransparency;
    }

    public boolean isAttacking() {
        return this.enemyState == EnemyState.ATTACKING;
    }

    public boolean isWalking() {
        return this.enemyState == EnemyState.WALKING;
    }

    public int getInitialHealth() {
        return this.INIT_HEALTH;
    }

    public StandardParticleHandler getExplosionHandler() {
        return this.explosionHandler;
    }

//================================ SETTERS ===================================//
    public void setTarget(Entity _target) {
        this.target = _target;
    }

    public void setDamage(double _damage) {
        this.damage = _damage;
    }

    public void setTransparentFactor(float _alphaFactor) {
        this.deathTransparencyFactor = _alphaFactor;
    }

    public void setEnemyState(EnemyState _state) {
        this.enemyState = _state;
    }
}
