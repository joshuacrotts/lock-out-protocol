package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command representing when the user shoots their weapon.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class SprintCommand extends Command {

    //  Miscellaneous reference variables
    private final Game game;
    private final Player player;

    private float VEL_FACTOR = -6.0f;

    //  This may need to change with time.
    public SprintCommand (Game _game, Player _obj) {
        this.game = _game;
        this.player = _obj;
        this.bind(_game.getKeyboard(), KeyEvent.VK_SHIFT);
    }

    @Override
    public void pressed (float _dt) {
        this.player.setApproachVelocity(VEL_FACTOR);
    }

    @Override
    public void down (float _dt) {
        this.pressed(_dt);
    }

    @Override
    public void up (float _dt) {
        this.player.setApproachVelocity(-3.0f);

    }
}
