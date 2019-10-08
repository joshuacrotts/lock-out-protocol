package com.dsd.game.objects.items;

import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class represents the coin object; there are three values a coin can
 * take; small, medium, and large. The three parameters passed in the
 * constructor (the last three) designate the rarity of these.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class Coin extends StandardGameObject {

    //  Handler for the coins
    private final StandardCollisionHandler parentContainer;
    //  Frames of animation for the coins
    private static final BufferedImage[] coinOneFrames;
    private static final BufferedImage[] coinTwoFrames;
    //  Randomness for the scatter of the coin
    //  This the value at which the coins can scatter
    private final double SCATTER_RANGE = 0.99;
    //  Variables for changing the speed of the coins as they disperse
    private final double VEL_LOWER_BOUND = 0.5;
    private final double VEL_UPPER_BOUND = 1.5;
    private final int COIN_FPS = 5;
    private int value = 0;

    /**
     * The _small, _medium, and _large parameters should be sequential, and go
     * up to 1.0. Essentially, _small = 0.7, _mid = 0.9, _large = 1.0 can be
     * your coin rarity, in that 70% of the time, a small coin will drop. 20% of
     * the time, a medium coin will drop (0.9-0.7). 10% for the large.
     *
     * @param _x
     * @param _y
     * @param _small
     * @param _medium
     * @param _large
     * @param _sch
     */
    public Coin (int _x, int _y, double _small, double _medium, double _large, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Coin);
        this.parentContainer = _sch;
        this.generateCoinType(_small, _medium, _large);
        this.setVelX(StdOps.randBounds(-VEL_UPPER_BOUND, -VEL_LOWER_BOUND,
                VEL_LOWER_BOUND, VEL_UPPER_BOUND));
        this.setVelY(StdOps.randBounds(-VEL_UPPER_BOUND, -VEL_LOWER_BOUND,
                VEL_LOWER_BOUND, VEL_UPPER_BOUND));
    }

    @Override
    public void tick () {
        if (this.isAlive()) {
            this.getAnimationController().tick();
            this.slowVelocities();
            this.updatePosition();
        }
        else {
            this.parentContainer.removeEntity(this);
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isAlive()) {
            this.getAnimationController().renderFrame(_g2);
        }
    }

    /**
     * Slows the velocity of the coins gradually.
     */
    private void slowVelocities () {
        this.setVelX(this.getVelX() * SCATTER_RANGE);
        this.setVelY(this.getVelY() * SCATTER_RANGE);
    }

    /**
     * Generates a coin type depending on the probability of the coins.
     *
     * @param _small
     * @param _medium
     * @param _large
     */
    private void generateCoinType (double _small, double _medium, double _large) {
        int coin = StdOps.rand(0, 100);
        if (coin < _small * 100) {
            this.setAnimation(new StandardAnimatorController(this, coinOneFrames, COIN_FPS));
            this.value = 1;
        }
        else {
            this.setAnimation(new StandardAnimatorController(this, coinTwoFrames, COIN_FPS));
            this.value = 5;
        }
    }

//================================= GETTERS ==================================//
    public int getValue () {
        return this.value;
    }

    //static value
    static {
        coinOneFrames = Utilities.loadFrames("src/resources/img/items/coin/small", 4);
        coinTwoFrames = Utilities.loadFrames("src/resources/img/items/coin/medium", 4);
    }
}
