package com.dsd.game.userinterface.view;

import com.dsd.game.Game;
import com.dsd.game.objects.Inventory;
import com.dsd.game.userinterface.Screen;
import java.awt.Graphics2D;

/**
 * This class is a view representation of what the user currently has in their
 * inventory. It displays the current weapon in their hand.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class InventoryView extends Screen {

    //  Miscellaneous reference to the inventory.
    private final Inventory inventory;

    public InventoryView (Game _game, Inventory _inventory) {
        super(_game);
        this.inventory = _inventory;
        this.createUIElements();
    }

    @Override
    public void tick () {
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }

    private void createUIElements () {
        super.addInteractor(new WeaponIconView(this.getGame(), this.inventory));
    }

}
