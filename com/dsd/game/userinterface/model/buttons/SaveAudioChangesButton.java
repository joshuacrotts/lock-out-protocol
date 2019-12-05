package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.objects.ResolutionEnum;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * This class is used for verifying changes in the audio settings (note, this is
 * DIFFERENT from the actual VOLUME of the game).
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/14/19
 */
public class SaveAudioChangesButton extends MenuButton implements MouseEventInterface {

    //  Miscellaneous menu reference.
    private final MenuScreen menuScreen;

    //  Button size and position offsets.
    private static final int BUTTON_X_OFFSET = 0;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int TEXT_X_OFFSET = 60;
    private static final int TEXT_Y_OFFSET = 45;

    public SaveAudioChangesButton(Game _game, MenuScreen _menuScreen) {
        super(SaveAudioChangesButton.BUTTON_X_OFFSET,
                _game.getGameHeight() - SaveAudioChangesButton.BUTTON_Y_OFFSET,
                LanguageController.translate("SAVE CHANGES"), _game, _menuScreen);
        this.menuScreen = _menuScreen;
    }

    @Override
    public void tick() {
        this.setX(SaveAudioChangesButton.BUTTON_X_OFFSET);
        this.setY(Screen.gameHeight - SaveAudioChangesButton.BUTTON_Y_OFFSET);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick() {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        super.onMouseClick();
        //  Once the user presses the save changes button, it will update the game's resolution.
        Dimension changedDimension = ResolutionEnum.getDimension();
        this.getGame().changeResolution((int) changedDimension.getWidth(), (int) changedDimension.getHeight());
        this.getGame().saveToSettings();
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover() {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnVolume()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }

}
