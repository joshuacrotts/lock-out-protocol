package com.dsd.game.objects;

import com.dsd.game.Game;
import com.dsd.game.objects.weapons.Gun;
import com.revivedstandards.model.StandardParticle;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class represents the casings from a gun.
 *
 * @author Joshua, Ronald, Rinty
 */
public class BulletCasing extends StandardParticle implements Renderable, Updatable {

    //  Miscellaneous reference variables.
    private final Game game;
    private final Player player;
    private final Gun gun;
    //  Casing images.
    private final BufferedImage casingImage;
    //  Initial velocity variables and values for slowing the bullet's 'descent'.
    private static final double CASING_VEL_X_MIN = -3.0;
    private static final double CASING_VEL_X_MAX = -2.0;
    private static final double CASING_VEL_Y_MIN = 0.3;
    private static final double CASING_VEL_Y_MAX = 0.6;
    private static final double VEL_DESCENT = 0.96;

    public BulletCasing (Game _game, Player _player, Gun _parentGun) {
        
        super(_player.getX(), _player.getY(), 1f, _player.getCasingHandler());
        this.game = _game;
        this.player = _player;
        this.gun = _parentGun;
        //  Sets the x and y position of the casing to the middle pos of the player.
        this.setX(this.player.getX() + this.player.getWidth() / 2);
        this.setY(this.player.getY() + this.player.getHeight() / 2);
        //  Randomizes the velocity ot seem like it is dropping.
        this.setVelX(StdOps.rand(CASING_VEL_X_MIN, CASING_VEL_X_MAX));
        this.setVelY(StdOps.rand(CASING_VEL_Y_MIN, CASING_VEL_Y_MAX));
        this.casingImage = this.gun.getRandomCasing();
        _player.getCasingHandler().addEntity(this);
    }

    @Override
    public void tick () {
        
        this.setX(this.getX() - this.getVelX());
        this.setY(this.getY() - this.getVelY());
        this.setVelX(this.getVelX() * VEL_DESCENT);
        this.setVelY(this.getVelY() * VEL_DESCENT);
    }

    @Override
    public void render (Graphics2D _g2) {
        
        _g2.setColor(this.getColor());
        _g2.drawImage(this.casingImage, (int) this.getX(), (int) this.getY(), null);
    }
    
}
