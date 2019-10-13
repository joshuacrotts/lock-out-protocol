package com.dsd.game.userinterface.model.labels;

import com.dsd.game.Game;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 * Title label for the main menu. This is only used/instantiated once.
 *
 * @author Joshua
 */
public class TitleLabel extends StandardLabel {

    private final Game game;
    private final int shakeFactor = 1;

    private static final int TITLE_X_OFFSET = 140;
    private static final int TITLE_Y_OFFSET = 40;

    public TitleLabel (Game _game) {
        super(Screen.gameHalfWidth - TitleLabel.TITLE_X_OFFSET,
                TitleLabel.TITLE_Y_OFFSET, _game.getWindow().getTitle(),
                "src/resources/fonts/chargen.ttf", 32f);

        this.game = _game;
    }

    @Override
    public void tick () {
        this.shakeText();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }

    private void shakeText () {
        this.setX(StdOps.rand(Screen.gameHalfWidth - TitleLabel.TITLE_X_OFFSET - shakeFactor,
                Screen.gameHalfWidth - TitleLabel.TITLE_X_OFFSET + this.shakeFactor));

        this.setY(StdOps.rand(TitleLabel.TITLE_Y_OFFSET - shakeFactor, TitleLabel.TITLE_Y_OFFSET + this.shakeFactor));
    }
}
