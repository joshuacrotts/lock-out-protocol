package com.dsd.game.objects.weapons.projectiles;

import com.dsd.game.core.Game;
import com.dsd.game.enemies.Enemy;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardID;
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
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/21/19
 */
public class BossProjectileObject extends ProjectileGameObject {

    //  Velocity factor applied to the bullet.
    private static final int VEL_FACTOR = 3;
    //  Static reference to the BufferedImages
    private static final BufferedImage[] frames = new BufferedImage[1];
    //  Animation frame per second setting
    private static final int BULLET_FPS = 20;

    private int damage = 25;

    public BossProjectileObject(int _x, int _y, int _velX, int _velY, int _damage, Game _game,
            StandardCollisionHandler _parentContainer, Enemy _parent) {
        super(_x, _y, 0, _damage, BossProjectileObject.VEL_FACTOR, BossProjectileObject.frames,
                BossProjectileObject.BULLET_FPS, _game, _parentContainer, _parent, StandardID.Bullet1);
        this.setVelX(_velX);
        this.setVelY(_velY);
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
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
    private static BufferedImage[] initImages() {
        BossProjectileObject.frames[0] = StdOps.loadImage("src/resources/img/bullet/enemy_projectile/ball.png");
        return BossProjectileObject.frames;
    }

    //  Initializes the bullet frames
    static {
        BossProjectileObject.initImages();
    }
}
