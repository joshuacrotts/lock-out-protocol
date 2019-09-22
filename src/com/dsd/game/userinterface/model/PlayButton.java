package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from Menu to Running
 * when the user clicks it.
 *
 * @author Joshua
 */
public class PlayButton extends StandardButton {

    private final Game sg;

    public PlayButton (Game _sg, int _x, int _y, int _width, int _height, String _text, Color _color) {
        super(_x, _y, _width, _height, _text, _color);

        this.sg = _sg;
    }

    @Override
    public void tick () {
        super.tick();

        if (super.isPressed()) {
            this.sg.setGameState(GameState.RUNNING);
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }
}
