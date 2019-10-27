package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudioType;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class is the music volume control for the... as you guessed, music audio
 * tracks.
 *
 * @author Joshua
 */
public class MusicVolumeControl extends Interactor {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;
    private Rectangle[] volumeBars;

    //  Buttons to increase/decrease the sfx volume.
    private final IncreaseVolumeButton incVolumeButton;
    private final DecreaseVolumeButton decVolumeButton;

    //  Music Button position offsets.
    private static final int BUTTON_X_OFFSET = 200;
    private static final int BUTTON_Y_OFFSET = -200;

    private static float volume = 1.0f;

    public MusicVolumeControl (Game _game, MenuScreen _menuScreen) {
        super(0, 0, 0, 0);

        this.game = _game;
        this.menuScreen = _menuScreen;

        this.incVolumeButton = new IncreaseVolumeButton(_game, _menuScreen, this,
                MusicVolumeControl.BUTTON_X_OFFSET, MusicVolumeControl.BUTTON_Y_OFFSET);
        this.decVolumeButton = new DecreaseVolumeButton(_game, _menuScreen, this,
                MusicVolumeControl.BUTTON_X_OFFSET, MusicVolumeControl.BUTTON_Y_OFFSET);

        this.menuScreen.addInteractor(this.incVolumeButton);
        this.menuScreen.addInteractor(this.decVolumeButton);

        this.initializeVolumeBars();
    }

    @Override
    public void tick () {
        if (!this.game.isMenu() || !this.menuScreen.isOnVolume()) {
            return;
        }

        this.initializeVolumeBars();

        this.incVolumeButton.tick();
        this.decVolumeButton.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.game.isMenu() || !this.menuScreen.isOnVolume()) {
            return;
        }

        this.incVolumeButton.render(_g2);
        this.decVolumeButton.render(_g2);
        this.renderVolumeBars(_g2);
    }

    @Override
    public void onMouseClick () {
    }

    @Override
    public void onMouseEnterHover () {
    }

    @Override
    public void onMouseExitHover () {
    }

    public void incrementVolume () {
        if (MusicVolumeControl.volume == IncreaseVolumeButton.MAX_VOLUME) {
            return;
        }
        MusicVolumeControl.volume += 0.1;
        StandardAudioController.setVolumeOfTracks(MusicVolumeControl.volume, StandardAudioType.MUSIC);
    }

    public void decrementVolume () {
        System.out.println(volume);
        if (MusicVolumeControl.volume < 0.1) {
            StandardAudioController.setVolumeOfTracks(0, StandardAudioType.MUSIC);
        }
        else {
            MusicVolumeControl.volume -= 0.1;
            StandardAudioController.setVolumeOfTracks(MusicVolumeControl.volume, StandardAudioType.MUSIC);
        }
    }

    private void initializeVolumeBars () {
        this.volumeBars = new Rectangle[10];

        for (int i = 0, xOffset = -120 ; i < this.volumeBars.length ; i++, xOffset += 30) {
            this.volumeBars[i] = new Rectangle(Screen.gameHalfWidth + xOffset, Screen.gameHalfHeight - 200, 20, 60);
        }

    }

    private void renderVolumeBars (Graphics2D _g2) {
        _g2.setColor(Color.RED);

        for (int i = 0 ; i < this.volumeBars.length ; i++) {
            if (((i + 1)) <= Math.round(volume * 10)) {
                _g2.fill(this.volumeBars[i]);
            }
            else {
                _g2.draw(this.volumeBars[i]);

            }
        }
    }

}
