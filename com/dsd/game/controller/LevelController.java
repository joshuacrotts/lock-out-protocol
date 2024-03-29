package com.dsd.game.controller;

import com.dsd.game.core.Game;
import com.dsd.game.objects.SerializableObject;
import com.dsd.game.database.SerializableType;
import com.dsd.game.userinterface.TimerInterface;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudioType;
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
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public class LevelController implements TimerInterface, SerializableObject {

    // Miscellaneous reference variables.
    private final Game game;
    private final List<StandardLevel> levels;
    private Timer levelTimer;
    /**
     * LevelID and wave number; corresponds to the map and the current wave
     * number (as the name suggests) respectively.
     */
    private int currentLevelID = 0;
    private int currentWave = 1;
    // Every 5 waves, a boss character will spawn.
    private int bossSpawnWaveInt = 5;

    public LevelController(Game _game) {
        this.game = _game;
        this.levels = new ArrayList<>();
        this.levelTimer = new Timer(true);
        TimerController.addTimer(this);
    }

    /**
     * Ticks the level pointed at in the List of levels.
     */
    public void tickLevel() {
        this.levels.get(this.currentLevelID).tick();
    }

    /**
     * Renders the level pointed at in the List of levels.
     *
     * @param _g2
     */
    public void renderLevel(Graphics2D _g2) {
        this.levels.get(this.currentLevelID).render(_g2);
    }

    /**
     * Adds a level to the List of levels.
     *
     * @param _level object.
     */
    public void addLevel(StandardLevel _level) {
        this.levels.add(_level);
    }

    /**
     * Increments the level pointer.
     */
    public void incrementLevel() {
        this.currentLevelID++;
    }

    /**
     * Increments which wave the level is currently on. This will be used much
     * more frequently than incrementLevel().
     */
    public void incrementWave() {
        this.currentWave++;
    }

    /**
     * Begins the wave timer, depending on what difficulty the game is on.
     */
    public void startWaveTimer() {
        this.levelTimer = new Timer(true);
        this.levelTimer.scheduleAtFixedRate(new LevelTimer(this.game, this),
                (long) DifficultyController.levelTransitionTimer,
                (long) DifficultyController.levelTransitionTimer);
        TimerController.addTimer(this);
    }

    /**
     * Removes all levels from the ArrayList, and sets the wave to 1, and the
     * currentID back to 0.
     */
    public void clearLevels() {
        this.stopMusic();
        this.levels.clear();
        this.currentLevelID = 0;
        this.currentWave = 1;
    }

    /**
     * Plays the music track associated with the current level ID.
     */
    public void playMusic() {
        StandardAudioController.play("src/resources/audio/music/level" + this.currentLevelID + ".mp3", StandardAudioType.MUSIC);
    }

    /**
     * Stops the music track associated with the current level ID.
     */
    public void stopMusic() {
        StandardAudioController.stop("src/resources/audio/music/level" + this.currentLevelID + ".mp3", StandardAudioType.MUSIC);
    }

    @Override
    public void cancelTimer() {
        this.levelTimer.cancel();
    }

    @Override
    public String createObject(SerializableType _id) {
        if (_id != SerializableType.WAVE_INFO) {
            return null;
        }
        StringBuilder levelInformation = new StringBuilder();
        // Appends the level ID and current wave info.
        levelInformation.append(this.currentLevelID).append(";");
        levelInformation.append(this.currentWave).append(";");
        return levelInformation.toString();
    }

    public void readObject(int _currentLevelID, int _currentWave) {
        this.currentLevelID = _currentLevelID;
        this.currentWave = _currentWave;
    }

    @Override
    public void updateObject(SerializableType _obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroyObject(SerializableType _obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//================================== GETTERS ==================================
    public int getCurrentLevelID() {
        // Returns current ID.
        return this.currentLevelID;
    }

    public StandardLevel getCurrentLevel() {
        // Returns current level ID.
        return this.levels.get(this.currentLevelID);
    }

    public int getWaveNumber() {
        // Returns current wave number.
        return this.currentWave;
    }

    public int getLogicalCurrentLevelID() {
        // Returns natural level ID.
        return this.currentLevelID + 1;
    }

    public int getBossSpawnInterval() {
        return this.bossSpawnWaveInt;
    }

//=============================== SETTERS =====================================
    public void setLevelID(int _levelID) {
        this.currentLevelID = _levelID;
    }

    public void setWaveNumber(int _waveNumber) {
        this.currentLevelID = _waveNumber;
    }

    /**
     * This timer, overtime, will continuously increase the difficulty of the
     * game.
     */
    private class LevelTimer extends TimerTask {

        // Miscellaneous level information.
        private final Game game;
        private final LevelController levelController;

        public LevelTimer(Game _game, LevelController _levelController) {
            this.levelController = _levelController;
            this.game = _game;
        }

        @Override
        public void run() {
            if (!this.game.isRunning()) {
                return;
            }
            this.levelController.incrementWave();
            if (this.levelController.getWaveNumber() % 5 == 0) {
                this.updateLevelDifficulty();
            }
            this.game.setPreambleState();
        }

        private void updateLevelDifficulty() {
            DifficultyController.incrementMobHealth();
        }
    }
    
}