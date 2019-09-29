/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.objects.Inventory;
import com.dsd.game.userinterface.model.Interactor;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

/**
 * This view draws the border and the user's current weapon to the center of the
 * screen.
 *
 * @author Joshua
 */
public class WeaponIconView extends Interactor {

    private final Game game;
    private final Inventory inventory;
    private final BasicStroke rectBorderStroke;

    private final int iconYOffset = 50;

    public WeaponIconView (Game _game, Inventory _inventory) {
        this.game = _game;
        this.inventory = _inventory;
        this.rectBorderStroke = new BasicStroke(3f);
    }

    @Override
    public void tick () {
    }

    @Override
    public void render (Graphics2D _g2) {
        this.setX((int) (this.game.getCamera().getX()));
        this.setY((int) ((this.game.getCamera().getY() + Screen.gameHalfHeight / 2) + this.iconYOffset));
        this.drawIcon(_g2);
        this.drawRectangleBorder(_g2);

    }

    private void drawIcon (Graphics2D _g2) {
        _g2.drawImage(this.inventory.getCurrentWeapon().getIcon(), this.getX(), this.getY(), null);

    }

    private void drawRectangleBorder (Graphics2D _g2) {
        _g2.setStroke(this.rectBorderStroke);
        _g2.drawRect(this.getX(), this.getY(), this.inventory.getCurrentWeapon().getIconWidth(),
                this.inventory.getCurrentWeapon().getIconHeight());
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
