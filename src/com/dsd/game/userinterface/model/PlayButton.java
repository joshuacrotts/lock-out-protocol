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
 * Subclass of StandardButton - switches the game state from Menu to Running
 * when the user clicks it.
 *
 * @author Joshua
 */
public class PlayButton extends StandardButton implements MouseEventInterface {

    private final Game game;

    private final Font font;

    private final int yOffset = 400;
    private final int xOffset = 70;
    private final int textXOffset = 30;
    private final int buttonWidth = 200;
    private final int buttonHeight = 100;

    public PlayButton (Game _game) {
        this.game = _game;

        this.font = StdOps.initFont("src/res/fonts/chargen.ttf", 24f);

        this.setX(Screen.gameHalfWidth - xOffset);
        this.setY(this.game.getGameHeight() - yOffset);
        this.setWidth(buttonWidth);
        this.setHeight(buttonHeight);
        this.setText("PLAY");
        this.setColor(Color.RED);
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
        StandardDraw.text(this.getText(), (this.getX() + (this.getWidth() / 2)) - textXOffset,
                this.getY() + this.getHeight() / 2, this.font,
                this.font.getSize(), Color.WHITE);
    }

    @Override
    public void onMouseClick () {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }

        this.game.setGameState(GameState.RUNNING);
    }

    @Override
    public void onMouseEnterHover () {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        this.setColor(Color.BLUE);
    }

    @Override
    public void onMouseExitHover () {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        this.setColor(Color.RED);
    }
}
