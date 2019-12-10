package com.dsd.game.core;

/**
 * This enum defines all possible objects/classes that are, by definition,
 * serializable. Any serializable type must implement the SerializableObject
 * interface and override its CRUD operations so the database can call them.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public enum SerializableType {
    
    PLAYER, LEVEL;
    
}
