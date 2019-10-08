package com.dsd.game.controller;

import com.revivedstandards.model.StandardLevel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

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

    public LevelController() {
        this.levels = new ArrayList<>();
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

//============================= GETTERS ======================================//
    public int getCurrentLevelID() {
        return this.currentLevelID;
    }

    public StandardLevel getCurrentLevel() {
        return this.levels.get(this.currentLevelID);
    }

    public int getWaveNumber() {
        return this.currentWave;
    }

    /**
     * Instead of returning the INDEX in the array of levels, we return the
     * natural level ID.
     *
     * @return
     */
    public int getLogicalCurrentLevelID() {
        return this.currentLevelID + 1;
    }

//============================= SETTERS ======================================//
    protected void changeLevelID(int _levelID) {
        this.currentLevelID = _levelID;
    }

    public void setWaveNumber(int _waveNumber) {
        this.currentLevelID = _waveNumber;
    }
}
