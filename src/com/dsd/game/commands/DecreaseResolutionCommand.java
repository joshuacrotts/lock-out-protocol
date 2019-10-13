package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.ResolutionEnum;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command representing when the user presses the left arrow to decrease the
 * game's screen resolution.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class DecreaseResolutionCommand extends Command {

    private final Game game;

    public DecreaseResolutionCommand (Game _game) {
        this.game = _game;
        this.bind(this.game.getKeyboard(), KeyEvent.VK_LEFT);
    }

    @Override
    public void pressed (float _dt) {
        ResolutionEnum.decreaseResolution();
    }
}
