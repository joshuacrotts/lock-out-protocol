/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.shop.weapons;

import com.dsd.game.Game;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.model.Interactor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joshua
 */
public abstract class ShopButton extends Interactor {

    private final Game game;
    private final ShopScreen shopScreen;

    private BufferedImage iconImage;

    private static final int BUTTON_WIDTH = 198;
    private static final int BUTTON_HEIGHT = 145;

    public ShopButton (Game _game, ShopScreen _shopScreen, int x, int y) {
        super(x, y);
        this.game = _game;
        this.shopScreen = _shopScreen;
        this.setScaled(true);
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.drawImage(this.iconImage, this.getX(), this.getY(), null);
        _g2.setColor(Color.red);
        _g2.fill(new Rectangle(this.getX(), this.getY(), BUTTON_WIDTH, BUTTON_HEIGHT));
    }

    @Override
    public void tick () {
    }

//=============================== GETTERS ====================================//
    public Game getGame () {
        return this.game;
    }

//=============================== SETTERS ====================================//
    public void setImage (BufferedImage _image) {
        this.iconImage = _image;
    }

}
