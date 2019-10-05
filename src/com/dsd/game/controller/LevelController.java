package com.dsd.game.controller;

import com.revivedstandards.model.StandardLevel;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as the level controller; it will change the level depending
 * on certain circumstances.
 *
 * @author Joshua
 */
public class LevelController {

    private int currentLevelID = 0;
    private final List<StandardLevel> levels;

    public LevelController () {
        this.levels = new ArrayList<>();
    }

    public void tickLevel () {
        this.levels.get(this.currentLevelID).tick();
    }

    public void renderLevel (Graphics2D _g2) {
        this.levels.get(this.currentLevelID).render(_g2);
    }

    public void addLevel (StandardLevel _level) {
        this.levels.add(_level);
    }

//============================= GETTERS ======================================//
    public int getCurrentLevelID () {
        return this.currentLevelID;
    }

    public StandardLevel getCurrentLevel () {
        return this.levels.get(this.currentLevelID);
    }

//============================= SETTERS ======================================//
    protected void changeLevelID (int _levelID) {
        this.currentLevelID = _levelID;
    }

    public void incrementLevel () {
        this.currentLevelID++;
    }
}
