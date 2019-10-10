package com.dsd.game.database;

import com.dsd.game.AccountStatus;
import java.io.BufferedWriter;
import java.io.FileWriter;
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

    private TranslatorDatabase translatorDatabase;
    private BufferedWriter fileWriter;
    private Connection remoteDBConnection;

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
     *
     * @return true if a connection was successful, false otherwise.
     */
    public boolean connect () {
        //  SQL Database information
        String ipAddress = "35.226.95.88";
        //  Database NAME (db name in remote sql)
        String instanceID = "users";
        String dbUsername = "root";
        String rootPswd = "lockoutprotocol340";

        this.generateClassName();

        String url = String.format("jdbc:mysql://%s:3306/%s", ipAddress, instanceID);
        try {
            this.remoteDBConnection = DriverManager.getConnection(url, dbUsername, rootPswd);
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        System.out.println("Connection successful!");
        return true;
    }

    /**
     * Generates the necessary classname to get the MySQL java driver working.s
     */
    private void generateClassName () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public AccountStatus userAuthenticated (String _email, String _password) {
        PreparedStatement insertStatement = null;
        ResultSet resultQuery = null;
        try {
            //  Verify the email
            insertStatement = this.remoteDBConnection.prepareStatement(String.format("SELECT * FROM user_accounts WHERE Email = ?;"));
            insertStatement.setString(1, _email);
            resultQuery = insertStatement.executeQuery();

            if (!resultQuery.next()) {
                return AccountStatus.DOES_NOT_EXIST;
            }

            //  Verify the email AND the password.
            insertStatement = this.remoteDBConnection.prepareStatement(String.format("SELECT * FROM user_accounts WHERE Email = ? AND Password = MD5(?);"));
            insertStatement.setString(1, _email);
            insertStatement.setString(2, _password);
            resultQuery = insertStatement.executeQuery();
            if (!resultQuery.next()) {
                return AccountStatus.INCORRECT_PASS;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return AccountStatus.EXISTS;
    }

    /**
     * Adds a user to the SQL database.
     *
     * @param _email
     * @param _password
     */
    public void addUser (String _email, String _password) {
        PreparedStatement insertStatement = null;
        try {
            insertStatement = this.remoteDBConnection.prepareStatement(String.format("INSERT INTO user_accounts " + "VALUES(DEFAULT, ?, MD5(?));"));
            insertStatement.setString(1, _email);
            insertStatement.setString(2, _password);
            insertStatement.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("User added.");
    }
}
