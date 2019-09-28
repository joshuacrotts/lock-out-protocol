package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.userinterface.MouseEventInterface;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from Menu to Running
 * when the user clicks it.
 *
 * @author Joshua
 */
public class PlayButton extends StandardButton implements MouseEventInterface {

    private final Game sg;

    public PlayButton (Game _sg, int _x, int _y, int _width, int _height, String _text, Color _color) {
        super(_x, _y, _width, _height, _text, _color);

        this.sg = _sg;
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }

    @Override
    public void onMouseClick () {
        this.sg.setGameState(GameState.RUNNING);
    }

    @Override
    public void onMouseEnterHover () {
        this.setColor(Color.BLUE);
    }

    @Override
    public void onMouseExitHover () {
        this.setColor(Color.RED);
    }

}
