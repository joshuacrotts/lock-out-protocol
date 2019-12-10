package com.dsd.game.userinterface.model.labels;

import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.model.PasswordTextFieldModel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class denotes the label for showing which of the two volume adjusters
 * controls the music.
 *
 * @author Joshua
 */
public class PasswordLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final MenuScreen menuScreen;
    private final PasswordTextFieldModel passwordModel;

    //  Label positioning offsets.
    private static final int BUTTON_X_OFFSET = 200;
    private static final int BUTTON_Y_OFFSET = 40;

    //  View element factors.
    private static final float FONT_SIZE = 32f;

    public PasswordLabel(PasswordTextFieldModel _passwordModel, MenuScreen _menuScreen) {
        super(_passwordModel.getX(), _passwordModel.getY(),
                LanguageController.translate("PASSWORD:"),
                "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.menuScreen = _menuScreen;
        this.passwordModel = _passwordModel;
    }

    @Override
    public void tick() {
        if (this.menuScreen.isOnAccountScreen()) {
            this.setX(passwordModel.getX() - BUTTON_X_OFFSET);
            this.setY(passwordModel.getY() + BUTTON_Y_OFFSET);
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.menuScreen.isOnAccountScreen()) {
            _g2.setColor(Color.WHITE);
            super.render(_g2);
        }
    }
}
