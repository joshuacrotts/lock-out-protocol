package com.dsd.game.objects;

/**
 * This class is a template for a weapon. In the future, this will be abstract,
 * so other guns can extend it and modify the fields as needed.
 *
 * @TODO: Add bullet damage type.
 *
 * @author Joshua
 */
public class Weapon {

    private final String type;
    private int totalAmmo;
    private int currentAmmo;

    //
    //  Sound effects played when the gun is shot, or empty.
    //
    private final String emptySFXPath;
    private final String gunSFXPath;

    public Weapon (String _type, int _totalAmmo) {
        this.type = _type;
        this.totalAmmo = _totalAmmo;
        this.currentAmmo = this.totalAmmo;
        this.gunSFXPath = "src/res/audio/sfx/" + _type + ".wav";
        this.emptySFXPath = "src/res/audio/sfx/empty.wav";
    }

    public void deductAmmo () {
        this.currentAmmo--;
    }

//============================== GETTERS ===================================//
    public String getWeaponType () {
        return this.type;
    }

    public int getTotalAmmo () {
        return this.totalAmmo;
    }

    public int getCurrentAmmo () {
        return this.currentAmmo;
    }

    public boolean isWeaponEmpty () {
        return this.currentAmmo == 0;
    }

    public String getGunSFXPath () {
        return this.gunSFXPath;
    }

    public String getEmptySFXPath () {
        return this.emptySFXPath;
    }
//============================== SETTERS ===================================//

    public void setCurrentAmmo (int _ammo) {
        this.currentAmmo = _ammo;
    }

}
