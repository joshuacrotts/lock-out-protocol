package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.userinterface.Screen;
import java.awt.Graphics2D;

/**
 * Title label for the main menu. This is only used/instantiated once.
 *
 * @author Joshua
 */
public class TitleLabel extends StandardLabel {

    private static final int TITLE_X_OFFSET = 150;
    private static final int TITLE_Y_OFFSET = 40;

    public TitleLabel (Game _game) {
        super(Screen.gameHalfWidth - TitleLabel.TITLE_X_OFFSET, TitleLabel.TITLE_Y_OFFSET,
                _game.getWindow().getTitle(), "src/res/fonts/chargen.ttf", 32f);
    }

    @Override
    public void tick () {
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }

    static {

    }
}
