package com.dsd.game.userinterface.model.labels;

import com.dsd.game.Game;
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
 */
public class ShopTextLabel extends StandardLabel {

    //  Game reference.
    private final Game game;

    //  Offsets from the parent view component.
    private final int LABEL_X_OFFSET;
    private final int LABEL_Y_OFFSET;
    private static final int WEAPON_BOX_X_OFFSET = 98;
    private static final int WEAPON_BOX_Y_OFFSET = 41;

    public ShopTextLabel (Game _game, int _xOffset, int _yOffset) {
        super((int) Screen.gameHalfWidth,
                (int) Screen.gameHalfHeight,
                "WEAPON", "src/resources/fonts/chargen.ttf", 18f);
        this.game = _game;
        this.LABEL_X_OFFSET = _xOffset - WEAPON_BOX_X_OFFSET;
        this.LABEL_Y_OFFSET = _yOffset - WEAPON_BOX_Y_OFFSET;
        this.setScaled(true);
    }

    @Override
    public void tick () {
        this.setX((int) this.game.getCamera().getX() - this.LABEL_X_OFFSET);
        this.setY((int) this.game.getCamera().getY() - this.LABEL_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        Color oldColor = _g2.getColor();
        _g2.setColor(Color.WHITE);
        super.render(_g2);
        _g2.setColor(oldColor);
    }
}
