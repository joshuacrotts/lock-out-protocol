/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.objects;

import com.dsd.game.Game;
import com.dsd.game.objects.items.Coin;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.model.DeathListener;
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
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class BasicMonster extends Enemy implements DeathListener {

    //
    //  Handler for particle explosions after the
    //  monster dies.
    //
    private StandardParticleHandler explosionHandler;

    //
    //  Static bufferedimage array so the images aren't constantly
    //  loading in upon instantiation of a new monster
    //
    private static final BufferedImage[] WALK_FRAMES;
    private static final BufferedImage[] DEATH_FRAMES;

    //
    //  Animation frame per second setting
    //
    private static final int WALKING_FPS = 10;
    private static final int DEATH_FPS = 5;

    //
    //  One-time variable for tracking the "alive" to "death state" transition
    //
    private boolean aliveFlag = true;

    //
    //  Variables representing the angle and approach velocity
    //
    private final float APPROACH_VEL = -1.5f;
    private final double DAMAGE = 0.20;
    private float angle;

    //
    //  Health factor for this specific object.
    //
    private static int health = 100;

    public BasicMonster (int _x, int _y, Game _game, StandardCamera _sc, StandardCollisionHandler _sch) {
        super(_x, _y, BasicMonster.health, StandardID.BasicMonster, _game, _sch);
        this.setTarget(_game.getPlayer());

        super.initWalkingFrames(BasicMonster.WALK_FRAMES, WALKING_FPS);
        super.initDeathFrames(BasicMonster.DEATH_FRAMES, DEATH_FPS, 5);

        super.setAnimation(super.getWalkingAnimation());

        //  The width/height of the model is set by the buffered image backing it.
        super.setDimensions();
        super.setDamage(this.DAMAGE);

        super.getHandler().addCollider(this.getId());
        super.getHandler().flagAlive(this.getId());

        super.setTransparentFactor((float) 0.001);
    }

    @Override
    public void tick () {
        // If the monster's health is less than 0, we can flag it as dead.
        this.setAlive(this.getHealth() > 0);
        this.getAnimationController().tick();
        this.getAnimationController().getStandardAnimation().setRotation(this.angle);

        if (this.isAlive()) {
            this.updatePosition();

            // Save the mouse position
            double tx = this.getTarget().getX();
            double ty = this.getTarget().getY();
            //*******************************************************************//
            //    Causes the monster to follow the target wherever on the screen //
            //*******************************************************************//

            this.followPlayer((int) tx, (int) ty);

            //*****************************************************************//
            //      Calculates the angle the monster needs to be in to face    //
            //      the player                                                 //
            //*****************************************************************//
            this.facePlayer((int) tx, (int) ty);
        }
        else {
            // Do this only once.
            if (this.aliveFlag) {
                this.uponDeath();
                this.aliveFlag = false;
            }

            //
            //  Creates the alpha composite object based off the object's current
            //  transparency.
            //
            this.updateComposite();

            // If the size of the exphandler (MAX_PARTICLES - dead ones) == 0,
            // we can set this entity to be dead, and remove it from the handler.
            if (this.explosionHandler.size() == 0 || this.getTransparency() <= 0) {
                this.getHandler().removeEntity(this);
            }

            StandardHandler.Handler(this.explosionHandler);
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        //
        //  We need to save the old alpha composition, apply the new one,
        //  render, THEN set the old one back.

        if (!this.isAlive() && this.explosionHandler != null) {
            StandardDraw.Handler(this.explosionHandler);
            //
            //  We need to save the old alpha composition, apply the new one,
            //  render, THEN set the old one back.
            //
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
                    this.angle, ShapeType.CIRCLE, true));
        }

        this.generateCoins(StdOps.rand(0, 5));
        this.generateDeathSound(StdOps.rand(1, 2));
        this.moveEntityToFront();
    }

    /**
     * Randomly generate one of the zombie sound effects.
     *
     * @param _sfx
     */
    public void generateHurtSound (int _sfx) {
        StandardAudioController.play("src/res/audio/sfx/zombies/zombie-" + _sfx + ".wav");
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

        // Sets the velocity according to how far away the enemy is from the
        // player
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
        //
        //  Calculates the angle using arctangent that the monster needs to
        //  face so they are angled towards the player.
        //
        float xSign = (float) FastMath.signum(_posX - this.getX());
        double dx = FastMath.abs(_posX - this.getX());
        double dy = FastMath.abs(_posY - this.getY());

        this.angle = (float) ((xSign) * (FastMath.atan((dx) / (dy))));

        // If we're in Q1 (+x, -+y) or in Q2 (-x, +y)
        if ((_posX > this.getX() && _posY > this.getY()) || (_posX < this.getX() && _posY > this.getY())) {
            this.angle = (float) ((FastMath.PI / 2) + (FastMath.PI / 2 - this.angle));
        }
    }

    /**
     * Randomly plays one of the two sound effects for the monster.
     *
     * @param sfx either 1 or 2
     */
    private void generateDeathSound (int _sfx) {
        StandardAudioController.play("src/res/audio/sfx/splat" + _sfx + ".wav");
    }

    /**
     * Generates _coinAmt randomly-dispersed coins around the monster's death
     * area.
     *
     * @param _coinAmt
     */
    private void generateCoins (int _coinAmt) {
        for (int i = 0 ; i < _coinAmt ; i++) {
            this.getHandler().addEntity(new Coin((int) this.getX(), (int) this.getY(), 0.7, 0.9, 1.0, this.getHandler()));
        }
    }

    //
    //  Static block for instantiating the images.
    //
    static {
        WALK_FRAMES = Utilities.loadFrames("src/res/img/enemies/monster1/walk/", 9);
        DEATH_FRAMES = Utilities.loadFrames("src/res/img/enemies/monster1/death/", 6);
    }
}
