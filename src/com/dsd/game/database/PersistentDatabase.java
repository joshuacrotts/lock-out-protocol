package com.dsd.game.database;

import com.dsd.game.AccountStatus;
import com.dsd.game.Game;
import com.dsd.game.util.StdConsole;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private final Game game;
    //  Relative database information (depending on if it's local or not).
    private TranslatorDatabase translatorDatabase;
    private BufferedWriter fileWriter;
    private Connection remoteDBConnection;
    //  SQL Database information.
    private static String IP_ADDRESS;
    private static String USERNAME;
    private static String PASSWORD;

    public PersistentDatabase(Game _game) {
        this.game = _game;
    }

    /**
     * Write the contents of the supplied information to the db type.
     */
    public void save() {
        throw new UnsupportedOperationException("Not supported at this time.");
    }

    /**
     * Parse through the file, load the contents, and return as some type of
     * list, perhaps?
     */
    public void load() {
        throw new UnsupportedOperationException("Not supported at this time.");
    }

    /**
     * Connect to the SQL database... when applicable.
     *
     * @param _dbName
     * @return true if a connection was successful, false otherwise.
     */
    public boolean connect(String _dbName) {
        //  Database NAME (db name in remote sql)
        String instanceID = _dbName;

        this.generateClassName();

        this.loadDatabaseCreds();

        String url = String.format("jdbc:mysql://%s:3306/%s", IP_ADDRESS, instanceID);
        try {
            this.remoteDBConnection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        StdConsole.println(StdConsole.GREEN, "Connection successful!");
        return true;
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
    public AccountStatus userAuthenticated(String _email, String _password) {
        try {
            //  Verify the email
            if (!this.userEmailInDatabase(_email)) {
                return AccountStatus.DOES_NOT_EXIST;
            } //  Verify the email AND the password.
            else if (!this.userInDatabase(_email, _password)) {
                return AccountStatus.INCORRECT_PASS;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return AccountStatus.CORRECT;
    }

    /**
     * Adds a user to the SQL database.
     *
     * @param _email
     * @param _password
     * @return
     */
    public AccountStatus addUser(String _email, String _password) {
        PreparedStatement insertStatement = null;
        try {
            if (this.userInDatabase(_email, _password)) {
                return AccountStatus.EXISTS;
            } else {
                insertStatement = this.remoteDBConnection.prepareStatement(String.format("INSERT INTO user_accounts " + "VALUES(DEFAULT, ?, MD5(?));"));
                insertStatement.setString(1, _email);
                insertStatement.setString(2, _password);
                insertStatement.executeUpdate();
                return AccountStatus.ACCOUNT_CREATED;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Queries the database to see if their username AND password are in the
     * database.
     *
     * @param _email
     * @param _password
     * @return
     * @throws SQLException
     */
    private boolean userInDatabase(String _email, String _password) throws SQLException {
        PreparedStatement insertStatement = null;
        ResultSet resultQuery = null;
        try {
            insertStatement = this.remoteDBConnection.prepareStatement(String.format("SELECT * FROM user_accounts WHERE Email = ? AND Password = MD5(?);"));
            insertStatement.setString(1, _email);
            insertStatement.setString(2, _password);
            resultQuery = insertStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultQuery.next();
    }

    /**
     * Queries the DB to determine if the email is in the db or not.
     *
     * @param _email
     * @return
     * @throws SQLException
     */
    private boolean userEmailInDatabase(String _email) throws SQLException {
        PreparedStatement insertStatement = this.remoteDBConnection.prepareStatement(String.format("SELECT * FROM user_accounts WHERE Email = ?;"));
        insertStatement.setString(1, _email);
        ResultSet resultQuery = insertStatement.executeQuery();

        return resultQuery.next();
    }

    /**
     * Generates the necessary classname to get the MySQL java driver working.s
     */
    private void generateClassName() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Opens the database info file to retrieve the ip address, username, and
     * password.
     */
    private void loadDatabaseCreds() {
        try {
            BufferedReader databaseFile = null;

            try {
                databaseFile = new BufferedReader(new FileReader("src/.config/.database_info.txt"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }

            String ip = databaseFile.readLine();
            PersistentDatabase.IP_ADDRESS = ip.substring(ip.indexOf("=") + 1);

            String user = databaseFile.readLine();
            PersistentDatabase.USERNAME = user.substring(user.indexOf("=") + 1);

            String pswd = databaseFile.readLine();
            PersistentDatabase.PASSWORD = pswd.substring(pswd.indexOf("=") + 1);
            
        } catch (IOException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
