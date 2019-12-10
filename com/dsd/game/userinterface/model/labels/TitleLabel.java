package com.dsd.game.userinterface.model.labels;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.controller.StandardFadeController;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Title label for the main menu. This is only used/instantiated once.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class TitleLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final Game game;
    private final StandardFadeController fadeController;

    //  Variability in the shaking of the text.
    private final int shakeFactor = 1;

    //  Title x and y positioning offsets.
    private static final int TITLE_X_OFFSET = 140;
    private static final int TITLE_Y_OFFSET = 40;
    private static final float FONT_SIZE = 32f;
    
    //  View transition timer.
    private final float TRANSITION_INTERVAL = 0.01f;

    public TitleLabel(Game _game) {
        super(Screen.gameHalfWidth - TitleLabel.TITLE_X_OFFSET,
                TitleLabel.TITLE_Y_OFFSET, _game.getWindow().getTitle(),
                "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.game = _game;
        this.fadeController = new StandardFadeController(Color.white, Color.gray, this.TRANSITION_INTERVAL);
    }

    @Override
    public void tick() {
        this.shakeText();
    }

    @Override
    public void render(Graphics2D _g2) {
        Color oldColor = _g2.getColor();
        _g2.setColor(fadeController.combine());
        super.render(_g2);
        _g2.setColor(oldColor);
    }

    /**
     * Causes the label to shake violently.
     */
    private void shakeText() {
        this.setX(StdOps.rand(Screen.gameHalfWidth - TitleLabel.TITLE_X_OFFSET - shakeFactor,
                Screen.gameHalfWidth - TitleLabel.TITLE_X_OFFSET + this.shakeFactor));
        this.setY(StdOps.rand(TitleLabel.TITLE_Y_OFFSET - shakeFactor, TitleLabel.TITLE_Y_OFFSET + this.shakeFactor));
    }
}
