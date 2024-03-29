package com.dsd.game.userinterface.model.labels;

import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.model.buttons.MusicVolumeControl;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class denotes the label for showing which of the two volume adjusters
 * controls the music.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class MusicLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final MenuScreen menuScreen;
    private final MusicVolumeControl musicVolumeControl;

    //  Label positioning offsets.
    private static final int BUTTON_X_OFFSET = 260;
    private static final int BUTTON_Y_OFFSET = 40;

    //  View element factors.
    private static final float FONT_SIZE = 32f;

    public MusicLabel(MusicVolumeControl _mvc, MenuScreen _menuScreen) {
        super(_mvc.getLeftButtonX(), _mvc.getLeftButtonY(), "MUSIC VOLUME", "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.menuScreen = _menuScreen;
        this.musicVolumeControl = _mvc;
    }

    @Override
    public void tick() {
        if (this.menuScreen.isOnVolume()) {
            this.setX(musicVolumeControl.getLeftButtonX() - BUTTON_X_OFFSET);
            this.setY(musicVolumeControl.getLeftButtonY() + BUTTON_Y_OFFSET);
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
