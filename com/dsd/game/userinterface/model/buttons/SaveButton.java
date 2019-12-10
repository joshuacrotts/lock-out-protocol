package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.core.GameState;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This button will allow the user to save their game and resume the game once
 * they come back.
 *
 * [Group Name: Data Structure Deadheads]
 * 
 * @author Rinty, Ronald, Joshua
 *
 * @updated 11/14/19
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

    public SaveButton(Game _game, MenuScreen _menuScreen) {
        super(SaveButton.BUTTON_X_OFFSET, SaveButton.BUTTON_Y_OFFSET,
                LanguageController.translate("SAVE GAME"), _game, _menuScreen);
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.setX((int) this.game.getCamera().getX() - SaveButton.BUTTON_X_OFFSET);
        this.setY((int) this.game.getCamera().getY());
        this.setScaled(true);
    }

    @Override
    public void tick() {
        if (!this.game.isPaused()) {
            return;
        }
        this.setX((int) this.game.getCamera().getX() - SaveButton.BUTTON_X_OFFSET);
        this.setY((int) this.game.getCamera().getY());
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.game.isPaused()) {
            return;
        }
        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + SaveButton.TEXT_X_OFFSET,
                this.getY() + SaveButton.TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick() {
        if (!this.game.isPaused()) {
            return;
        }
        super.onMouseClick();
        //  Once the user presses the save button, it will stop the game (for now).
        this.getGame().saveToDatabase();
        this.getGame().setGameState(GameState.MENU);
        this.getGame().resetGame();
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.game.isPaused()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover() {
        if (!this.game.isPaused()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
