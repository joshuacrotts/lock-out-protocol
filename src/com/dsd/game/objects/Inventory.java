package com.dsd.game.objects;

import com.dsd.game.Game;
import com.dsd.game.userinterface.InventoryView;

/**
 * Model representing the player's current inventory.
 *
 * @author Joshua
 */
public class Inventory {

    private final Game game;
    private final Player player;
    private final InventoryView view;

    private Weapon currentWeapon;

    public Inventory (Game _game, Player _player) {
        this.game = _game;
        this.player = _player;
        this.currentWeapon = new Weapon("pistol", 16);
        this.view = new InventoryView(this.game);
    }

//============================= GETTERS ===================================//
    public Weapon getCurrentWeapon () {
        return this.currentWeapon;
    }

    public InventoryView getView () {
        return this.view;
    }

//============================= SETTERS ===================================//
    public void setCurrentWeapon (Weapon _weapon) {
        this.currentWeapon = _weapon;
    }
}
