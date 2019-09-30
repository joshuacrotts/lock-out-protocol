package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.PlayerState;
import com.dsd.game.WeaponSelection;
import com.dsd.game.objects.Player;
import com.revivedstandards.commands.Command;
import com.sun.glass.events.KeyEvent;

/**
 * Command representing when the user decrements their weapon
 * counter position.
 *
 * @author Joshua
 */
public class DecrementWeaponCommand extends Command {

    private final Game game;
    private final Player player;

    //
    //  This may need to change with time.
    //
    public DecrementWeaponCommand (Game _game, Player _player) {
        this.game = _game;
        this.player = _player;
        this.bind(this.game.getKeyboard(), KeyEvent.VK_X);
    }

    @Override
    public void pressed (float _dt) {
        if (this.player.getPlayerState() == PlayerState.ATTACKING) {
            return;
        }
        this.player.getInventory().changeWeapon(WeaponSelection.DECREMENT);
    }
}
