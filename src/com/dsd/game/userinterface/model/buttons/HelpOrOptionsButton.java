package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from Menu to Running
 * when the user clicks it.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class HelpOrOptionsButton extends MenuButton implements MouseEventInterface {

    private static final int BUTTON_X_OFFSET = 185;
    private static final int BUTTON_Y_OFFSET = -10;
    private static final int TEXT_X_OFFSET = 90;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public HelpOrOptionsButton (Game _game, MenuScreen _menuScreen) {
        super(BUTTON_X_OFFSET, _game.getGameHeight() - BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, LanguageController.translate("SETTINGS"), _game, _menuScreen);
    }

    @Override
    public void tick () {
        this.setX(Screen.gameHalfWidth + BUTTON_X_OFFSET);
        this.setY(Screen.gameHalfHeight - BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getMenuScreen().isOnMainMenu()) {
            return;
        }

        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        super.onMouseClick();

        this.getMenuScreen().pushMenuStack(MenuState.MAIN);
        this.getMenuScreen().setMenuState(MenuState.OPTIONS);
    }

    @Override
    public void onMouseEnterHover () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnMainMenu()) {
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
