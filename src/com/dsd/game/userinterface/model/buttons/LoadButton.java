package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.core.GameState;
import com.dsd.game.controller.DebugController;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This button will allow the user to load their game from an existing save
 * file, given that it exists.
 *
 * @author Joshua
 */
public class LoadButton extends MenuButton implements MouseEventInterface {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;

    //  Screen dimensions
    private static final int BUTTON_X_OFFSET = 130;
    private static final int BUTTON_Y_OFFSET = -10;
    private static final int TEXT_X_OFFSET = 80;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public LoadButton (Game _game, MenuScreen _menuScreen) {
        super(Screen.gameHalfWidth - BUTTON_X_OFFSET,
                Screen.gameHalfHeight - BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, LanguageController.translate("LOAD GAME"), _game, _menuScreen);
        this.game = _game;
        this.menuScreen = _menuScreen;
    }

    @Override
    public void tick () {
        if (!this.getGame().isMenu()
                || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        this.setX(Screen.gameHalfWidth - BUTTON_X_OFFSET);
        this.setY(Screen.gameHalfHeight - BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getGame().isMenu()
                || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }

        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isMenu()
                || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }

        super.onMouseClick();

        /**
         * Once the user presses the load button, it will contact the database,
         * and update the game information.
         *
         * If there is nothing to load (or there is no account present), we just
         * return.
         */
        if (!this.getGame().loadFromDatabase()) {
            return;
        }

        //  If the user has debug mode enabled to skip the wave screen, go ahead
        //  and toggle it.
        if (!DebugController.DEBUG_MODE) {
            this.getGame().setPreambleState();
        }
        else {
            this.getGame().setGameState(GameState.RUNNING);
        }

        this.getGame().uponPlay();
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
        if (!this.getGame().isMenu()
                || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
