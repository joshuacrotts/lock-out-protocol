package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.main.StandardDraw;
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

    private static final int BUTTON_X_OFFSET = 20;
    private static final int BUTTON_Y_OFFSET = -50;
    private static final int TEXT_X_OFFSET = 80;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public LoadButton (Game _game, MenuScreen _menuScreen) {
        super(Screen.gameHalfWidth + BUTTON_X_OFFSET,
                Screen.gameHalfHeight - BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, "LOAD GAME", _game, _menuScreen);

        this.game = _game;
        this.menuScreen = _menuScreen;
    }

    @Override
    public void tick () {
        if (!this.getGame().isMenu()
                || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        this.setX(Screen.gameHalfWidth + BUTTON_X_OFFSET);
        this.setY(Screen.gameHalfHeight - BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getGame().isMenu()
                || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }

        super.render(_g2);
        StandardDraw.text(this.getText(), (int) this.getX() + TEXT_X_OFFSET,
                (int) this.getY() + TEXT_Y_OFFSET,
                this.font, 24f, Color.WHITE);

    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isMenu()
                || !this.getMenuScreen().isOnMainMenu()) {
            return;
        }

        //  Once the user presses the load button, it will contact the database,
        //  and update the game information.
        this.getGame().loadFromDatabase();
        this.getGame().setGameState(GameState.RUNNING);
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
