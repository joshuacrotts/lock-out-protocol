package com.dsd.game.controller;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.model.Crosshair;
import com.revivedstandards.view.Updatable;

/**
 * This is a very primitive class controlling when the cursor changes images.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public class CursorController implements Updatable {

    // Miscellaneous reference variables.
    private final Game game;
    private final Crosshair crosshair;

    public CursorController(Game _game) {
        this.game = _game;
        this.crosshair = new Crosshair(_game);
    }

    @Override
    public void tick() {
        this.crosshair.updateCursor();
    }
    
}
