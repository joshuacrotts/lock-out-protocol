package com.dsd.game.objects;

import com.dsd.game.Game;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Bullet game object
 *
 * @TODO: Re-factor this to couple it on a per-weapon basis rather than its own
 * object fired/instantiated from AttackCommand.
 *
 * [Group Name: Data Structure Deadheads]
 * @author Joshua, Ronald, Rinty
 */
public class BulletGameObject extends ProjectileGameObject {

    //  Velocity factor applied to the bullet.
    private final int VEL_FACTOR = 20;
    //  Static reference to the BufferedImages
    private static final BufferedImage[] frames = new BufferedImage[1];
    //  Animation frame per second setting
    private static final int BULLET_FPS = 20;

    private static int damage = 25;

    public BulletGameObject (int _x, int _y, double _angle, int _damage, Game _game,
            StandardCollisionHandler _parentContainer, Player _parent) {
        super(_x, _y, _angle, _damage, BulletGameObject.frames,
                BulletGameObject.BULLET_FPS, _game, _parentContainer, _parent);
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
     * Instantiates the buffered image array.
     *
     * @return
     */
    private static BufferedImage[] initImages () {
        BulletGameObject.frames[0] = StdOps.loadImage("src/resources/img/bullet/bullet_sprite/new_bullet/bullet.png");
        return BulletGameObject.frames;
    }

    //  Initializes the bullet frames
    static {
        BulletGameObject.initImages();
    }
}
