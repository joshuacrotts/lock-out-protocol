package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - exits the game when the user presses it.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/14/19
 */
public class ExitButton extends MenuButton implements MouseEventInterface {

    private static final int BUTTON_X_OFFSET = 130;
    private static final int BUTTON_Y_OFFSET = 180;
    private static final int TEXT_X_OFFSET = 84;
    private static final int TEXT_Y_OFFSET = 45;

    public ExitButton (Game _game, MenuScreen _menuScreen) {
        super(_game.getGameWidth() - ExitButton.BUTTON_X_OFFSET - ExitButton.BUTTON_WIDTH / 2,
                _game.getGameHeight(), LanguageController.translate("QUIT GAME"), _game, _menuScreen);
    }

    @Override
    public void tick () {
        this.setX(Screen.gameHalfWidth - ExitButton.BUTTON_X_OFFSET);
        this.setY(Screen.gameHalfHeight + ExitButton.BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + ExitButton.TEXT_X_OFFSET,
                this.getY() + ExitButton.TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        this.getGame().stopGame();
        System.exit(0);
    }

    @Override
    public void onMouseEnterHover () {
        if (!this.getGame().isMenu()
                || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover () {
        if (!this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
