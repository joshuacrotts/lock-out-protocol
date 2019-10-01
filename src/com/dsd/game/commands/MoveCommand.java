package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.objects.Player;
import com.revivedstandards.commands.Command;
import com.dsd.game.PlayerState;
import java.awt.event.KeyEvent;

/**
 * Command representing when the user shoots their weapon.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class MoveCommand extends Command {

    private final Game game;
    private final Player player;

    public MoveCommand (Game _game, Player _obj) {
        this.game = _game;
        this.player = _obj;
        this.bind(_game.getKeyboard(), KeyEvent.VK_W);
    }

    @Override
    public void pressed (float _dt) {
        if (this.game.getGameState() != GameState.PAUSED) {
            this.player.setPlayerState(PlayerState.WALKING);
            this.player.updatePosition();
        }
    }

    @Override
    public void released (float dt) {
        this.player.setPlayerState(PlayerState.STANDING);
    }

    @Override
    public void down (float _dt) {
        if (this.game.getGameState() != GameState.PAUSED) {
            this.player.setPlayerState(PlayerState.WALKING);
            this.player.updatePosition();
        }
    }
}
