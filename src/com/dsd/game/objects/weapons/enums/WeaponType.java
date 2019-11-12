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
    GRENADE_LAUNCHER("grenade_launcher"),
    //  PPSH-41 equivalent.
    FAST_RIFLE("fast_rifle"),
    MINIGUN("minigun"),
    SUPER_SHOTGUN("sshotgun");

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
