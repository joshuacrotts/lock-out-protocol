package com.dsd.game.database;

import com.dsd.game.AccountStatus;
import com.dsd.game.Game;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

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
public class PersistentDatabase implements RemoteDatabase {

    private final Game game;

    //  Relative database information (depending on if it's local or not).
    private TranslatorDatabase translatorDatabase;
    private BufferedWriter fileWriter;
    private Connection remoteDBConnection;

    //  Unique ID for each user in the database.
    private int connectedUserID = -1;

    //  SQL Database information.
    private static String IP_ADDRESS;
    private static String USERNAME;
    private static String PASSWORD;

    public PersistentDatabase (Game _game) {
        this.game = _game;
    }

    /**
     * Write the contents of the supplied information to the db type.
     */
    @Override
    public boolean save () {
        String playerInfo = this.game.getPlayer().createObject(SerializableType.PLAYER);
        String inventoryInfo = this.game.getPlayer().getInventory().createObject(SerializableType.INVENTORY);
        String levelInfo = this.game.getLevelController().createObject(SerializableType.WAVE_INFO);
        String waveInfo = this.game.getDifficultyController().createObject(SerializableType.LEVEL);

        //  Parse through player info
        this.uploadPlayerInfo(playerInfo.split(";"));
        this.uploadInventoryInfo(inventoryInfo.split(";"));
        this.uploadLevelInfo(levelInfo.split(";"));
        this.uploadWaveInfo(waveInfo.split(";"));

        return true;
    }

    /**
     * Uploads the player info to the SQL server.
     *
     * @param _playerData
     */
    private void uploadPlayerInfo (String[] _playerData) {
        PreparedStatement updatePlayerQuery = null;

        try {
            updatePlayerQuery = this.remoteDBConnection.prepareStatement(String.format("UPDATE user_accounts SET PlayerX = ?, PlayerY = ?, Money = ?, Health = ? WHERE UserID = ? ;"));
            for (int i = 0 ; i < _playerData.length ; i++) {
                updatePlayerQuery.setInt(i + 1, Integer.parseInt(_playerData[i]));
            }
            updatePlayerQuery.setInt(_playerData.length + 1, this.connectedUserID);
            updatePlayerQuery.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Uploads the inventory information to the SQL server.
     *
     * @param _inventoryData
     */
    private void uploadInventoryInfo (String[] _inventoryData) {
        PreparedStatement updateInventoryQuery = null;

        try {
            updateInventoryQuery = this.remoteDBConnection.prepareStatement(String.format("UPDATE user_accounts SET Pistol = ?, PistolAmmo = ?, PistolTotalAmmo = ?, Rifle = ?, RifleAmmo = ?, RifleTotalAmmo = ?, FastRifle = ?, FastRifleAmmo = ?, FastRifleTotalAmmo = ?, Shotgun = ?, ShotgunAmmo = ?, ShotgunTotalAmmo = ?, GrenadeLauncher = ?, GrenadeLauncherAmmo = ?, GrenadeLauncherTotalAmmo = ? WHERE UserID = ?;"));

            for (int i = 0 ; i < _inventoryData.length ; i++) {
                updateInventoryQuery.setInt(i + 1, Integer.parseInt(_inventoryData[i]));
            }

            updateInventoryQuery.setInt(_inventoryData.length + 1, this.connectedUserID);
            updateInventoryQuery.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Uploads the level information to the SQL database.
     *
     * @param _levelData
     */
    private void uploadLevelInfo (String[] _levelData) {
        PreparedStatement updateLevelQuery = null;

        try {
            updateLevelQuery = this.remoteDBConnection.prepareStatement(String.format("UPDATE user_accounts SET LevelID = ?, Wave = ? WHERE UserID = ?;"));

            for (int i = 0 ; i < _levelData.length ; i++) {
                updateLevelQuery.setInt(i + 1, Integer.parseInt(_levelData[i]));
            }

            updateLevelQuery.setInt(_levelData.length + 1, this.connectedUserID);
            updateLevelQuery.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Uploads the wave information to the SQL database.
     *
     * @param _levelData
     */
    private void uploadWaveInfo (String[] _waveInfo) {
        PreparedStatement updateLevelQuery = null;

        try {
            updateLevelQuery = this.remoteDBConnection.prepareStatement(String.format("UPDATE user_accounts SET LevelTransitionTimer = ?, DifficultyFactor = ? WHERE UserID = ?;"));
            updateLevelQuery.setInt(1, Integer.parseInt(_waveInfo[0]));
            updateLevelQuery.setDouble(2, Double.parseDouble(_waveInfo[1]));
            updateLevelQuery.setInt(_waveInfo.length + 1, this.connectedUserID);

            updateLevelQuery.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Parse through the file, load the contents, and return as some type of
     * list, perhaps?
     *
     * @return true if the load was successful, false otherwise.
     */
    @Override
    public boolean load () {
        PreparedStatement playerStatsQuery = null;

        try {
            //  Perform the query to retrieve the information about the player
            //  and their inventory from the SQL db.
            playerStatsQuery = this.remoteDBConnection.prepareStatement(String.format("SELECT Sex, PlayerX, PlayerY, Money, Health, Pistol, PistolAmmo, PistolTotalAmmo, Rifle, RifleAmmo, RifleTotalAmmo, FastRifle, FastRifleAmmo, FastRifleTotalAmmo, Shotgun, ShotgunAmmo, ShotgunTotalAmmo, GrenadeLauncher, GrenadeLauncherAmmo, GrenadeLauncherTotalAmmo, LevelID, Wave, LevelTransitionTimer, DifficultyFactor FROM user_accounts WHERE UserID = ?;"));
            playerStatsQuery.setInt(1, this.connectedUserID);
            ResultSet playerStatsSet = playerStatsQuery.executeQuery();

            //  If there is no information for whatever reason, return false.
            if (!playerStatsSet.next()) {
                return false;
            }

            //  I'm sure there's a MUCH more elegant way to do this, but we'll
            //  optimize the crap out of it later (or at the very LEAST encapsulate
            //  some of this into other methods.
            //  Firstly, we declare arraylists to hold the values retrieved from
            //  the SQL database. Then, we pass them to their respective objects
            //  and let them deal with it.
            ArrayList<Integer> playerInfo = new ArrayList<>();
            ArrayList<Integer> inventoryInfo = new ArrayList<>();
            final int PLAYER_AMT = 5;
            final int INVENTORY_AMT = 15;

            //  Load in the player data from the Result Set
            for (int i = 1 ; i <= PLAYER_AMT ; i++) {
                playerInfo.add(playerStatsSet.getInt(i));
            }

            //  Load in the inventory data from the Result set
            for (int j = PLAYER_AMT + 1 ; j <= INVENTORY_AMT + PLAYER_AMT ; j++) {
                inventoryInfo.add(playerStatsSet.getInt(j));
            }
            //  Load in the player statistics.
            this.game.getPlayer().readObject(playerInfo);
            //  Load the weapon statistics.
            this.game.getPlayer().getInventory().readObject(inventoryInfo);
            //  Load in level information from the result set.
            this.game.getLevelController().readObject(playerStatsSet.getInt(21), playerStatsSet.getInt(22));
            //  Load in wave information from the result set..
            this.game.getDifficultyController().readObject(playerStatsSet.getInt(23), playerStatsSet.getInt(24));
        }
        catch (SQLException | NullPointerException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    /**
     * Connect to the SQL database... when applicable.
     *
     * @param _dbName
     * @return true if a connection was successful, false otherwise.
     */
    @Override
    public boolean connect (String _dbName) {
        //  Database name (db name in remote sql).
        String instanceID = _dbName;

        this.generateClassName();

        this.loadDatabaseCreds();

        String url = String.format("jdbc:mysql://%s:3306/%s", IP_ADDRESS, instanceID);
        try {
            this.remoteDBConnection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
    @Override
    public AccountStatus userAuthenticated (String _email, String _password) {
        try {
            //  Verify the email
            if (!this.userEmailInDatabase(_email)) {
                return AccountStatus.DOES_NOT_EXIST;
            } //  Verify the email AND the password.
            else if (!this.userInDatabase(_email, _password)) {
                return AccountStatus.INCORRECT_PASS;
            }

            this.connectedUserID = -1;
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.connectedUserID = this.getUserID(_email);
        return AccountStatus.CORRECT;
    }

    /**
     * Returns the UserID associated with an email address form the SQL
     * database. This is later used for updating the game information in the
     * server.
     *
     * @param _email
     * @return
     */
    private int getUserID (String _email) {
        try {
            PreparedStatement idStatement = null;
            ResultSet idSet = null;

            try {
                idStatement = this.remoteDBConnection.prepareStatement(String.format("SELECT UserID FROM user_accounts WHERE Email = ?;"));
                idStatement.setString(1, _email);
                idSet = idStatement.executeQuery();
            }
            catch (SQLException ex) {
                Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (idSet.next()) {
                return idSet.getInt(1);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    /**
     * Adds a user to the SQL database.
     *
     * @param _email
     * @param _password
     * @return
     */
    @Override
    public AccountStatus addUser (String _email, String _password) {
        PreparedStatement insertStatement = null;
        try {
            if (this.userInDatabase(_email, _password)) {
                return AccountStatus.EXISTS;
            }
            else {
                String hashed = BCrypt.hashpw(_password, BCrypt.gensalt());

                //  Very, VERY long statement to update the user.
                insertStatement = this.remoteDBConnection.prepareStatement(String.format("INSERT INTO user_accounts " + "VALUES(DEFAULT, ?, ?, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT);"));
                insertStatement.setString(1, _email);
                insertStatement.setString(2, hashed);
                insertStatement.executeUpdate();
                return AccountStatus.ACCOUNT_CREATED;
            }

        }
        catch (SQLException ex) {
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
     *
     * @throws SQLException
     */
    private boolean userInDatabase (String _email, String _password) throws SQLException {
        return this.isValidPassword(_email, _password);
    }

    /**
     * Queries the DB to determine if the email is in the db or not.
     *
     * @param _email
     * @return
     * @throws SQLException
     */
    private boolean userEmailInDatabase (String _email) throws SQLException {
        PreparedStatement insertStatement = this.remoteDBConnection.prepareStatement(String.format("SELECT * FROM user_accounts WHERE Email = ?;"));
        insertStatement.setString(1, _email);
        ResultSet resultQuery = insertStatement.executeQuery();

        return resultQuery.next();
    }

    /**
     * Queries the SQL database for the salted and hashed password. The hashed
     * and salted password is selected from the table, and compared using
     * JBCrypt to verify if it's correct or not.
     *
     * @param _email
     * @param _password
     * @return
     * @throws SQLException
     */
    private boolean isValidPassword (String _email, String _password) throws SQLException {
        //  Returns the salted and hashed pswd with the associated email.
        PreparedStatement insertStatement = this.remoteDBConnection.prepareStatement(String.format("SELECT Password FROM user_accounts WHERE Email = ?;"));
        insertStatement.setString(1, _email);
        ResultSet resultQuery = insertStatement.executeQuery();

        //  If we have a result query value, then we check the password against the hashed pswd.
        if (resultQuery.next()) {
            return BCrypt.checkpw(_password, resultQuery.getString("Password"));
        }
        return false;
    }

    /**
     * Generates the necessary classname to get the MySQL java driver working.
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
     * Opens the database info file to retrieve the ip address, username, and
     * password.
     */
    private void loadDatabaseCreds () {
        try {
            BufferedReader databaseFile = null;

            try {
                databaseFile = new BufferedReader(new FileReader("src/.config/.database_info.txt"));
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }

            String ip = databaseFile.readLine();
            PersistentDatabase.IP_ADDRESS = ip.substring(ip.indexOf("=") + 1);

            String user = databaseFile.readLine();
            PersistentDatabase.USERNAME = user.substring(user.indexOf("=") + 1);

            String pswd = databaseFile.readLine();
            PersistentDatabase.PASSWORD = pswd.substring(pswd.indexOf("=") + 1);

        }
        catch (IOException ex) {
            Logger.getLogger(PersistentDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
