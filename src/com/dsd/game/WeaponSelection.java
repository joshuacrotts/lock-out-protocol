/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game;

/**
 *
 * @author Joshua
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
