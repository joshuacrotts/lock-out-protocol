package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.controller.TimerController;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.TimerInterface;
import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudioType;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Command representing when the user shoots their weapon.
 *
 * @author Joshua, Ronald, Rinty
 */
public class ReloadCommand extends Command implements TimerInterface {

    //  Miscellaneous reference variables
    private final Game game;
    private final Player player;
    private Timer reloadTimer;
    //  How long the timer should wait before letting the player fire after
    //  reloading their gun.
    private final int RELOAD_DELAY = 3000;

    //  This may need to change with time.
    public ReloadCommand (Game _game, Player _obj) {

        this.game = _game;
        this.player = _obj;
        this.bind(_game.getKeyboard(), KeyEvent.VK_R);
        this.reloadTimer = new Timer(true);
        TimerController.addTimer(this);
    }

    @Override
    public void cancelTimer () {

        this.reloadTimer.cancel();
    }

    @Override
    public void pressed (float _dt) {

        //  No point in trying to reload if they have a melee weapon.
        if (!this.player.getInventory().hasGun()) {

            return;
        }

        Gun gun = this.player.getInventory().getGun();

        if (!gun.hasAmmo() || gun.hasFullAmmo()) {

            return;
        }

        gun.setReloading(true);

        //  If the gun is a shotgun and we still have shotgun shells, we don't
        //  reload.
        if (gun.getWeaponType() == WeaponType.SHOTGUN && !gun.isWeaponEmpty()) {
            return;
        }

        //  Play the reload sfx.
        StandardAudioController.play(gun.getReloadSFXPath(), StandardAudioType.SFX);

        this.reloadTimer = new Timer(true);
        this.reloadTimer.schedule(new ReloadTimer(gun), gun.getReloadDelay());
    }

    /**
     * Private class that stops the player from firing their weapon until they
     * have "reloaded".
     */
    private class ReloadTimer extends TimerTask {

        private final Gun weapon;

        public ReloadTimer (Gun _weapon) {

            this.weapon = _weapon;
        }

        @Override
        public void run () {

            this.weapon.reload();
            this.weapon.setReloading(false);
        }

    }

}
