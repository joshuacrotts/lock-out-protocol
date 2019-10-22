package com.dsd.game.objects;

import com.dsd.game.Game;
import com.dsd.game.api.WeatherConnector;
import com.dsd.game.objects.weapons.FastRifle;
import com.dsd.game.objects.weapons.GrenadeLauncher;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.Knife;
import com.dsd.game.objects.weapons.Pistol;
import com.dsd.game.objects.weapons.Rifle;
import com.dsd.game.objects.weapons.Shotgun;
import com.dsd.game.objects.weapons.Weapon;
import com.dsd.game.objects.weapons.enums.WeaponSelection;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.view.InventoryView;
import com.revivedstandards.handlers.StandardCollisionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Model representing the player's current inventory.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class Inventory {

    //  Miscellaneous reference variables
    private final Game game;
    private final Player player;
    private final StandardCollisionHandler parentHandler;
    private final InventoryView view;
    private final List<Weapon> weapons;

    private int currentWeapon = 0;
    private boolean hasGun;

    public Inventory (Game _game, Player _player, StandardCollisionHandler _sch) {
        this.game = _game;
        this.player = _player;
        this.weapons = new ArrayList<>();
        this.parentHandler = _sch;

        //  This will change with time (to a subclass of Weapon).
        this.weapons.add(new Knife(_player));
        this.weapons.add(new Pistol(_game, _player, _sch));
        this.weapons.add(new Rifle(_game, _player, _sch));
        this.weapons.add(new FastRifle(_game, _player, _sch));
        this.weapons.add(new Shotgun(_game, _player, _sch));
        this.weapons.add(new GrenadeLauncher(_game, _player, _sch));

        this.view = new InventoryView(this.game, this);
    }

    /**
     * Either increments or decrements the weapon pointer, depending on what
     * button the user pressed.
     *
     * @param choice
     */
    public void changeWeapon (WeaponSelection choice) {
        this.currentWeapon += choice.getChange();
        this.clampWeapon();
        this.updateAnimation();
        this.hasGun = this.weapons.get(this.currentWeapon) instanceof Gun;
    }

    /**
     * When the user changes their sex, we need to reload the weapon assets
     * since they're bound to the player's sex.
     */
    public void reloadInventoryAssets () {
        for (int i = 0 ; i < this.weapons.size() ; i++) {
            this.weapons.get(i).loadAssets(this.player);
        }
    }

    /**
     * Checks the inventory to see if the player has this specific weapon.
     *
     * @param _type
     * @return
     */
    public Weapon hasWeapon (WeaponType _type) {
        for (int i = 0 ; i < this.weapons.size() ; i++) {
            Weapon weapon = this.weapons.get(i);
            if (weapon.getWeaponType() == _type) {
                return weapon;
            }
        }

        return null;
    }

    /**
     * Adds a weapon to the inventory. This is mainly used in the shop classes.
     *
     * @param _weapon
     */
    public void addWeapon (Weapon _weapon) {
        if (hasWeapon(_weapon.getWeaponType()) == null) {
            this.weapons.add(_weapon);
        }
    }

    /**
     * Clamps the pointer for the current weapon to values in the arraylist.
     */
    private void clampWeapon () {
        if (this.currentWeapon < 0) {
            this.currentWeapon = this.weapons.size() - 1;
        }
        else if (this.currentWeapon >= this.weapons.size()) {
            this.currentWeapon = 0;
        }
    }

    /**
     * Updates the animation set by the player determined by which weapon they
     * have.
     */
    private void updateAnimation () {
        if (this.player.isWalking() || this.player.isStanding()) {
            this.player.setAnimation(this.weapons.get(this.currentWeapon).getWalkFrames());
        }
        else if (this.player.isAttacking()) {
            this.player.setAnimation(this.weapons.get(this.currentWeapon).getAttackFrames());
        }

        this.player.setAttackAnimator(this.weapons.get(this.currentWeapon).getAttackFrames());
    }

    /**
     * When the user resets the game, their inventory needs to be reset.
     */
    public void resetInventory () {
        this.weapons.clear();

        this.weapons.add(new Knife(this.player));
        this.weapons.add(new Pistol(this.game, this.player, this.parentHandler));
    }

//============================= GETTERS ===================================//
    public Weapon getCurrentWeapon () {
        return weapons.get(this.currentWeapon);
    }

    public Gun getGun () {
        Gun gun = null;

        try {
            gun = (Gun) this.getCurrentWeapon();
        }
        catch (ClassCastException ex) {
            Logger.getLogger(WeatherConnector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return gun;
    }

    public InventoryView getView () {
        return this.view;
    }

    public boolean hasGun () {
        return this.hasGun;
    }
}
