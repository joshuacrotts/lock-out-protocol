package com.dsd.game.objects.weapons.enums;

/**
 * Enums for determining how the player wants to switch their weapon (forwards
 * or backwards)
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public enum WeaponSelection {

    DECREMENT(-1), INCREMENT(1);
    private final int change;

    private WeaponSelection (int change) {
        
        this.change = change;
    }

//================= GETTERS ============================
    public int getChange () {
        
        return this.change;
    }
    
}
