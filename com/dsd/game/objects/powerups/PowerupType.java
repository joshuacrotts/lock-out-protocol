package com.dsd.game.objects.powerups;

/**
 * This class is a simple enum that defines a string representation of each
 * power-up, including the coins.s
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 12/3/19
 */
public enum PowerupType {
    
    HEALTH("HEALTH"),
    INFINITE_AMMO("INFINITE AMMO"),
    BERSERK("BERSERK"),
    COIN("COIN");

    private final String label;

    private PowerupType(String _label) {
        this.label = _label;
    }

    @Override
    public String toString() {
        return this.label;
    }
    
}
