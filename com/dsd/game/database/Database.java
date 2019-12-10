package com.dsd.game.database;

/**
 * This class acts as the barebones template of any persistent database this
 * program uses.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public interface Database {

    public boolean save();

    public boolean load();
    
}
