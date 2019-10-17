package com.dsd.game;

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

    public String createObject ();

    public SerializableObject readObject (SerializableObject _obj);

    public void updateObject (SerializableObject _obj);

    public void destroyObject (SerializableObject _obj);
}
