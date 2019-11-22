package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.AccountStatus;
import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.database.TranslatorDatabase;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.EmailTextFieldModel;
import com.dsd.game.userinterface.model.PasswordTextFieldModel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from Menu to Running
 * when the user clicks it.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/14/19
 */
public class MakeAccountButton extends MenuButton implements MouseEventInterface {

    private final EmailTextFieldModel emailModel;
    private final PasswordTextFieldModel pswdModel;

    //  Button position and dimension offsets.
    private static final int BUTTON_X_OFFSET = 0;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int TEXT_X_OFFSET = 55;
    private static final int TEXT_Y_OFFSET = 45;

    public MakeAccountButton(Game _game, MenuScreen _menuScreen, EmailTextFieldModel _email, PasswordTextFieldModel _pswd) {
        super(Screen.gameHalfWidth + BUTTON_X_OFFSET,
                _game.getGameHeight() - BUTTON_Y_OFFSET,
                LanguageController.translate("MAKE ACCOUNT"), _game, _menuScreen);
        this.emailModel = _email;
        this.pswdModel = _pswd;
    }

    @Override
    public void tick() {
        this.setX(Screen.gameHalfWidth + BUTTON_X_OFFSET);
        this.setY(this.getGame().getGameHeight() - BUTTON_Y_OFFSET);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick() {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        super.onMouseClick();
        AccountStatus accountStatus = TranslatorDatabase.addUser(this.emailModel.getString(), this.pswdModel.getString());
        this.displayAccountStatus(accountStatus);
        this.getMenuScreen().setMenuState(MenuState.MAIN);
        this.clearTextboxes();
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover() {
        if (!this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }

    private void clearTextboxes() {
        this.emailModel.clearString();
        this.pswdModel.clearString();
    }
}
