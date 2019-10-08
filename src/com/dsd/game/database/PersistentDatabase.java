package com.dsd.game.database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class acts as the persistent data store for the game information. Once
 * we get further into the implementation, this class will write all the
 * contents from the game to a file, which will then be uploaded to a SQL
 * database.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class PersistentDatabase {

    private TranslatorDatabase translatorDatabase;
    private BufferedWriter fileWriter;

    public PersistentDatabase (String _fileName) {
        try {
            this.fileWriter = new BufferedWriter(new FileWriter(_fileName));
        }
        catch (IOException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Write the contents of the supplied information to the file.
     */
    public void save () {
        throw new UnsupportedOperationException("Not supported at this time.");
    }

    /**
     * Parse through the file, load the contents, and return as some type of
     * list, perhaps?
     */
    public void load () {
        throw new UnsupportedOperationException("Not supported at this time.");
    }

    /**
     * Connect to the SQL database... when applicable.
     */
    public void connect () {
        throw new UnsupportedOperationException("Not supported at this time.");
    }

    /**
     * Authenticates the user's account in the database. Returns true if correct
     * information is supplied, false otherwise. Probably a very bad idea to do
     * it this way, but we'll cross this bridge in due time.
     *
     * @param _email
     * @param _password
     *
     * @return
     */
    public boolean userAuthenticated (String _email, char[] _password) {
        throw new UnsupportedOperationException("Not supported at this time.");
    }
}
