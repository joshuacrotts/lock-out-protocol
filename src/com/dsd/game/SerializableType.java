package com.dsd.game;

/**
 * This enum defines all possible objects/classes that are, by definition,
 * serializable. Any serializable type must implement the SerializableObject
 * interface and override its CRUD operations so the database can call them.
 *
 * @author Joshua
 */
public enum SerializableType {
    
    PLAYER, LEVEL;
}
