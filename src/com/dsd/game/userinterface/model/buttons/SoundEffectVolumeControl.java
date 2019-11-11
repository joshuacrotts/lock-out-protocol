package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.Interactor;
import com.dsd.game.userinterface.model.labels.SFXLabel;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudioType;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class is the sfx volume control for the... as you guessed, sound
 * effects.
 *
 * @author Joshua
 */
public class SoundEffectVolumeControl extends Interactor {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;
    private Rectangle[] volumeBars;

    //  Buttons to increase/decrease the sfx volume.
    private final IncreaseVolumeButton incVolumeButton;
    private final DecreaseVolumeButton decVolumeButton;
    private final SFXLabel sfxLabel;

    //  SFX Button position offsets.
    private static final int BUTTON_X_OFFSET = 200;
    private static final int BUTTON_Y_OFFSET = 0;

    private static float volume = 1.0f;

    public SoundEffectVolumeControl (Game _game, MenuScreen _menuScreen) {
        super(0, 0, 0, 0);

        this.game = _game;
        this.menuScreen = _menuScreen;

        this.incVolumeButton = new IncreaseVolumeButton(_game, _menuScreen, this,
                SoundEffectVolumeControl.BUTTON_X_OFFSET, SoundEffectVolumeControl.BUTTON_Y_OFFSET);
        this.decVolumeButton = new DecreaseVolumeButton(_game, _menuScreen, this,
                SoundEffectVolumeControl.BUTTON_X_OFFSET, SoundEffectVolumeControl.BUTTON_Y_OFFSET);
        this.sfxLabel = new SFXLabel(this, _menuScreen);

        this.menuScreen.addInteractor(this.incVolumeButton);
        this.menuScreen.addInteractor(this.decVolumeButton);
        this.menuScreen.addInteractor(this.sfxLabel);
        this.initializeVolumeBars();
    }

    @Override
    public void tick () {
        if (!this.game.isMenu() || !this.menuScreen.isOnVolume()) {
            return;
        }

        this.initializeVolumeBars();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.game.isMenu() || !this.menuScreen.isOnVolume()) {
            return;
        }

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

    /**
     * Increments the volume by a factor of 0.1 until it hits the max. Once it
     * hits it, we simply return to avoid going over 1.0.
     */
    public void incrementVolume () {
        if (SoundEffectVolumeControl.volume >= IncreaseVolumeButton.MAX_VOLUME) {
            return;
        }
        SoundEffectVolumeControl.volume += 0.1;
        StandardAudioController.setVolumeOfTracks(SoundEffectVolumeControl.volume, StandardAudioType.SFX);
    }

    /**
     * Decreases the volume by a factor of 0.1 until it hits less than that.
     * Once it does, we zero it out to avoid a floating point problem.
     */
    public void decrementVolume () {
        if (SoundEffectVolumeControl.volume < 0.1) {
            StandardAudioController.setVolumeOfTracks(0, StandardAudioType.SFX);
        }
        else {
            SoundEffectVolumeControl.volume -= 0.1;
            StandardAudioController.setVolumeOfTracks(SoundEffectVolumeControl.volume, StandardAudioType.SFX);
        }
    }

    /**
     * Instantiates the volume bars at their appropriate positions on the
     * screen.
     */
    private void initializeVolumeBars () {
        this.volumeBars = new Rectangle[10];

        for (int i = 0, xOffset = -120 ; i < this.volumeBars.length ; i++, xOffset += 30) {
            this.volumeBars[i] = new Rectangle(Screen.gameHalfWidth + xOffset, Screen.gameHalfHeight, 20, 60);
        }

    }

    /**
     * Renders the volume bars to the screen.
     *
     * @param _g2
     */
    private void renderVolumeBars (Graphics2D _g2) {
        _g2.setColor(Color.BLUE);

        for (int i = 0 ; i < this.volumeBars.length ; i++) {
            if ((i + 1) <= Math.round(volume * 10)) {
                _g2.fill(this.volumeBars[i]);
            }
            else {
                _g2.draw(this.volumeBars[i]);

            }
        }
    }

    public int getLeftButtonX () {
        return this.decVolumeButton.getX();
    }

    public int getLeftButtonY () {
        return this.decVolumeButton.getY();
    }
}
