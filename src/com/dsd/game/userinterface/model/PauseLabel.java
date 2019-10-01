package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class represents the text shown on the screen when the user presses the
 * ESCAPE button (thus pausing the game).
 *
 * @author Joshua
 */
public class PauseLabel extends StandardLabel {

    private final Game game;

    private final Color transparentBlack;

    public PauseLabel (Game _game) {
        super((int) Screen.gameHalfWidth,
                (int) Screen.gameHalfHeight,
                "PAUSED", "src/res/fonts/chargen.ttf", 32f);

        this.game = _game;
        this.transparentBlack = new Color(0f, 0f, 0f, 0.5f);
    }

    @Override
    public void tick () {
        this.setX((int) this.game.getCamera().getX());
        this.setY((int) this.game.getCamera().getY());
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.setColor(this.transparentBlack);
        _g2.fillRect((int) (this.game.getCamera().getX() - Screen.gameHalfWidth),
                (int) (this.game.getCamera().getY() - Screen.gameHalfHeight),
                (int) (this.game.getCamera().getX() + Screen.gameDoubleWidth),
                (int) (this.game.getCamera().getY() + Screen.gameDoubleHeight));

        super.render(_g2);
    }
}
