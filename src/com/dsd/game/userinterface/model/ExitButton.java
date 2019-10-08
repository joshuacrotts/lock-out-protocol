package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - exits the game when the user presses it.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class ExitButton extends StandardButton implements MouseEventInterface {

    private final Game game;
    private final int Y_OFFSET = 200;
    private final int X_OFFSET = 70;
    private final int TEXT_X_OFFSET = 25;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 100;
    private final Font font;

    public ExitButton (Game _game) {
        super();
        this.game = _game;
        this.font = StdOps.initFont("src/resources/fonts/chargen.ttf", 24f);
        this.setX(Screen.gameHalfWidth - X_OFFSET);
        this.setY(this.game.getGameHeight() - Y_OFFSET);
        this.setWidth(BUTTON_WIDTH);
        this.setHeight(BUTTON_HEIGHT);
        this.setText("EXIT");
        this.setColor(Color.RED);
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
        StandardDraw.text(this.getText(),
                (this.getX() + (this.getWidth() / 2)) - TEXT_X_OFFSET,
                (this.getY() + this.getHeight() / 2), this.font,
                this.font.getSize(), Color.WHITE);
    }

    @Override
    public void onMouseClick () {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        this.game.stopGame();
    }

    @Override
    public void onMouseEnterHover () {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        this.setColor(Color.GREEN);
    }

    @Override
    public void onMouseExitHover () {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        this.setColor(Color.RED);
    }
}
