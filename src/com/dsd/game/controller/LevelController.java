package com.dsd.game.controller;

import com.dsd.game.Game;
import com.revivedstandards.model.StandardLevel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class acts as the level controller; it will change the level depending
 * on certain circumstances.
 *
 * We need to come up with some mathematical equation that will determine how
 * many monster spawn on a given wave.
 *
 * With the current implementation, there will be several levels, each with
 * their own respective background. Each level on the other hand will have its
 * own respective wave.
 *
 * @author Joshua
 */
public class LevelController {

    private int currentLevelID = 0;
    private int currentWave = 1;
    private final List<StandardLevel> levels;
    private final Game game;
    private final Timer levelTimer;

    public LevelController (Game _game) {
        this.levels = new ArrayList<>();
        this.levelTimer = new Timer(true);
        this.game = _game;
    }

    /**
     * Ticks the level pointed at in the List of levels.
     */
    public void tickLevel () {
        this.levels.get(this.currentLevelID).tick();
    }

    /**
     * Renders the level pointed at in the List of levels.
     *
     * @param _g2
     */
    public void renderLevel (Graphics2D _g2) {
        this.levels.get(this.currentLevelID).render(_g2);
    }

    /**
     * Adds a level to the List of levels.
     *
     * @param _level object.
     */
    public void addLevel (StandardLevel _level) {
        this.levels.add(_level);
    }

    /**
     * Increments the level pointer.
     */
    public void incrementLevel () {
        this.currentLevelID++;
    }

    /**
     * Increments which wave the level is currently on. This will be used much
     * more frequently than incrementLevel().
     */
    public void incrementWave () {
        this.currentWave++;
    }

    /**
     * Begins the wave timer, depending on what difficulty the game is on.
     */
    public void startWaveTimer () {
        this.levelTimer.scheduleAtFixedRate(new LevelTimer(this.game, this),
                (long) DifficultyController.levelTransitionTimer,
                (long) DifficultyController.levelTransitionTimer);
    }

//============================= GETTERS ======================================//
    public int getCurrentLevelID () {
        return this.currentLevelID;
    }

    public StandardLevel getCurrentLevel () {
        return this.levels.get(this.currentLevelID);
    }

    public int getWaveNumber () {
        return this.currentWave;
    }

    /**
     * Instead of returning the INDEX in the array of levels, we return the
     * natural level ID.
     *
     * @return
     */
    public int getLogicalCurrentLevelID () {
        return this.currentLevelID + 1;
    }

//============================= SETTERS ======================================//
    protected void changeLevelID (int _levelID) {
        this.currentLevelID = _levelID;
    }

    public void setWaveNumber (int _waveNumber) {
        this.currentLevelID = _waveNumber;
    }

    private class LevelTimer extends TimerTask {

        private final Game game;
        private final LevelController levelController;

        public LevelTimer (Game _game, LevelController _levelController) {
            this.levelController = _levelController;
            this.game = _game;
        }

        @Override
        public void run () {
            this.levelController.incrementWave();
            if (this.levelController.getWaveNumber() % 5 == 0) {
                this.updateLevelDifficulty();
            }
            this.game.setPreambleState();
        }

        private void updateLevelDifficulty () {
            DifficultyController.incrementMobHealth();
        }
    }
}
