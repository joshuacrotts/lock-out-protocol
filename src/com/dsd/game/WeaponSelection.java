package com.dsd.game;

/**
 * Enums for determining how the player wants to switch their weapon (forwards
 * or backwards)
 *
 * [Group Name: Data Structure Deadheads]
 * @author Joshua, Ronald, Rinty 
 */
public enum WeaponSelection {

    DECREMENT(-1), INCREMENT(1);

    private final int change;

    private WeaponSelection (int change) {
        this.change = change;
    }

    public int getChange () {
        return this.change;
    }
}
