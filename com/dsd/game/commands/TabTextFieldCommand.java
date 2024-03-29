package com.dsd.game.commands;

import com.dsd.game.core.Game;
import com.dsd.game.controller.TextFieldController;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command that defines what happens when the user presses the "ESCAPE" key (it
 * changes the game state to paused).
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class TabTextFieldCommand extends Command {

    private final Game game;

    public TabTextFieldCommand(Game _game) {
        
        this.game = _game;
        /**
         * We need to tell the Canvas within the Game class to stop listening
         * for tab events because the focus subsystem consumes focus traversal
         * keys (for Swing mechanisms).
         */
        _game.setFocusTraversalKeysEnabled(false);
        this.bind(game.getKeyboard(), KeyEvent.VK_TAB);
    }

    @Override
    public void pressed(float _dt) {
        // If we're on the preamble screen OR the menu screen, we need to leave.
        if (!this.game.isMenu()) {
            return;
        }
        TextFieldController.incrementSelectedTextField();
    }

}
