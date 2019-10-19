package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command that defines what happens when the user presses the "ESCAPE" key (it
 * changes the game state to paused).
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class PauseCommand extends Command {

    //  Miscellaneous reference variables.
    public final Game game;

    public PauseCommand (Game _game) {
        this.game = _game;
        this.bind(game.getKeyboard(), KeyEvent.VK_ESCAPE);
    }

    @Override
    public void pressed (float dt) {
        //  If we're on the preamble screen OR the menu screen, we need to leave.
        if (this.game.isPreamble() || this.game.isMenu()) {
            return;
        }
        else if (!this.game.isPaused()) {
            this.game.setGameState(GameState.PAUSED);
        }
        else {
            this.game.setGameState(GameState.RUNNING);
        }
    }
}
