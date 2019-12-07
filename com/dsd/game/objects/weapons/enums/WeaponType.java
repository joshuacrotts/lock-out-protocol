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
    PISTOL("Pistol"),
    RIFLE("Rifle"),
    FAST_RIFLE("FastRifle"),
    SHOTGUN("Shotgun"),
    GRENADE_LAUNCHER("GrenadeLauncher"),
    MINIGUN("Minigun"),
    SUPER_SHOTGUN("SuperShotgun");

    private final String type;

    private WeaponType(String _type) {
        this.type = _type;
    }

    /**
     * Constructs a StringBuilder object for every weapon, accounting for their
     * total ammo, current ammo and the weapon type.
     *
     * Used in the persistent database.
     */
    public static StringBuilder buildInventoryString() {
        StringBuilder weapons = new StringBuilder();

        for (WeaponType value : WeaponType.values()) {
            String currType = value.getType();
            weapons.append(currType).append(", ");
            weapons.append(currType).append("Ammo").append(", ");
            weapons.append(currType).append("TotalAmmo").append(", ");
        }

        return weapons;
    }

    @Override
    public String toString() {
        return this.type;
    }

//======================= GETTERS =======================//
    public String getType() {
        return this.type;
    }
}
