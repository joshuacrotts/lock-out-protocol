package com.dsd.game.userinterface;

import com.dsd.game.Game;

/**
 * This class will represent the shop screen.
 *
 * @author Joshua
 */
public class ShopScreen extends Screen {

    private boolean isActive = false;

    public ShopScreen (Game _game) {
        super(_game);
    }

    public boolean isActive () {
        return this.isActive;
    }

    public void setActive (boolean _active) {
        this.isActive = _active;
    }

}
