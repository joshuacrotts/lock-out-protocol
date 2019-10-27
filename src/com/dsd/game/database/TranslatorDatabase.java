package com.dsd.game.database;

import com.dsd.game.AccountStatus;
import com.dsd.game.Game;

/**
 * This class, very similar to the TranslatorAPI class, acts as the intermediary
 * between the persistent database and the game itself. The game will contact
 * the translator with information to save, and vice versa for when information
 * needs to be loaded (which will only be once).
 *
 * Information to be saved: - Account info (if applicable): email, password,
 * name - Player inventory - Current level/wave
 *
 * This implementation allows for new Player objects AND levels to be directly
 * instantiated into the code after loading them in from this translator. After
 * parsing through the file, we will load in the previous game's weapons,
 * player's health, etc. into a new game. The background of the wave/level/stage
 * is also saved.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class TranslatorDatabase {

    //  Miscellaneous reference variables.
    private static RemoteDatabase remoteDatabase;
    private static SettingsDatabase settingsDatabase;

    public TranslatorDatabase (Game _game) {
        TranslatorDatabase.remoteDatabase = new PersistentDatabase(_game);
        TranslatorDatabase.settingsDatabase = new SettingsDatabase(_game);
    }

    /**
     * Save the Game state, the level state, the player's health, money, current
     * inventory.
     *
     * @return true if the save to the database was successful, false otherwise.
     */
    public boolean saveToDatabase () {
        return TranslatorDatabase.remoteDatabase.save();
    }

    /**
     * Load the game state, level state, player health, money and current
     * inventory
     *
     * @return true if the load from the database was successful, false otherwise.
     */
    public boolean loadFromDatabase () {
        return TranslatorDatabase.remoteDatabase.load();
    }

    /**
     * Contacts the local settings file and saves the preferred resolution, and
     * language preferences.
     *
     * @return true if successful save, false otherwise.
     */
    public boolean saveToSettings () {
        return settingsDatabase.save();
    }

    /**
     * Contacts the local settings file and loads the preferred resolution, and
     * language preferences.
     *
     * @return true if successful load, false otherwise.
     */
    public boolean loadFromSettings () {
        return settingsDatabase.load();
    }

    /**
     * Contacts the remote SQL database to authenticate a user's password and
     * username. It uses a RemoteDatabase object instead of a PersistentDB
     * object as the parent superclass because all PersistentDB classes should
     * implement the RemoteDatabase interface.
     *
     * @param _email
     * @param _password
     * @return
     */
    public static AccountStatus authenticateUser (String _email, String _password) {
        if (remoteDatabase.connect("users")) {
            return remoteDatabase.userAuthenticated(_email, _password);
        }

        throw new IllegalStateException("Could not connect to db!");
    }

    /**
     * If the user tries to create an account, this method will contact the SQL
     * database and insert them into the db.
     *
     * @param _email
     * @param _password
     * @return
     */
    public static AccountStatus addUser (String _email, String _password) {
        if (remoteDatabase.connect("users")) {
            return remoteDatabase.addUser(_email, _password);
        }

        throw new IllegalStateException("Could not connect to db!");
    }
}
