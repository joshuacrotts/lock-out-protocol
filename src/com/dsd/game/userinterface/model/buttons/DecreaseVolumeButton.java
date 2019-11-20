package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 * This button is used in the volume controls for the sfx and the music. It
 * decreases the volume of either by a factor of 0.1f.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/14/19
 */
public class DecreaseVolumeButton extends MenuButton implements MouseEventInterface {

    //  References to either of the two volume controls.
    private SoundEffectVolumeControl sfxVolume;
    private MusicVolumeControl musicVolume;

    //  Position offsets.
    private final int BUTTON_X_OFFSET;
    private final int BUTTON_Y_OFFSET;

    public DecreaseVolumeButton (Game _game, MenuScreen _menuScreen, SoundEffectVolumeControl _sfxVolume, int _xOffset, int _yOffset) {
        super(0, 0, "", _game, _menuScreen);
        this.BUTTON_X_OFFSET = _xOffset;
        this.BUTTON_Y_OFFSET = _yOffset;
        this.sfxVolume = _sfxVolume;
        this.buttonImg = StdOps.loadImage("src/resources/img/ui/dec.png");
        this.onHoverButtonImg = StdOps.loadImage("src/resources/img/ui/dec_h.png");
        this.activeImage = this.buttonImg;
        super.setWidth(this.activeImage.getWidth());
        super.setHeight(this.activeImage.getHeight());
    }

    public DecreaseVolumeButton (Game _game, MenuScreen _menuScreen, MusicVolumeControl _musicVolume, int _xOffset, int _yOffset) {
        super(0, 0, "", _game, _menuScreen);
        this.BUTTON_X_OFFSET = _xOffset;
        this.BUTTON_Y_OFFSET = _yOffset;
        this.musicVolume = _musicVolume;
        this.buttonImg = StdOps.loadImage("src/resources/img/ui/dec.png");
        this.onHoverButtonImg = StdOps.loadImage("src/resources/img/ui/dec_h.png");
        this.activeImage = this.buttonImg;
        this.setWidth(this.activeImage.getWidth());
        this.setHeight(this.activeImage.getHeight());
    }

    @Override
    public void tick () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        super.tick();
        this.setX(Screen.gameHalfWidth - this.BUTTON_X_OFFSET);
        this.setY(Screen.gameHalfHeight + this.BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        super.render(_g2);
    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        if (this.sfxVolume != null) {
            this.sfxVolume.decrementVolume();
        }
        else {
            this.musicVolume.decrementVolume();
        }
    }

    @Override
    public void onMouseEnterHover () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
