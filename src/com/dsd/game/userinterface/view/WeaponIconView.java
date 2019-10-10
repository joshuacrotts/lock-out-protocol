package com.dsd.game.userinterface.view;

import com.dsd.game.Game;
import com.dsd.game.objects.Inventory;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This view draws the border and the user's current weapon to the center of the
 * screen.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class WeaponIconView extends Interactor {

    private final Game game;
    private final Inventory inventory;
    private final BufferedImage weaponBorder;
    private final int ICON_X_OFFSET = 310;
    private final int ICON_Y_OFFSET = 110;

    public WeaponIconView (Game _game, Inventory _inventory) {
        this.game = _game;
        this.inventory = _inventory;
        this.weaponBorder = StdOps.loadImage("src/resources/img/items/icons/item_holder.png");
    }

    @Override
    public void tick () {}

    @Override
    public void render (Graphics2D _g2) {
        this.setX((int) (this.game.getCamera().getX() + Screen.gameHalfWidth - this.ICON_X_OFFSET));
        this.setY((int) ((this.game.getCamera().getY() + Screen.gameHalfHeight) - this.ICON_Y_OFFSET));
        this.drawWeaponBorder(_g2);
        this.drawIcon(_g2);
    }

    /**
     * Draws the background behind the actual icon of the weapon.
     *
     * @param _g2
     */
    private void drawWeaponBorder (Graphics2D _g2) {
        _g2.drawImage(this.weaponBorder, this.getX(), this.getY(), null);
    }

    /**
     * Draws the icon of the weapon.
     *
     * @param _g2
     */
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
