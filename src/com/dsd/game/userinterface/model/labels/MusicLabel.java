package com.dsd.game.userinterface.model.labels;

import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.model.buttons.MusicVolumeControl;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Joshua
 */
public class MusicLabel extends StandardLabel {

    private MenuScreen menuScreen;
    private MusicVolumeControl musicVolumeControl;

    private static final int BUTTON_X_OFFSET = 260;
    private static final int BUTTON_Y_OFFSET = 40;

    public MusicLabel (MusicVolumeControl _mvc, MenuScreen _menuScreen) {
        super(_mvc.getLeftButtonX(), _mvc.getLeftButtonY(), "MUSIC VOLUME", "src/resources/fonts/chargen.ttf", 32f);
        this.menuScreen = _menuScreen;
        this.musicVolumeControl = _mvc;
    }

    @Override
    public void tick () {
        if (this.menuScreen.isOnVolume()) {
            this.setX(musicVolumeControl.getLeftButtonX() - BUTTON_X_OFFSET);
            this.setY(musicVolumeControl.getLeftButtonY() + BUTTON_Y_OFFSET);
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.menuScreen.isOnVolume()) {
            _g2.setColor(Color.WHITE);
            super.render(_g2);
        }
    }
}
