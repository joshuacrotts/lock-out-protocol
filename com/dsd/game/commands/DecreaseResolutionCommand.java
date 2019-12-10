package com.dsd.game.commands;

import com.dsd.game.core.Game;
import com.dsd.game.objects.ResolutionEnum;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command representing when the user presses the left arrow to decrease the
 * game's screen resolution.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public class DecreaseResolutionCommand extends Command {

    private final Game game;

    public DecreaseResolutionCommand(Game _game) {
        this.game = _game;
        this.bind(this.game.getKeyboard(), KeyEvent.VK_LEFT);
    }

    @Override
    public void pressed(float _dt) {
        if (!this.game.isMenu() || !this.game.getMenuScreen().isOnResolution()) {
            return;
        }
        ResolutionEnum.decreaseResolution();
    }
    
}
