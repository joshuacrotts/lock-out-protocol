package com.dsd.game.objects;

import com.dsd.game.Game;
import com.dsd.game.SerializableObject;
import com.dsd.game.api.WeatherConnector;
import com.dsd.game.database.SerializableType;
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
public class Inventory implements SerializableObject {

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
        if (this.hasWeapon(_weapon.getWeaponType()) == null) {
            this.weapons.add(_weapon);
        }
    }

    public void addWeapon (WeaponType _type) {
        if (this.hasWeapon(_type) == null) {
            switch (_type) {
                case PISTOL:
                    this.weapons.add(new Pistol(game, player, parentHandler));
                    break;
                case RIFLE:
                    this.weapons.add(new Rifle(game, player, parentHandler));
                    break;
                case FAST_RIFLE:
                    this.weapons.add(new FastRifle(game, player, parentHandler));
                    break;
                case SHOTGUN:
                    this.weapons.add(new Shotgun(game, player, parentHandler));
                    break;
                case GRENADE_LAUNCHER:
                    this.weapons.add(new GrenadeLauncher(game, player, parentHandler));
                    break;

            }
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

//============================== CRUD OPERATIONS =============================//
    @Override
    public String createObject (SerializableType _id) {
        StringBuilder inventoryDetails = new StringBuilder();
        Weapon[] weaponStatuses = this.hasWeapons();

        for (int i = 0 ; i < weaponStatuses.length ; i++) {
            inventoryDetails.append(weaponStatuses[i] != null ? 1 : 0).append(";");
            if (weaponStatuses[i] != null) {
                Gun gun = (Gun) weaponStatuses[i];
                inventoryDetails.append(gun.getCurrentAmmo()).append(";");
                inventoryDetails.append(gun.getTotalAmmo()).append(";");
            }
            else {
                inventoryDetails.append("0;0;");
            }
        }

        return inventoryDetails.toString();
    }

    public void readObject (int _hasPistol, int _pistolAmmo, int _pistolTotalAmmo,
            int _hasRifle, int _rifleAmmo, int _rifleTotalAmmo,
            int _hasFastRifle, int _fastRifleAmmo, int _fastRifleTotalAmmo,
            int _hasShotgun, int _shotgunAmmo, int _shotgunTotalAmmo,
            int _hasGrenadeLauncher, int _grenadeLauncherAmmo, int _grenadeLauncherTotalAmmo) {

        this.loadWeaponFromDB(_hasPistol, WeaponType.PISTOL, _pistolAmmo, _pistolTotalAmmo);
        this.loadWeaponFromDB(_hasRifle, WeaponType.RIFLE, _rifleAmmo, _rifleTotalAmmo);
        this.loadWeaponFromDB(_hasFastRifle, WeaponType.FAST_RIFLE, _fastRifleAmmo, _fastRifleTotalAmmo);
        this.loadWeaponFromDB(_hasShotgun, WeaponType.SHOTGUN, _shotgunAmmo, _shotgunTotalAmmo);
        this.loadWeaponFromDB(_hasGrenadeLauncher, WeaponType.GRENADE_LAUNCHER, _grenadeLauncherAmmo, _grenadeLauncherTotalAmmo);
    }

    @Override
    public void updateObject (SerializableType _obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroyObject (SerializableType _obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Determines which of the n weapons the player has.
     *
     * @return
     */
    private Weapon[] hasWeapons () {
        Weapon[] typesOfWeapons = new Weapon[5];

        typesOfWeapons[0] = this.hasWeapon(WeaponType.PISTOL);
        typesOfWeapons[1] = this.hasWeapon(WeaponType.RIFLE);
        typesOfWeapons[2] = this.hasWeapon(WeaponType.FAST_RIFLE);
        typesOfWeapons[3] = this.hasWeapon(WeaponType.SHOTGUN);
        typesOfWeapons[4] = this.hasWeapon(WeaponType.GRENADE_LAUNCHER);

        return typesOfWeapons;
    }

    /**
     * Loads a weapon in from the database.
     *
     * @param _hasWeapon boolean variable (1 for if they had it previously, 0
     * otherwise).
     * @param _id
     * @param _currAmmo
     * @param _totalAmmo
     */
    private void loadWeaponFromDB (int _hasWeapon, WeaponType _id, int _currAmmo, int _totalAmmo) {
        if (_hasWeapon == 0) {
            return;
        }
        else {
            Gun gun = (Gun) this.hasWeapon(_id);
            if (gun == null) {
                this.addWeapon(_id);
            }
            gun = (Gun) this.hasWeapon(_id);
            gun.setCurrentAmmo(_currAmmo);
            gun.setTotalAmmo(_totalAmmo);
        }
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
