package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import java.awt.Graphics2D;

/**
 * Text-field for the user to enter their password.
 *
 * @author Joshua
 */
public class PasswordTextFieldModel extends TextFieldModel {

    public PasswordTextFieldModel (int _x, int _y, Game _game, MenuScreen _menuScreen) {
        super(_x, _y, _game, _menuScreen);
    }

    @Override
    public void tick () {
        if (!this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getMenuScreen().isOnAccountScreen()) {
            return;
        }
        super.render(_g2);
    }

}
