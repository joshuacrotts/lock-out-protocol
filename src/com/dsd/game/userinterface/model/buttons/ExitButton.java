package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - exits the game when the user presses it.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class ExitButton extends MenuButton implements MouseEventInterface {

    private static final int BUTTON_X_OFFSET = 155;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int TEXT_X_OFFSET = 120;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public ExitButton (Game _game, MenuScreen _menuScreen) {
        super(_game.getGameWidth() - BUTTON_X_OFFSET - BUTTON_WIDTH / 2,
                _game.getGameHeight() - BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, "EXIT", _game, _menuScreen);
    }

    @Override
    public void tick () {
        this.setX(this.getGame().getGameWidth() - BUTTON_X_OFFSET - BUTTON_WIDTH / 2);
        this.setY(this.getGame().getGameHeight() - BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        super.render(_g2);
        StandardDraw.text(this.getText(), this.getX() + TEXT_X_OFFSET,
                this.getY() + TEXT_Y_OFFSET, this.font, 24f, Color.WHITE);
    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        this.getGame().stopGame();
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
