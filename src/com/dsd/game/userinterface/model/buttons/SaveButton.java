package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This button will allow the user to save their game and resume the game once
 * they come back.
 *
 * @author rinty
 */
public class SaveButton extends MenuButton implements MouseEventInterface {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;

    //  Button offsets and dimensions.
    private static final int BUTTON_X_OFFSET = 100;
    private static final int BUTTON_Y_OFFSET = 100;
    private static final int TEXT_X_OFFSET = 80;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public SaveButton (Game _game, MenuScreen _menuScreen) {
        super(BUTTON_X_OFFSET, BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, "SAVE GAME", _game, _menuScreen);
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.setX((int) this.game.getCamera().getX() - BUTTON_X_OFFSET);
        this.setY((int) this.game.getCamera().getY());

        this.setScaled(true);
    }

    @Override
    public void tick () {
        if (!this.game.isPaused()) {
            return;
        }

        this.setX((int) this.game.getCamera().getX() - BUTTON_X_OFFSET);
        this.setY((int) this.game.getCamera().getY());
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.game.isPaused()) {
            return;
        }

        super.render(_g2);
        StandardDraw.text(this.getText(), (int) this.getX() + TEXT_X_OFFSET,
                (int) this.getY() + TEXT_Y_OFFSET,
                this.font, 24f, Color.WHITE);

    }

    @Override
    public void onMouseClick () {
        if (!this.game.isPaused()) {
            return;
        }
        //  Once the user presses the save button, it will stop the game (for now).
        //this.getGame().saveToDatabase();
        this.getGame().setGameState(GameState.MENU);
        this.getGame().resetGame();
    }

    @Override
    public void onMouseEnterHover () {
        if (!this.game.isPaused()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover () {
        if (!this.game.isPaused()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
