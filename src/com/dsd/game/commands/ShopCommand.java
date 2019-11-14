package com.dsd.game.commands;

import com.dsd.game.core.Game;
import com.dsd.game.core.GameState;
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
 * @updated 11/12/19
 */
public class ShopCommand extends Command {

    public Game game;

    public ShopCommand (Game _game) {
        this.game = _game;
        this.bind(game.getKeyboard(), KeyEvent.VK_G);
    }

    @Override
    public void pressed (float _dt) {
        if (this.game.isPreamble() || this.game.isMenu()) {
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
