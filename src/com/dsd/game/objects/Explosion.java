package com.dsd.game.objects;

import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class is a simple explosion artifact in the game.
 *
 * @author Joshua
 */
public class Explosion extends StandardGameObject {

    //  Miscellaneous reference variables
    private final StandardAnimatorController animation;
    private static final BufferedImage[] frames;

    private static final int EXPLOSION_FPS = 25;

    public Explosion (int _x, int _y) {
        super(_x, _y, StandardID.Tile1);

        this.animation = new StandardAnimatorController(new StandardAnimation(this,
                    Explosion.frames, EXPLOSION_FPS, Explosion.frames.length - 1));
    }

    @Override
    public void render (Graphics2D _gd) {
        if (this.isAlive()) {
            this.animation.renderFrame(_gd);
        }
    }

    @Override
    public void tick () {
        if (this.isAlive()) {
            this.animation.tick();
        }
        if (this.animation.getStandardAnimation().getCurrentFrameIndex() == Explosion.frames.length - 1) {
            this.setAlive(false);
        }
    }

    static {
        frames = Utilities.loadFrames("src/resources/img/bullet/explosion/explosion1", 16);
    }
}
