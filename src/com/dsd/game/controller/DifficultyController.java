package com.dsd.game.controller;

import com.dsd.game.DifficultyType;
import com.dsd.game.Game;
import com.dsd.game.SerializableObject;
import com.dsd.game.database.SerializableType;
import com.dsd.game.enemies.BasicMonster;
import com.dsd.game.enemies.GreenMonster;

/**
 * Controller to determine how strong the enemies are as a collective group, how
 * fast the rounds go, and how many spawners are placed.
 *
 * @author Joshua
 */
public class DifficultyController implements SerializableObject {

    //  Miscellaneous reference variables
    private final Game game;
    public static DifficultyType difficultyType;

    //  How many spawners are currently defined.
    private static int spawnerAmount = 0;

    //  Difficulty factor, spawn rates of enemies, and other timer vars.
    public static float difficultyFactor;
    public static float basicMonsterSpawnRate = 1.0f;
    public static float greenMonsterSpawnRate = 0.0f;
    public static int levelTransitionTimer = 50000;
    private static final int LEVEL_TRANS_LIMIT = 20000;

    public DifficultyController (Game _game) {
        this.game = _game;
    }

    /**
     * Increases the amount of spawners that are currently in the level.
     */
    public static void incrementSpawnerAmount () {
        DifficultyController.spawnerAmount++;
    }

    /**
     * Sets the difficulty multiplication factor (the higher the difficulty, the
     * higher the difficulty factor variable). Determines how quickly the levels
     * transition, and how much health the mobs continue to gain overtime.
     */
    public static void setDifficultyFactor () {
        if (DifficultyController.difficultyType == null) {
            return;
        }

        switch (DifficultyController.difficultyType) {
            case EASY:
                DifficultyController.difficultyFactor = 1f;
                break;
            case MEDIUM:
                DifficultyController.difficultyFactor = 1.125f;
                break;
            case HARD:
                DifficultyController.difficultyFactor = 1.25f;
                break;
        }
    }

    /**
     * Defines the level transition timer (i.e. how long each level lasts). As
     * the wave number gets higher and higher, waves start getting quicker and
     * quicker until the plateau is hit (LEVEL_TRANS_LIMIT).
     */
    public static void setLevelTransitionTimer () {
        if (levelTransitionTimer <= LEVEL_TRANS_LIMIT) {
            return;
        }
        levelTransitionTimer *= (2 - difficultyFactor);
    }

    /**
     * Resets the difficulty factor variables if the game is start from scratch
     * from within a game (if they quit and start a brand-new game).
     */
    public static void resetDifficultyFactors () {
        DifficultyController.levelTransitionTimer = 50000;
        DifficultyController.difficultyFactor = 1.0f;
        DifficultyController.basicMonsterSpawnRate = 1.0f;
        DifficultyController.greenMonsterSpawnRate = 0.0f;
        DifficultyController.spawnerAmount = 0;
    }

    /**
     * Slowly increments the mob health depending on what difficulty the game
     * is.
     */
    protected static void incrementMobHealth () {
        BasicMonster.originalHealth *= difficultyFactor;
        GreenMonster.originalHealth *= difficultyFactor;
    }

    /**
     * If we load in a save file from the database, we can use this to set them.
     *
     * @param _levelTransitionTimer
     * @param _difficultyFactor
     */
    protected static void setDifficultyFactors (int _levelTransitionTimer, float _difficultyFactor) {
        DifficultyController.levelTransitionTimer = _levelTransitionTimer;
        DifficultyController.difficultyFactor = _difficultyFactor;
    }

//============================= CRUD OPERATIONS ===============================//
    @Override
    public String createObject (SerializableType _id) {
        if (_id != SerializableType.LEVEL) {
            return null;
        }

        StringBuilder difficultyControllerInfo = new StringBuilder();
        difficultyControllerInfo.append(levelTransitionTimer).append(";");
        difficultyControllerInfo.append(difficultyFactor).append(";");
        return difficultyControllerInfo.toString();
    }

    public void readObject (int _levelTransitionTimer, double _difficultyType) {
        DifficultyController.setDifficultyFactors(_levelTransitionTimer, (float) _difficultyType);
    }

    @Override
    public void destroyObject (SerializableType _obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateObject (SerializableType _obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
