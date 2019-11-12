package com.dsd.game.objects.weapons;

import com.dsd.game.objects.SerializableObject;
import com.dsd.game.database.SerializableType;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.enums.WeaponState;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.util.StdOps;
import java.awt.image.BufferedImage;

/**
 * This class is a template for a weapon. All weapons will extend this class.
 * This class includes melee weapons and guns.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public abstract class Weapon implements SerializableObject {

    //  Type of weapon, standard animator controllers, the icon (for the inventoryview)
    private WeaponType weaponType;
    private StandardAnimatorController walkWeaponFrames;
    private StandardAnimatorController attackWeaponFrames;
    private BufferedImage weaponIcon;
    //  State of the weapon (when applicable), and the sfx it makes when attacking.
    private WeaponState weaponState;
    private String attackSFXPath;
    //  Variables to determine how long to wait in between attacks.
    private long delay = 0;
    private boolean ready = true;

    /**
     * Some weapons will have damage that the actual weapons do as opposed to
     * just a projectile exiting the weapon (like a bullet).
     */
    private int damage;

    //  Some powerups will increase the weapon's damage. This variable handles that.
    private int damageFactor = 1;

    public Weapon (WeaponType _type) {
        this.weaponType = _type;
        this.weaponState = WeaponState.READY;
        this.setSFXPath("src/resources/audio/sfx/" + _type + ".wav");
        this.setIcon(StdOps.loadImage("src/resources/img/items/icons/" + _type.getType() + "_icon.png"));

    }

    /**
     * Determine if the weapon is ready to attack or not depending on the delay.
     *
     * @return
     */
    public boolean ready () {
        return this.ready;
    }

    public abstract void loadAssets (Player _player);

    @Override
    public String createObject (SerializableType _id) {
        StringBuilder object = new StringBuilder();
        object.append("weapon=").append("\n");
        object.append("type=").append(this.weaponType).append("\n");
        object.append("gun=").append(this instanceof Gun).append("\n");
        if (this instanceof Gun) {
            Gun gun = (Gun) this;
            object.append("ammo=").append(gun.getCurrentAmmo()).append("\n");
            object.append("total=").append(gun.getTotalAmmo()).append("\n");
            object.append("mag=").append(gun.getMagazineCapacity()).append("\n");
        }
        return object.toString();
    }

    public void readObject () {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateObject (SerializableType _id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroyObject (SerializableType _id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//============================== GETTERS ===================================//
    public long getDelay () {
        return this.delay;
    }

    public WeaponType getWeaponType () {
        return this.weaponType;
    }

    public WeaponState getWeaponState () {
        return this.weaponState;
    }

    public StandardAnimatorController getWalkFrames () {
        return this.walkWeaponFrames;
    }

    public StandardAnimatorController getAttackFrames () {
        return this.attackWeaponFrames;
    }

    public BufferedImage getIcon () {
        return this.weaponIcon;
    }

    public int getIconWidth () {
        return this.weaponIcon.getWidth();
    }

    public int getIconHeight () {
        return this.weaponIcon.getHeight();
    }

    public String getSFXPath () {
        return this.attackSFXPath;
    }

    public int getDamage () {
        return this.damage;
    }

    public int getDamageFactor () {
        return this.damageFactor;
    }

//============================== SETTERS ===================================//
    public void setWeaponState (WeaponState _state) {
        this.weaponState = _state;
    }

    public void setIcon (BufferedImage _image) {
        this.weaponIcon = _image;
    }

    public void setSFXPath (String _sfx) {
        this.attackSFXPath = _sfx;
    }

    protected void setWalkFrames (StandardAnimatorController _walkFrames) {
        this.walkWeaponFrames = _walkFrames;
    }

    protected void setAttackFrames (StandardAnimatorController _attackFrames) {
        this.attackWeaponFrames = _attackFrames;
    }

    public void setDamage (int _damage) {
        this.damage = _damage;
    }

    public void setDelay (long _delay) {
        this.delay = _delay;
    }

    public void setReady (boolean _ready) {
        this.ready = _ready;
    }

    public void setDamageFactor (int _factor) {
        this.damageFactor = _factor;
    }
}
