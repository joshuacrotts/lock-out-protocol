/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.particles;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.util.Utilities;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.model.StandardParticle;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class represents the casings from a gun.
 *
 * @author Joshua
 */
public class BulletCasing extends StandardParticle implements Renderable, Updatable {

    //  Miscellaneous reference variables.
    private final Game game;
    private final Player player;

    //  Casing images.
    private static final BufferedImage[] casingImgs;
    private final BufferedImage casingImage;

    //  Initial velocity variables and values for slowing the bullet's 'descent'.
    private static final double CASING_VEL_X_MIN = -3.0;
    private static final double CASING_VEL_X_MAX = -2.0;
    private static final double CASING_VEL_Y_MIN = 0.3;
    private static final double CASING_VEL_Y_MAX = 0.6;
    private static final double VEL_DESCENT = 0.96;

    public BulletCasing (Game _game, Player _player) {
        super(_player.getX(), _player.getY(), 15f, _player.getCasingHandler(), Color.YELLOW);
        this.game = _game;
        this.player = _player;

        this.setX(this.player.getX() + this.player.getWidth() / 2);
        this.setY(this.player.getY() + this.player.getHeight() / 2);
        this.setVelX(StdOps.rand(CASING_VEL_X_MIN, CASING_VEL_X_MAX));
        this.setVelY(StdOps.rand(CASING_VEL_Y_MIN, CASING_VEL_Y_MAX));

        this.casingImage = BulletCasing.casingImgs[StdOps.rand(0, BulletCasing.casingImgs.length - 1)];
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

    static {
        casingImgs = Utilities.loadFrames("src/resources/img/objects/casings", 11);
    }

}
