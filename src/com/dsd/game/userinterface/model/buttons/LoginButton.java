package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.AccountStatus;
import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.database.TranslatorDatabase;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.EmailTextFieldModel;
import com.dsd.game.userinterface.model.PasswordTextFieldModel;
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
public class LoginButton extends MenuButton implements MouseEventInterface {

    private static final int BUTTON_X_OFFSET = 0;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int TEXT_X_OFFSET = 115;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    private final EmailTextFieldModel emailModel;
    private final PasswordTextFieldModel pswdModel;

    public LoginButton (Game _game, MenuScreen _menuScreen, EmailTextFieldModel _email, PasswordTextFieldModel _pswd) {
        super(Screen.gameHalfWidth - BUTTON_X_OFFSET - BUTTON_WIDTH,
                _game.getGameHeight() - BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, "LOGIN", _game, _menuScreen);

        this.emailModel = _email;
        this.pswdModel = _pswd;
    }

    @Override
    public void tick () {
        this.setX(Screen.gameHalfWidth - BUTTON_X_OFFSET - BUTTON_WIDTH);
        this.setY(this.getGame().getGameHeight() - BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getMenuScreen().isOnAccountScreen()) {
            return;
        }

        super.render(_g2);
        StandardDraw.text(this.getText(), this.getX() + TEXT_X_OFFSET,
                this.getY() + TEXT_Y_OFFSET, this.font, 24f, Color.WHITE);
    }

    @Override
    public void onMouseClick () {
        if (this.getGame().getGameState() != GameState.MENU || !this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        AccountStatus accountStatus = TranslatorDatabase.authenticateUser(this.emailModel.getString(), this.pswdModel.getString());
        this.displayAccountStatus(accountStatus);
        this.getMenuScreen().setMenuState(MenuState.MAIN);
        this.clearTextboxes();
    }

    @Override
    public void onMouseEnterHover () {
        if (this.getGame().getGameState() != GameState.MENU || !this.getMenuScreen().isOnAccountScreen()) {
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

    private void clearTextboxes () {
        this.emailModel.clearString();
        this.pswdModel.clearString();
    }
}
