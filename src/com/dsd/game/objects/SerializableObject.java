package com.dsd.game.objects;

import com.dsd.game.database.SerializableType;

/**
 * This class defines the basic CRUD operations for denoting how an object is to
 * be saved to the database; whichever that may be (detailed in the DBTranslator
 * class). Not all objects are serializable (in fact, MOST won't be; only the
 * Player, the level, and perhaps the inventory should directly be serializable,
 * but this may very well change as time goes on).
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/12/19
 */
public interface SerializableObject {

    public String createObject(SerializableType _id);

    public void updateObject(SerializableType _obj);

    public void destroyObject(SerializableType _obj);
}
