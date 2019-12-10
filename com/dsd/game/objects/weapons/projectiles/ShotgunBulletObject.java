package com.dsd.game.objects.weapons.projectiles;

import com.dsd.game.core.Game;
import com.dsd.game.objects.Player;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Shotgun bullet game object.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/9/19
 */
public class ShotgunBulletObject extends ProjectileGameObject {

    //  Velocity factor applied to the bullet.
    private static final int VEL_FACTOR = 40;

    //  Static reference to the BufferedImages
    private static final BufferedImage[] frames = new BufferedImage[1];

    //  Animation frame per second setting
    private static final int BULLET_FPS = 20;

    public ShotgunBulletObject(int _x, int _y, double _angle, int _damage, Game _game,
            StandardCollisionHandler _parentContainer, Player _parent) {
        super(_x, _y, _angle, _damage, ShotgunBulletObject.VEL_FACTOR, ShotgunBulletObject.frames,
                ShotgunBulletObject.BULLET_FPS, _game, _parentContainer, _parent, StandardID.Bullet);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }

    /**
     * Instantiates the buffered image array.
     *
     * @return
     */
    private static void initImages() {
        ShotgunBulletObject.frames[0] = StdOps.loadImage("src/resources/img/bullet/bullet_sprite/new_bullet/shotgun_bullet.png");
    }

//========================== GETTERS =======================================//
    //  Initializes the bullet frames
    static {
        ShotgunBulletObject.initImages();
    }
}
