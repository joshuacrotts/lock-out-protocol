/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.objects.Inventory;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This view draws the border and the user's current weapon to the center of the
 * screen.
 *
 * @author Joshua
 */
public class WeaponIconView extends Interactor {

    private final Game game;
    private final Inventory inventory;
    private final BufferedImage weaponBorder;

    private final int iconXOffset = 300;
    private final int iconYOffset = 70;

    public WeaponIconView (Game _game, Inventory _inventory) {
        this.game = _game;
        this.inventory = _inventory;
        this.weaponBorder = StdOps.loadImage("src/res/img/items/icons/item_holder.png");
    }

    @Override
    public void tick () {
    }

    @Override
    public void render (Graphics2D _g2) {

        this.setX((int) (this.game.getCamera().getX() + Screen.gameHalfWidth - this.iconXOffset));
        this.setY((int) ((this.game.getCamera().getY() + Screen.gameHalfHeight / 2) + this.iconYOffset));

        this.drawWeaponBorder(_g2);
        this.drawIcon(_g2);
    }

    public void drawWeaponBorder (Graphics2D _g2) {
        _g2.drawImage(this.weaponBorder, this.getX(), this.getY(), null);
    }

    private void drawIcon (Graphics2D _g2) {
        _g2.drawImage(this.inventory.getCurrentWeapon().getIcon(), this.getX(), this.getY(), null);

    }

    @Override
    public void onMouseClick () {
    }

    @Override
    public void onMouseEnterHover () {
    }

    @Override
    public void onMouseExitHover () {
    }
}
