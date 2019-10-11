package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from Menu to the account
 * information screen when selected.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class BackButton extends MenuButton implements MouseEventInterface {

    private static final int BUTTON_X_OFFSET = 155;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int TEXT_X_OFFSET = 115;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public BackButton (Game _game, MenuScreen _menuScreen) {
        super(_game.getGameWidth() - BUTTON_X_OFFSET - BUTTON_WIDTH / 2,
                _game.getGameHeight() - BUTTON_Y_OFFSET, BUTTON_WIDTH, BUTTON_HEIGHT, "BACK", _game, _menuScreen);
    }

    public BackButton (int _x, int _y, Game _game, MenuScreen _menuScreen) {
        super(_x, _y, BUTTON_WIDTH, BUTTON_HEIGHT, "BACK", _game, _menuScreen);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.getMenuScreen().isOnMainMenu()) {
            return;
        }

        super.render(_g2);
        StandardDraw.text(this.getText(), this.getX() + TEXT_X_OFFSET,
                this.getY() + TEXT_Y_OFFSET, this.font, 24f, Color.WHITE);
    }

    @Override
    public void onMouseClick () {
        if (this.getGame().getGameState() != GameState.MENU) {
            return;
        }

        this.getMenuScreen().setMenuState(this.getMenuScreen().popMenuStack());
    }

    @Override
    public void onMouseEnterHover () {
        if (this.getGame().getGameState() != GameState.MENU) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;

    }

    @Override
    public void onMouseExitHover () {
        if (this.getGame().getGameState() != GameState.MENU) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
