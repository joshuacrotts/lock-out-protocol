package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.commands.ShopCommand;
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

    private final ShopView shopView;
    private final ShopCommand shopCommand;

    public ShopScreen (Game _game) {
        super(_game);
        this.shopView = new ShopView(_game);
        this.shopCommand = new ShopCommand(_game);
        this.addInteractor(this.shopView);
    }

    @Override
    public void tick () {
        if (this.getGame().isMenu()) {
            return;
        }
        this.shopView.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.getGame().isMenu()) {
            return;
        }
        this.shopView.render(_g2);
    }
}
