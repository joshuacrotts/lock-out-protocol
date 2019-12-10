package com.dsd.game.commands;

import com.dsd.game.core.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.enums.PlayerState;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command representing when the user back-peddles.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public class MoveBackwardCommand extends Command {

    //  Miscellaneous reference variables.
    private final Game game;
    private final Player player;

    public MoveBackwardCommand(Game _game, Player _obj) {
        this.game = _game;
        this.player = _obj;
        this.bind(_game.getKeyboard(), KeyEvent.VK_S);
    }

    @Override
    public void pressed(float _dt) {
        if (this.game.isInGameState() && !this.player.isMovingForward()) {
            this.player.setPlayerState(PlayerState.WALKING_BACKWARD);
            this.player.updatePosition();
        }
    }

    @Override
    public void released(float _dt) {
        this.player.setPlayerState(PlayerState.STANDING);
    }

    @Override
    public void down(float _dt) {
        if (this.game.isInGameState() && !this.player.isMovingForward()) {
            this.player.setPlayerState(PlayerState.WALKING_BACKWARD);
            this.player.updatePosition();
        }
    }
    
}
