package com.dsd.game.objects;

import com.dsd.game.Game;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Bullet game object
 *
 * @TODO: Refactor this into ProjectileGameObject
 *
 * @author Joshua
 */
public class BulletGameObject extends StandardGameObject {

    //
    //  Miscellaneous variables
    //
    private final StandardGame sg;
    private final StandardCollisionHandler sch;
    private final StandardCamera sc;

    //
    //  Angle of bullet according to current rotation of player,
    //  and damage of bullet.
    //
    private final double angle;
    private final int damage = 25;

    //
    //  Static reference to the BufferedImages
    //
    private static final BufferedImage[] frames = new BufferedImage[1];

    public BulletGameObject (int _x, int _y, double _angle, Game _sg,
                             StandardCollisionHandler _parentContainer,
                             StandardCamera _sc, Player _parent) {

        super(_x, _y, StandardID.Projectile);

        this.sg = _sg;
        this.sch = _parentContainer;
        this.angle = _angle;

        this.setAnimation(new StandardAnimatorController(
                    new StandardAnimation(this, BulletGameObject.frames, 20)));
        this.setWidth(this.getWidth());
        this.setHeight(this.getHeight());
        this.setAlive(true);
        this.setVelX(_parent.getVelX() * 20);
        this.setVelY(_parent.getVelY() * 20);

        this.sch.flagAlive(this.getId());
        this.sch.addCollider(this.getId());

        this.sc = _sc;
    }

    @Override
    public void tick () {
        if (this.sc.SGOInBounds(this)) {
            this.updatePosition();

            //As long as the object is alive, we can tick it.
            if (this.isAlive()) {
                this.getAnimationController().tick();
            }
        } else {
            this.sch.removeEntity(this);
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        // If they're alive, draw the frame that the bullet animation is on.
        // Otherwise, render the explosion handler
        if (this.isAlive()) {
            this.getAnimationController().getStandardAnimation().setRotation(angle);
            this.getAnimationController().renderFrame(_g2);
        }
    }

    /**
     * Instantiates the buffered image array.
     *
     * @return
     */
    private static BufferedImage[] initImages () {

        BulletGameObject.frames[0] = (StdOps.loadImage("src/res/img/bullet/bullet_sprite/bullet.png"));

        return BulletGameObject.frames;
    }

    public int getDamage () {
        return this.damage;
    }

    //
    //  Initializes the bullet frames
    //
    static {
        BulletGameObject.initImages();
    }
}
