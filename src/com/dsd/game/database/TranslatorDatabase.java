package com.dsd.game.database;

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

    private Game game;
    private PersistentDatabase database;
    private String fileName;

    public TranslatorDatabase (Game _game, String _fileName) {
        this.game = _game;
        this.fileName = _fileName;
        this.database = new PersistentDatabase(this.fileName);
    }

    /**
     * Save the Game state, the level state, the player's health, money, current
     * inventory.
     */
    public void save () {
        this.database.save();
    }

    /**
     * Load the game state, level state, player health, money and current
     * inventory
     */
    public void load () {
        this.database.load();
    }
}
