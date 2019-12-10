package com.dsd.game.userinterface.model.labels;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class will tell the user how long they've been playing, and what round
 * they're currently on.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/26/19
 */
public class PowerupTextLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final Game game;

    //  String of text to be displayed in the top-left.
    private final String powerupString;

    //  Time information; its position and how often it should increment.
    private final int HEALTH_X_OFFSET = 25;
    private final int HEALTH_Y_OFFSET = 35;
    private static final float FONT_SIZE = 14f;
    private int yOffset;

    public PowerupTextLabel(Game _game, String _powerupString) {
        super(0, 0, "", "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.game = _game;
        this.powerupString = _powerupString;
    }

    @Override
    public void tick() {
        if (!this.game.isInGameState()) {
            return;
        } else {
            this.setText("YOU PICKED UP " + this.powerupString);
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        this.setX((int) this.game.getCamera().getX() - Screen.gameHalfWidth + this.HEALTH_X_OFFSET);
        this.setY((int) (this.game.getCamera().getY() - Screen.gameHalfHeight + this.HEALTH_Y_OFFSET + this.yOffset));
        _g2.setColor(Color.WHITE);
        super.render(_g2);
    }

    public void setYOffset(int _yOffset) {
        this.yOffset = _yOffset;
    }
}
