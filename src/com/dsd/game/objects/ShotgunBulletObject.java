package com.dsd.game.objects;

import com.dsd.game.Game;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.apache.commons.math3.util.FastMath;

/**
 * Bullet game object
 *
 * @TODO: Re-factor this to couple it on a per-weapon basis rather than its own
 * object fired/instantiated from AttackCommand.
 *
 * [Group Name: Data Structure Deadheads]
 * @author Joshua, Ronald, Rinty
 */
public class ShotgunBulletObject extends ProjectileGameObject {

    //  Velocity factor applied to the bullet.
    private final int VEL_FACTOR = 20;
    //  Static reference to the BufferedImages
    private static final BufferedImage[] frames = new BufferedImage[1];
    //  Animation frame per second setting
    private static final int BULLET_FPS = 20;

    private static int damage = 100;

    public ShotgunBulletObject (int _x, int _y, double _angle, int _damage, Game _game,
            StandardCollisionHandler _parentContainer, Player _parent) {
        super(_x, _y, _angle, _damage, ShotgunBulletObject.frames,
                ShotgunBulletObject.BULLET_FPS, _game, _parentContainer, _parent);
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
    private static void initImages () {
        ShotgunBulletObject.frames[0] = StdOps.loadImage("src/resources/img/bullet/bullet_sprite/new_bullet/shotgun_bullet.png");
    }

//============================ SETTERS ====================================//
    /**
     * Instantiates the velocity of the bullet depending on where the cursor is
     * in relation to the player.
     *
     * @param _x
     * @param _y
     * @param _mx
     * @param _my
     */
    private void setVelocity (double _x, double _y, int _mx, int _my) {
        double deltaX = (_mx - _x);
        double deltaY = (_my - _y);
        // Use the pythagorean theorem to solve for the hypotenuse distance
        double distance = (double) FastMath.sqrt(((deltaX) * (deltaX))
                + ((deltaY) * (deltaY)));
        deltaX = (deltaX / distance) * this.VEL_FACTOR;
        deltaY = (deltaY / distance) * this.VEL_FACTOR;
        this.setVelX(deltaX);
        this.setVelY(deltaY);
    }

//========================== GETTERS =======================================//
    //  Initializes the bullet frames
    static {
        ShotgunBulletObject.initImages();
    }
}
