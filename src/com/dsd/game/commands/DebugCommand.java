package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.controller.DebugController;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command that defines what happens when the user presses the "K"; it will draw
 * the debug information text.
 *
 * @author Joshua, Ronald, Rinty
 */
public class DebugCommand extends Command {

    //  Miscellaneous reference variables.
    public Game game;

    public DebugCommand (Game _game) {

        this.game = _game;
        this.bind(game.getKeyboard(), KeyEvent.VK_K);
    }

    @Override
    public void pressed (float dt) {

        DebugController.DEBUG_MODE = !DebugController.DEBUG_MODE;
    }

}
