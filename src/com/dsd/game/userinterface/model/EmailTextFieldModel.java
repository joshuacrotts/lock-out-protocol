package com.dsd.game.userinterface.model;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.labels.EmailLabel;
import java.awt.Graphics2D;

/**
 * This class represents the text-field for the email field.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class EmailTextFieldModel extends TextFieldModel {

    private final int BUTTON_X_OFFSET = 300;
    private final EmailLabel emailLabel;

    public EmailTextFieldModel (int _x, int _y, Game _game, MenuScreen _menuScreen) {
        super(_x, _y, _game, _menuScreen);
        this.emailLabel = new EmailLabel(this, _menuScreen);
        this.getMenuScreen().addInteractor(this.emailLabel);
    }

    @Override
    public void tick () {
        if (!this.getMenuScreen().isOnAccountScreen()) {
            return;
        }

        super.tick();

        this.setX(Screen.gameHalfWidth - BUTTON_X_OFFSET);
        this.setY(Screen.gameFourthHeight);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        super.render(_g2);
    }

    public int getEmailLabelLength () {
        return this.emailLabel.getText().length();
    }
}
