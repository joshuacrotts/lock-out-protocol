package com.dsd.game.objects.weapons;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.enums.WeaponState;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardAudioType;
import java.awt.image.BufferedImage;

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

    //  Miscellaneous reference variables.
    private final Game game;
    private final StandardCollisionHandler globalHandler;
    private final Player player;

    private BufferedImage[] casingImages;

    /**
     * Variables for how much ammo the gun can carry, how much is in a mag, and
     * how much they currently have.
     */
    private final int magazineAmt;
    private int totalAmmo;
    private int currentAmmo;

    //  Sound effects played when the gun is shot, or empty.
    private final String emptySFXPath;
    private final String reloadSFXPath;

    //  Delay for reload command.
    private final long reloadDelay;

    public Gun (WeaponType _type, int _totalAmmo, Game _game, Player _player,
            StandardCollisionHandler _sch, String _reloadSFX, long _reloadDelay) {
        super(_type);
        this.game = _game;
        this.player = _player;
        this.globalHandler = _sch;
        this.currentAmmo = _totalAmmo;
        this.magazineAmt = _totalAmmo;
        this.totalAmmo = _totalAmmo << 2;
        this.emptySFXPath = "src/resources/audio/sfx/empty.wav";
        this.reloadSFXPath = _reloadSFX;
        this.reloadDelay = _reloadDelay;
        super.setWeaponState(WeaponState.READY);
    }

    public Gun (WeaponType _type, int _currAmmo, int _totalAmmo, int magCapacity,
            Game _game, Player _player, StandardCollisionHandler _sch, String _reloadSFX,
            long _reloadDelay) {
        super(_type);
        this.game = _game;
        this.player = _player;
        this.globalHandler = _sch;
        this.currentAmmo = _currAmmo;
        this.magazineAmt = magCapacity;
        this.totalAmmo = _totalAmmo << 2;
        this.emptySFXPath = "src/resources/audio/sfx/empty.wav";
        this.reloadSFXPath = _reloadSFX;
        this.reloadDelay = _reloadDelay;
        super.setWeaponState(WeaponState.READY);
    }

    /**
     * Defines what happens when the gun is shot.
     */
    public abstract void shoot ();

    /**
     * Plays the sound effect associated with the gun type.
     */
    public void playGunShotSFX () {
        StandardAudioController.play("src/resources/audio/sfx/" + this.getWeaponType() + ".wav", StandardAudioType.SFX);
    }

    /**
     * Removes one bullet from the gun.
     */
    public void deductAmmo () {
        this.currentAmmo--;
    }

    /**
     * Reloads a gun using the specified algorithm. This method is called AFTER
     * the sound effect is played; all it does is change the model.
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

    public String getReloadSFXPath () {
        return this.reloadSFXPath;
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

    public long getReloadDelay () {
        return this.reloadDelay;
    }

    public BufferedImage getRandomCasing () {
        return this.casingImages[(int) (Math.random() * this.casingImages.length)];
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

    public void loadCasingImages (int _casingAmount) {
        this.casingImages = Utilities.loadFrames("src/resources/img/objects/casings/" + this.getWeaponType() + "_casings/", _casingAmount);
    }
}
