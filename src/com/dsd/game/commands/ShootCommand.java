package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.revivedstandards.commands.Command;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.dsd.game.objects.BulletGameObject;
import com.dsd.game.PlayerState;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;

/**
 * Command representing when the user shoots their weapon.
 *
 * @author Joshua
 */
public class ShootCommand extends Command {

    //
    //  Needs to support multiple bullet types!!!
    //
    private final Game game;
    private final Player player;
    private final StandardCollisionHandler globalHandler;

    private StandardAnimatorController animation;

    //
    //  This may need to change with time.
    //
    private String gunSFXPath = "src/res/audio/sfx/pistol.wav";

    public ShootCommand (Game _sg, Player _obj, StandardCollisionHandler _gh, StandardAnimatorController animation) {
        this.game = _sg;
        this.player = _obj;
        this.globalHandler = _gh;
        this.animation = animation;
        this.animation.getStandardAnimation().setReturnAnimation(this.player.getAnimationController());
    }

    @Override
    public void pressed (float _dt) {
        if (this.player.getVelX() == 0 || this.player.getVelY() == 0) {
            return;
        }

        this.player.setAnimation(this.animation);
        this.player.setPlayerState(PlayerState.Shooting);

        this.globalHandler.addEntity(new BulletGameObject((int) this.player.getX() + this.player.getWidth() / 2,
                                                          (int) this.player.getY() + this.player.getHeight() / 2,
                                                          this.player.getAngle(), this.game, this.globalHandler,
                                                          this.player.getCamera(), this.player ));

        StandardAudioController.play(this.gunSFXPath);
    }

    /**
     * If we switch to a different weapon, we need to update this command.
     *
     * @param _sac
     */
    public void setAnimation (StandardAnimatorController _sac) {
        this.animation = _sac;
    }

}
