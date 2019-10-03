/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.controller.DebugController;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command that defines what happens when the user presses the "K"; it will
 * draw the debug information text.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class DebugCommand extends Command {

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
