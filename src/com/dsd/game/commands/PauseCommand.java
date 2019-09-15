package com.dsd.game.commands;

import com.dsd.game.FollowTheMouseGameTest;
import com.dsd.game.GameState;
import com.revivedstandards.commands.Command;

/**
 *
 * @author Joshua
 */
public class PauseCommand extends Command {

    private final FollowTheMouseGameTest sg;
    private boolean paused = false;

    public PauseCommand(FollowTheMouseGameTest sg) {
        this.sg = sg;
    }

    @Override
    public void pressed(float dt) {
        this.paused = !this.paused;
        this.sg.setGameState(this.paused ? GameState.PAUSED : GameState.RUNNING);
    }
}
