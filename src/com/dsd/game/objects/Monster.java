/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.objects;

import com.dsd.game.Game;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.apache.commons.math3.util.FastMath;

/**
 * Monster entity; follows the player around and (eventually) drains his health.
 *
 * @author Joshua
 */
public class Monster extends StandardGameObject {

    //
    //  Miscellaneous reference variables
    //
    private final Game game;
    private final Player target;

    //
    //  BufferedImage arrays for the sprites
    //
    private static final BufferedImage[] walkingFrames;

    //
    //  Variables representing the angle and approach velocity
    //
    private float angle;
    private final float approachVel = -1.5f;

    public Monster (int _x, int _y, Game _sg) {
        super(_x, _y, StandardID.Enemy3);
        this.game = _sg;
        this.target = _sg.getPlayer();

        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(this, Monster.walkingFrames, 10d));

        this.setAnimation(walkingAnimation);

        //  Hard-coded values from png files
        this.setWidth(199);
        this.setHeight(191);

    }

    @Override
    public void tick () {

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

    @Override
    public void render (Graphics2D _g2) {
        this.getAnimationController().renderFrame(_g2);
    }

    static {
        walkingFrames = Utilities.loadFrames("src/res/img/enemies/monster1/walk/", 9);
    }

}
