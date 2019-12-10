package com.dsd.game.userinterface.model.labels;

import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.model.EmailTextFieldModel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class denotes the label for showing which of the two volume adjusters
 * controls the music.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class EmailLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final MenuScreen menuScreen;
    private final EmailTextFieldModel emailModel;

    //  Label positioning offsets.
    private static final int BUTTON_X_OFFSET = 200;
    private static final int BUTTON_Y_OFFSET = 40;

    //  View element factors.
    private static final float FONT_SIZE = 32f;

    public EmailLabel(EmailTextFieldModel _emailModel, MenuScreen _menuScreen) {
        super(_emailModel.getX(), _emailModel.getY(),
                LanguageController.translate("EMAIL:"),
                "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.menuScreen = _menuScreen;
        this.emailModel = _emailModel;
    }

    @Override
    public void tick() {
        if (this.menuScreen.isOnAccountScreen()) {
            this.setX(emailModel.getX() - BUTTON_X_OFFSET);
            this.setY(emailModel.getY() + BUTTON_Y_OFFSET);
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
