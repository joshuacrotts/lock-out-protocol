package com.dsd.game;

import com.dsd.game.database.SerializableType;

/**
 * This class defines the basic CRUD operations for denoting how an object is to
 * be saved to the database; whichever that may be (detailed in the DBTranslator
 * class). Not all objects are going to be serializable (in fact, MOST of the
 * won't be; only the Player, the level, and perhaps the inventory should
 * directly be serializable, but this may very well change as time goes on).
 *
 * @author Joshua
 */
public interface SerializableObject {

    public String createObject (SerializableType _id);

    public void updateObject (SerializableType _obj);

    public void destroyObject (SerializableType _obj);
}
