/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ShopCommand extends Command {

    public Game game;

    public ShopCommand (Game _game) {
        this.game = _game;
        this.bind(game.getKeyboard(), KeyEvent.VK_G);
    }

    @Override
    public void pressed (float dt) {
        if (this.game.isPreamble()) {
            return;
        }
        else if (!this.game.isShop()) {
            this.game.setGameState(GameState.SHOP);
        }
        else {
            this.game.setGameState(GameState.RUNNING);
        }
    }
}
