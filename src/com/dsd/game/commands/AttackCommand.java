package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.controller.TimerController;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.enums.PlayerState;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.Knife;
import com.dsd.game.objects.weapons.Weapon;
import com.revivedstandards.commands.Command;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Command representing when the user shoots their weapon.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class AttackCommand extends Command {

    //  Miscellaneous reference variables.
    private final Game game;
    private final Player player;
    private final StandardCollisionHandler globalHandler;

    //  The animation controller for the current weapon's animation.
    private StandardAnimatorController animation;

    //  The delay for each attack.
    private Timer attackDelayTimer = null;

    private static boolean hasTimer = false;

    //  This may need to change with time.
    public AttackCommand(Game _game, Player _obj, StandardCollisionHandler _gh, StandardAnimatorController animation) {
        this.game = _game;
        this.player = _obj;
        this.globalHandler = _gh;
        this.animation = animation;
        this.animation.getStandardAnimation().setReturnAnimation(this.player.getAnimationController());
        this.attackDelayTimer = new Timer(true);

        TimerController.addTimer(this.attackDelayTimer);

        this.bind(_game.getKeyboard(), KeyEvent.VK_SPACE);
    }

    @Override
    public void pressed(float _dt) {
        if (!this.game.isInGameState()) {
            return;
        }
        Weapon weapon = this.player.getInventory().getCurrentWeapon();
        //  If the weapon's delay is not active, we can attack.
        if (weapon.ready()) {
            //  We need to do different things depending on what the weapon is.
            switch (weapon.getWeaponType()) {
                case PISTOL:
                case RIFLE:
                case SHOTGUN:
                    this.gunAttack((Gun) weapon);
                    break;
                case KNIFE:
                    this.knifeAttack((Knife) weapon);
                    break;
            }

            /**
             * Once the weapon is used, we need to toggle it to false so the
             * timer can resume.
             */
            weapon.setReady(false);
            AttackCommand.hasTimer = false;
        } /**
         * If there's not already a delay present and the weapon isn't active,
         * we can create one. We need to instantiate a new timer in the event
         * that the previous one was canceled.
         */
        else if (!AttackCommand.hasTimer) {
            AttackCommand.hasTimer = true;
            this.attackDelayTimer = new Timer(true);
            this.attackDelayTimer.schedule(new AttackDelayTimer(this, weapon), weapon.getDelay());
        }
    }

    @Override
    public void down(float dt) {
        //  down(dt) is essentially just multiple pressed(dt) calls in
        //  succession.
        this.pressed(dt);
    }

    /**
     * Shoots the gun, if necessary. If the gun is reloading, then we
     * (obviously) can't fire it. If it's empty, then we play the "gun empty"
     * sfx and just quit.
     *
     * @param _gun
     */
    private void gunAttack(Gun _gun) {
        if (!_gun.isReloading()) {
            if (_gun.isWeaponEmpty()) {
                StandardAudioController.play(_gun.getEmptySFXPath());
                return;
            }

            /**
             * Play the animation and deduct ammunition from the gun that the
             * player is using.
             */
            this.toggleAttackAnimation();
            _gun.shoot();
            StandardAudioController.play(_gun.getSFXPath());
        }
    }

    /**
     * Uses the knife attack.
     *
     * @param _knife
     */
    private void knifeAttack(Weapon _knife) {
        StandardAudioController.play(_knife.getSFXPath());
        this.toggleAttackAnimation();
    }

    /**
     * Changes the state of the player to ATTACKING, and sets their animation to
     * be the attacking one relevant to the weapon they're holding.
     */
    private void toggleAttackAnimation() {
        //  Update the animation if the player has chosen a different gender.
        this.player.setAnimation(this.animation);
        this.player.setPlayerState(PlayerState.ATTACKING);
    }

//============================ GETTERS ===================================//
    public boolean hasTimer() {
        return AttackCommand.hasTimer;
    }

//============================ SETTERS ===================================//
    /**
     * If we switch to a different weapon, we need to update the animation.
     *
     * @param _sac
     */
    public void setAnimation(StandardAnimatorController _sac) {
        this.animation = _sac;
        this.animation.getStandardAnimation().setReturnAnimation(this.player.getAnimationController());
    }

    public void setTimer(boolean _timer) {
        AttackCommand.hasTimer = _timer;
    }

    /**
     * Private class similar to the reload timer, except for this class, we need
     * to determine how long to wait in between attacks (so the user can't just
     * spam the hell out of the attack key).
     */
    private class AttackDelayTimer extends TimerTask {

        private final Weapon weapon;
        private final AttackCommand command;

        public AttackDelayTimer(AttackCommand _command, Weapon _weapon) {
            this.weapon = _weapon;
            this.command = _command;
        }

        @Override
        public void run() {
            this.command.setTimer(false);
            this.weapon.setReady(true);
        }
    }
}
