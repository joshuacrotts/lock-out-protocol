package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.enums.WeaponSelection;
import com.revivedstandards.commands.Command;
import com.sun.glass.events.KeyEvent;

/**
 * Command representing when the user decrements their weapon counter position.
 *
 * @author Joshua, Ronald, Rinty
 */
public class DecrementWeaponCommand extends Command {

    //  Miscellaneous reference variables.
    private final Game game;
    private final Player player;

    //  This may need to change with time.
    public DecrementWeaponCommand (Game _game, Player _player) {

        this.game = _game;
        this.player = _player;
        this.bind(this.game.getKeyboard(), KeyEvent.VK_X);
    }

    @Override
    public void pressed (float _dt) {

        if (this.player.isAttacking() || this.game.isShop()) {

            return;
        }

        this.player.getInventory().changeWeapon(WeaponSelection.DECREMENT);
    }

}
