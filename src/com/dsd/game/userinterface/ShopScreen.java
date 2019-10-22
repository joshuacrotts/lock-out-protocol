package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.userinterface.shop.enums.ShopState;
import com.dsd.game.commands.ShopCommand;
import com.dsd.game.userinterface.shop.ShopTitleLabel;
import com.dsd.game.userinterface.shop.weapons.RifleButton;
import com.dsd.game.userinterface.view.ShopView;
import java.awt.Graphics2D;

/**
 * Pause Screen displays a string "Paused" on the screen when the user presses
 * the escape key.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class ShopScreen extends Screen {

    private ShopState shopState;
    private final ShopView shopView;
    private final ShopCommand shopCommand;

    public ShopScreen (Game _game) {
        super(_game);
        this.shopView = new ShopView(_game);
        this.shopCommand = new ShopCommand(_game);
        this.addInteractor(this.shopView);
        this.shopState = ShopState.WEAPONS;

        this.createUIElements();
    }

    @Override
    public void tick () {
        if (!this.getGame().isShop()) {
            return;
        }
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getGame().isShop()) {
            return;
        }
        super.render(_g2);
    }

    private void createUIElements () {
        super.addInteractor(new ShopTitleLabel(this.getGame(), this));
        super.addInteractor(new RifleButton(this.getGame(), this));
    }

//============================ GETTERS ====================================//
    public boolean isOnWeapons () {
        return this.shopState == ShopState.WEAPONS;
    }

    public boolean isOnItems () {
        return this.shopState == ShopState.ITEMS;
    }

}
