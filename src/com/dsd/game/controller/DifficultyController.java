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

    private final Game game;
    private static int spawnerNumber = 0;

    public static float difficultyFactor;
    public static float basicMonsterSpawnRate = 1.0f;
    public static float greenMonsterSpawnRate = 0.0f;
    public static float levelTransitionTimer = 50000;
    public static DifficultyType difficultyType;
    private static final int LEVEL_TRANS_LIMIT = 20000;

    public DifficultyController (Game _game) {
        this.game = _game;
    }

    public static void incrementSpawnerAmount () {
        spawnerNumber++;
    }

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
        }
    }

    public static void setLevelTransitionTimer () {
        if (levelTransitionTimer <= LEVEL_TRANS_LIMIT) {
            return;
        }
        levelTransitionTimer *= (2 - difficultyFactor);
    }

    public static void incrementMobHealth () {
        BasicMonster.originalHealth *= difficultyFactor;
        GreenMonster.originalHealth *= difficultyFactor;
    }
}
