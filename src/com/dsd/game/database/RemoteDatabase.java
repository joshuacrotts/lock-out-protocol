package com.dsd.game.database;

import com.dsd.game.AccountStatus;

/**
 * This class is a template for all classes that are a remote database to
 * implement. For instance, if we change the PersistentDatabase class from a
 * MySQL database to something else, it needs to implement this interface and
 * rewrite the methods appropriate to that class (or version of SQL).
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public interface RemoteDatabase {

    /**
     * Opens the connection with whatever library is necessary to connect to the
     * SQL database _dbName. For MySQL, for example, we use the JDBC library.
     * Other SQL databases may need to use something different.
     *
     * @param _dbName name of remote database (instanceID).
     *
     * @return true if a connection was successfully established. False
     * otherwise.
     */
    public boolean connect(String _dbName);

    /**
     * Write the contents of the supplied information to the db type.
     * @return true if it saved successfully, false otherwise.
     */
    public boolean save();

    /**
     * Contacts the database and, with the account information, returns the
     * player's information in a parse-able way.
     * @return true if loaded info successfully, false otherwise.
     */
    public boolean load();

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
    public AccountStatus userAuthenticated(String _email, String _password);

    /**
     * Adds a user to the SQL database.
     *
     * @param _email
     * @param _password
     * @return if the account exists, was created, or null for neither.
     */
    public AccountStatus addUser(String _email, String _password);

}
