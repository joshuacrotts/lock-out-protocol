/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.controller;

import com.dsd.game.DifficultyType;
import com.dsd.game.Game;
import com.dsd.game.enemies.BasicMonster;
import com.dsd.game.enemies.GreenMonster;

/**
 * Controller to determine how strong the enemies are as a collective group, how
 * fast the rounds go, and how many spawners are placed.
 *
 * @author Joshua
 */
public class DifficultyController {

    //  Miscellaneous reference variables
    private final Game game;
    public static DifficultyType difficultyType;

    //  How many spawners are currently defined.
    private static int spawnerAmount = 0;

    //  Difficulty factor, spawn rates of enemies, and other timer vars.
    public static float difficultyFactor;
    public static float basicMonsterSpawnRate = 1.0f;
    public static float greenMonsterSpawnRate = 0.0f;
    public static float levelTransitionTimer = 50000;
    private static final int LEVEL_TRANS_LIMIT = 20000;

    public DifficultyController (Game _game) {
        this.game = _game;
    }

    /**
     * Increases the amount of spawners that are currently in the level.
     */
    public static void incrementSpawnerAmount () {
        spawnerAmount++;
    }

    /**
     * Sets the difficulty multiplication factor (the higher the difficulty, the
     * higher the difficulty factor variable). Determines how quickly the levels
     * transition, and how much health the mobs continue to gain overtime.
     */
    public static void setDifficultyFactor () {
        switch (difficultyType) {
            case EASY:
                difficultyFactor = 1f;
                break;
            case MEDIUM:
                difficultyFactor = 1.125f;
                break;
            case HARD:
                difficultyFactor = 1.25f;
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

}
