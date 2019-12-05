package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - modifies the volume of the sound effects and the
 * music.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/14/19
 */
public class VolumeControlButton extends MenuButton implements MouseEventInterface {

    //  Button position and dimension offsets.
    private static final int BUTTON_X_OFFSET = 141;
    private static final int BUTTON_Y_OFFSET = 450;
    private static final int TEXT_X_OFFSET = 50;
    private static final int TEXT_Y_OFFSET = 45;

    public VolumeControlButton(Game _game, MenuScreen _menuScreen) {
        super(VolumeControlButton.BUTTON_X_OFFSET, _game.getGameHeight() - VolumeControlButton.BUTTON_Y_OFFSET,
                LanguageController.translate("CHANGE VOLUME"),
                _game, _menuScreen);
    }

    @Override
    public void tick() {
        this.setX(Screen.gameHalfWidth - VolumeControlButton.BUTTON_X_OFFSET);
        this.setY(Screen.gameHeight - VolumeControlButton.BUTTON_Y_OFFSET);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick() {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        super.onMouseClick();
        this.getMenuScreen().pushMenuStack(MenuState.OPTIONS);
        this.getMenuScreen().setMenuState(MenuState.VOLUME);
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover() {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
