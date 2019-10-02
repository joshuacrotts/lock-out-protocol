package com.dsd.game;

/**
 * To avoid the whole fiasco with using instanceof when it's unwarranted and
 * kind of ugly, we can define an enum of weapon types.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public enum WeaponType {
    RIFLE("rifle"), PISTOL("pistol"), KNIFE("knife"), BAT("bat"), FLAMETHROWER("flamethrower");

    private final String type;

    private WeaponType (String _type) {
        this.type = _type;
    }

    public String getType () {
        return this.type;
    }

    @Override
    public String toString () {
        return this.type;
    }
}
