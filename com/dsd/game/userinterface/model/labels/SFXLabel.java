package com.dsd.game.userinterface.model.labels;

import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.model.buttons.SoundEffectVolumeControl;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class denotes the label for showing which of the two volume adjusters
 * controls the sfx.
 *
 * @author Joshua
 */
public class SFXLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final MenuScreen menuScreen;
    private final SoundEffectVolumeControl sfxVolumeControl;

    //  Label positioning offsets.
    private static final int BUTTON_X_OFFSET = 400;
    private static final int BUTTON_Y_OFFSET = 40;
    private static final float FONT_SIZE = 32f;

    public SFXLabel(SoundEffectVolumeControl _svc, MenuScreen _menuScreen) {
        super(_svc.getLeftButtonX(), _svc.getLeftButtonY(), "SOUND EFFECT VOLUME", "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.menuScreen = _menuScreen;
        this.sfxVolumeControl = _svc;
    }

    @Override
    public void tick() {
        if (this.menuScreen.isOnVolume()) {
            this.setX(sfxVolumeControl.getLeftButtonX() - BUTTON_X_OFFSET);
            this.setY(sfxVolumeControl.getLeftButtonY() + BUTTON_Y_OFFSET);
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.menuScreen.isOnVolume()) {
            _g2.setColor(Color.WHITE);
            super.render(_g2);
        }
    }
}
