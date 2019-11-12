package com.dsd.game.objects.weapons.projectiles;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Grenade launcher bullet game object.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/9/19
 *
 */
public class GrenadeBulletObject extends ProjectileGameObject implements DeathListener {

    //  Velocity factor applied to the bullet.
    private static final int VEL_FACTOR = 40;

    //  Static reference to the BufferedImages
    private static final BufferedImage[] frames = new BufferedImage[1];

    //  Animation frame per second setting
    private static final int BULLET_FPS = 20;

    private int damage = 250;

    private boolean uponDeathFlag = false;

    public GrenadeBulletObject (int _x, int _y, double _angle, int _damage, Game _game,
            StandardCollisionHandler _parentContainer, Player _parent) {
        super(_x, _y, _angle, _damage, VEL_FACTOR, GrenadeBulletObject.frames,
                GrenadeBulletObject.BULLET_FPS, _game, _parentContainer, _parent,
                StandardID.Bullet);

    }

    @Override
    public void tick () {
        if (this.isAlive() && !this.uponDeathFlag) {
            super.tick();
        }
        else if (!this.uponDeathFlag) {
            this.uponDeathFlag = true;
            this.uponDeath();
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }

    @Override
    public void uponDeath () {
        StandardAudioController.play("src/resources/audio/sfx/grenade_explosion.wav", StandardAudioType.SFX);
    }

    /**
     * Instantiates the buffered image array.
     *
     * @return
     */
    private static void initImages () {
        GrenadeBulletObject.frames[0] = StdOps.loadImage("src/resources/img/bullet/bullet_sprite/new_bullet/grenade_bullet.png");
    }

//========================== GETTERS =======================================//
    //  Initializes the bullet frames
    static {
        GrenadeBulletObject.initImages();
    }

}
