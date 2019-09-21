package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.revivedstandards.commands.Command;
import com.dsd.game.PlayerState;

/**
 * Command representing when the user shoots their weapon.
 *
 * @author Joshua
 */
public class MoveCommand extends Command {

    private final Game game;
    private final Player player;

    public MoveCommand (Game _sg, Player _obj) {
        this.game = _sg;
        this.player = _obj;
    }

    @Override
    public void pressed (float _dt) {
        this.player.setPlayerState(PlayerState.Walking);
        this.player.updatePosition();
    }

    @Override
    public void released (float dt) {
        this.player.setPlayerState(PlayerState.Standing);
    }

    @Override
    public void down (float _dt) {
        this.player.setPlayerState(PlayerState.Walking);
        this.player.updatePosition();
    }
}
