package com.dsd.game.objects.items;

import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joshua
 */
public class Coin extends StandardGameObject {

    private static final BufferedImage[] coinOneFrames;
    private static final BufferedImage[] coinTwoFrames;
    private final int coinFPS = 5;

    private int value = 0;

    public Coin (int _x, int _y, double _small, double _mid, double _large) {
        super(_x, _y, StandardID.Object);

        this.generateCoinType(_small, _mid, _large);

        this.setVelX(StdOps.randBounds(-1.5, -0.5, 0.5, 1.5));
        this.setVelY(StdOps.randBounds(-1.5, -0.5, 0.5, 1.5));
    }

    @Override
    public void tick () {
        if (this.isAlive()) {
            this.getAnimationController().tick();
            this.slowVelocities();
            this.updatePosition();
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isAlive()) {
            this.getAnimationController().renderFrame(_g2);
        }
    }

    private void slowVelocities () {
        this.setVelX(this.getVelX() * 0.99);
        this.setVelY(this.getVelY() * 0.99);
    }

    private void generateCoinType (double _small, double _mid, double _large) {
        int coin = StdOps.rand(0, 100);
        if (coin < _small * 100) {
            this.setAnimation(new StandardAnimatorController(this, coinOneFrames, coinFPS));
            this.value = 1;
        }
        else {
            this.setAnimation(new StandardAnimatorController(this, coinTwoFrames, coinFPS));
            this.value = 5;
        }
    }

//================================= GETTERS ==================================//
    public int getValue () {
        return this.value;
    }

    static {
        coinOneFrames = Utilities.loadFrames("src/res/img/items/coin/small", 4);
        coinTwoFrames = Utilities.loadFrames("src/res/img/items/coin/medium", 4);
    }
}
