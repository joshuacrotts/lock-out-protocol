package com.dsd.game.userinterface.view;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.buttons.MusicVolumeControl;
import com.dsd.game.userinterface.model.buttons.SaveAudioChangesButton;
import com.dsd.game.userinterface.model.buttons.SoundEffectVolumeControl;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudioType;
import java.awt.Graphics2D;

/**
 * This is the view that will be shown when the user wants to change their audio
 * levels.
 *
 * @author Joshua, Ronald, Rinty
 */
public class AudioView extends Screen implements MouseEventInterface {

    private final MenuScreen menuScreen;

    public AudioView (Game _game, MenuScreen _menuScreen) {
        super(_game);
        this.menuScreen = _menuScreen;
        this.menuScreen.addInteractor(new SaveAudioChangesButton(super.getGame(), this.menuScreen));
        this.menuScreen.addInteractor(new SoundEffectVolumeControl(super.getGame(), this.menuScreen));
        this.menuScreen.addInteractor(new MusicVolumeControl(super.getGame(), this.menuScreen));

        StandardAudioController.play("src/resources/audio/music/menu.wav", StandardAudioType.MUSIC);
    }

    @Override
    public void tick () {
        if (!this.menuScreen.isOnVolume() || !this.getGame().isMenu()) {
            return;
        }
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.menuScreen.isOnVolume() || !this.getGame().isMenu()) {
            return;
        }
        super.render(_g2);
    }

    @Override
    public void onMouseClick () {
        if (!this.menuScreen.isOnVolume() || !this.getGame().isMenu()) {
            return;
        }
    }

    @Override
    public void onMouseEnterHover () {
        if (!this.menuScreen.isOnResolution() || !this.getGame().isMenu()) {
            return;
        }
    }

    @Override
    public void onMouseExitHover () {
        if (!this.menuScreen.isOnResolution() || !this.getGame().isMenu()) {
            return;
        }
    }

}
