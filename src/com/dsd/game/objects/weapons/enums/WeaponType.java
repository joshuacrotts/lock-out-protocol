package com.dsd.game.objects.weapons.enums;

/**
 * To avoid the whole fiasco with using instanceof when it's unwarranted and
 * kind of ugly, we can define an enum of weapon types.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public enum WeaponType {
    RIFLE("rifle"),
    SHOTGUN("shotgun"),
    PISTOL("pistol"),
    KNIFE("knife"),
    BAT("bat"),
    GRENADE_LAUNCHER("grenade_launcher"),
    FLAMETHROWER("flamethrower");

    private final String type;

    private WeaponType (String _type) {
        this.type = _type;
    }

    @Override
    public String toString () {
        return this.type;
    }

//======================= GETTERS =======================//
    public String getType () {
        return this.type;
    }
}
