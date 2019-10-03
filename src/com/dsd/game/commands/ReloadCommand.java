package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.Player;
import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAudioController;
import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Command representing when the user shoots their weapon.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class ReloadCommand extends Command {

    private final Game game;
    private final Player player;
    private final Timer reloadTimer;
    private final int RELOAD_DELAY = 3000;

    //
    //  This may need to change with time.
    //
    public ReloadCommand(Game _game, Player _obj) {
        this.game = _game;
        this.player = _obj;

        this.bind(_game.getKeyboard(), KeyEvent.VK_R);
        this.reloadTimer = new Timer(true);
    }

    @Override
    public void pressed(float _dt) {
        if (!this.player.getInventory().hasGun()) {
            return;
        }

        Gun gun = this.player.getInventory().getGun();

        if (!gun.hasAmmo() || gun.hasFullAmmo()) {
            return;
        }

        gun.setReloading(true);
        StandardAudioController.play("src/res/audio/sfx/reload.wav");
        this.reloadTimer.schedule(new ReloadTimer(gun), this.RELOAD_DELAY);

    }

    //
    //  Private class that stops the player from firing their weapon
    //  until they have "reloaded".
    //
    private class ReloadTimer extends TimerTask {

        private final Gun weapon;

        public ReloadTimer(Gun _weapon) {
            this.weapon = _weapon;
        }

        @Override
        public void run() {
            this.weapon.reload();
            this.weapon.setReloading(false);
        }
    }
}
