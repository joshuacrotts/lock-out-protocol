/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.objects;

import com.dsd.game.Game;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.ShapeType;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.apache.commons.math3.util.FastMath;

/**
 * Monster entity; follows the player around and (eventually) drains his health.
 *
 * @author Joshua
 */
public class Monster extends Entity implements DeathListener {

    //
    //  Miscellaneous reference variables
    //
    private final Player target;
    private final StandardCamera sc;

    //
    //  Handler for particle explosions after the bullet
    //  collides with a wall.
    //
    private StandardParticleHandler explosionHandler;

    //
    //  BufferedImage arrays for the sprites
    //
    private static final BufferedImage[] walkingFrames;

    //
    //  One-time variable for tracking the "alive" to "death state" transition
    //
    private boolean aliveFlag = true;

    //
    //  Variables representing the angle and approach velocity
    //
    private final float approachVel = -1.5f;
    private float angle;

    public Monster (int _x, int _y, Game _sg, StandardCamera _sc, StandardCollisionHandler _sch) {
        super(_x, _y, 100, StandardID.Enemy3, _sg, _sch);
        this.target = _sg.getPlayer();
        this.sc = _sc;

        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(this, Monster.walkingFrames, 10d));

        this.setAnimation(walkingAnimation);

        //  Hard-coded values from png files
        this.setWidth(199);
        this.setHeight(191);

        this.getHandler().addCollider(this.getId());
        this.getHandler().flagAlive(this.getId());
    }

    @Override
    public void tick () {

        if (this.isAlive()) {
            this.updatePosition();
            this.getAnimationController().tick();
            this.getAnimationController().getStandardAnimation().setRotation(this.angle);

            //*******************************************************************//
            //    Causes the monster to follow the target wherever on the screen //
            //*******************************************************************//
            // Save the mouse position
            double tx = this.target.getX();
            double ty = this.target.getY();

            // Calculate the distance between the enemy and the player
            double diffX = this.getX() - tx - 8;
            double diffY = this.getY() - ty - 8;

            // Use the pythagorean theorem to solve for the hypotenuse distance
            double distance = (double) FastMath.sqrt(((this.getX() - tx) * (this.getX() - tx))
                    + ((this.getY() - ty) * (this.getY() - ty)));

            // Sets the velocity according to how far away the enemy is from the
            // player
            this.setVelX(((this.approachVel / distance) * (int) diffX));
            this.setVelY(((this.approachVel / distance) * (int) diffY));

            //*****************************************************************//
            //      Calculates the angle the monster needs to be in to face    //
            //      the player                                                 //
            //*****************************************************************//
            float xSign = (float) FastMath.signum(tx - this.getX());
            double dx = FastMath.abs(tx - this.getX());
            double dy = FastMath.abs(ty - this.getY());

            this.angle = (float) ((xSign) * (FastMath.atan((dx) / (dy))));

            // If we're in Q1 (+x, -+y) or in Q2 (-x, +y)
            if ((tx > this.getX() && ty > this.getY()) || (tx < this.getX() && ty > this.getY())) {
                this.angle = (float) ((FastMath.PI * 0.5) + (FastMath.PI * 0.5 - this.angle));
            }
        }
        else {
            // Do this only once.
            if (this.aliveFlag) {
                this.uponDeath();
                this.aliveFlag = false;
            }

            // If the size of the exphandler (MAX_PARTICLES - dead ones) == 0,
            // we can set this entity to be dead, and remove it from the handler.
            if (this.explosionHandler.size() == 0) {
                this.getHandler().removeEntity(this);
            }

            StandardHandler.Handler(this.explosionHandler);
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isAlive()) {
            this.getAnimationController().renderFrame(_g2);
        }
        else if (this.explosionHandler != null) {
            StandardDraw.Handler(this.explosionHandler);
        }
    }

    @Override
    public void uponDeath () {
        this.explosionHandler = new StandardParticleHandler(50);
        this.explosionHandler.setCamera(this.sc);

        for (int i = 0 ; i < this.explosionHandler.getMaxParticles() ; i++) {

            this.explosionHandler.addEntity(new StandardBoxParticle(this.getX(), this.getY(),
                    StdOps.rand(1.0, 5.0), StdOps.randBounds(-10.0, -3.0, 3.0, 10.0),
                    StdOps.randBounds(-10.0, -3.0, 3.0, 10.0), Color.RED, 3f, this.explosionHandler,
                    this.angle, ShapeType.CIRCLE, true));
        }

        this.generateDeathSound(StdOps.rand(1, 2));
    }

    /**
     * Randomly plays one of the two sound effects for the monster.
     *
     * @param sfx either 1 or 2
     */
    private void generateDeathSound (int _sfx) {
        StandardAudioController.play("src/res/audio/sfx/splat" + _sfx + ".wav");
    }

    static {
        walkingFrames = Utilities.loadFrames("src/res/img/enemies/monster1/walk/", 9);
    }

}
