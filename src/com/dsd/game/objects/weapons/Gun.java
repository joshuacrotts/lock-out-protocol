package com.dsd.game.objects.weapons;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.enums.WeaponState;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.revivedstandards.handlers.StandardCollisionHandler;

/**
 * This class is a template for a gun. In the future, this will be abstract, so
 * other guns can extend it and modify the fields as needed.
 *
 * @TODO: Add bullet damage type.
 *
 * [Group Name: Data Structure Deadheads]
 * @author Joshua, Ronald, Rinty
 */
public abstract class Gun extends Weapon {

    private final Game game;
    private final StandardCollisionHandler globalHandler;
    private final Player player;

    /**
     * Variables for how much ammo the gun can carry, how much is in a mag, and
     * how much they currently have.
     */
    private final int magazineAmt;
    private int totalAmmo;
    private int currentAmmo;

    //  Sound effects played when the gun is shot, or empty.
    private final String emptySFXPath;

    public Gun (WeaponType _type, int _totalAmmo, Game _game, Player _player, StandardCollisionHandler _sch) {
        super(_type);
        this.game = _game;
        this.player = _player;
        this.globalHandler = _sch;
        this.currentAmmo = _totalAmmo;
        this.magazineAmt = _totalAmmo;
        this.totalAmmo = _totalAmmo << 2;
        this.emptySFXPath = "src/resources/audio/sfx/empty.wav";
        super.setWeaponState(WeaponState.READY);
    }

    /**
     * Defines what happens when the gun is shot.
     */
    public abstract void shoot ();

    /**
     * Removes one bullet from the gun.
     */
    public void deductAmmo () {
        this.currentAmmo--;
    }

    /**
     * Reloads a gun using the specified algorithm.
     */
    public void reload () {
        /**
         * Three cases: 1. The magazine is empty and we have enough to fill it
         * 2. The magazine is not empty and we have enough to fill it
         */
        if (this.magExceedsTotalAmmo() && !this.magExceedsGunAmmo()) {
            this.currentAmmo += this.totalAmmo;
            this.totalAmmo = 0;
        }
        else {
            /**
             * If we don't have enough to fill the magazine but the current +
             * total > magazine
             */
            int difference = (this.magazineAmt - this.currentAmmo);
            this.totalAmmo -= difference;
            this.currentAmmo += difference;
        }
    }

    private boolean magExceedsTotalAmmo () {
        return this.totalAmmo < this.magazineAmt;
    }

    private boolean magExceedsGunAmmo () {
        return this.currentAmmo + this.totalAmmo > this.magazineAmt;
    }

//============================== GETTERS ===================================//
    public int getTotalAmmo () {
        return this.totalAmmo;
    }

    public int getCurrentAmmo () {
        return this.currentAmmo;
    }

    public int getMagazineCapacity () {
        return this.magazineAmt;
    }

    public boolean isWeaponEmpty () {
        return this.currentAmmo == 0;
    }

    public boolean hasFullAmmo () {
        return this.currentAmmo == this.magazineAmt;
    }

    public boolean hasAmmo () {
        return this.totalAmmo != 0;
    }

    public String getEmptySFXPath () {
        return this.emptySFXPath;
    }

    public boolean isReloading () {
        return super.getWeaponState() == WeaponState.RELOAD;
    }

    public Player getPlayer () {
        return this.player;
    }

    public StandardCollisionHandler getHandler () {
        return this.globalHandler;
    }

    public Game getGame () {
        return this.game;
    }
//============================== SETTERS ===================================//

    public void setCurrentAmmo (int _ammo) {
        this.currentAmmo = _ammo;
    }

    public void setTotalAmmo (int _ammo) {
        this.totalAmmo = _ammo;
    }

    public void setReloading (boolean _reloading) {
        this.setWeaponState(_reloading ? WeaponState.RELOAD : WeaponState.READY);
    }
}
