package com.dsd.game.userinterface.model.labels;

import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class represents the text shown on the screen when the user presses the
 * ESCAPE button (thus pausing the game).
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class PauseLabel extends StandardLabel {

    //  Miscellaneous reference variable.
    private final Game game;

    //  Label x and y positioning offset.
    private final int LABEL_X_OFFSET = 20;
    private final int LABEL_Y_OFFSET = 100;

    //  View element factors.
    private static final float FONT_SIZE = 32f;

    public PauseLabel(Game _game) {
        super((int) Screen.gameHalfWidth,
                (int) Screen.gameHalfHeight,
                LanguageController.translate("PAUSED"), "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.game = _game;
    }

    @Override
    public void tick() {
        this.setX((int) this.game.getCamera().getX() - this.LABEL_X_OFFSET);
        this.setY((int) this.game.getCamera().getY() - this.LABEL_Y_OFFSET);
    }

    @Override
    public void render(Graphics2D _g2) {
        Color oldColor = _g2.getColor();
        _g2.setColor(Color.WHITE);
        super.render(_g2);
        _g2.setColor(oldColor);
    }
}
