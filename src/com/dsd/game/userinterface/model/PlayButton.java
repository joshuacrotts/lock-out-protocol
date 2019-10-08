package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.controller.DebugController;
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
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class PlayButton extends StandardButton implements MouseEventInterface {

    private final Game game;
    private final Font font;
    private final int Y_OFFSET = 500;
    private final int X_OFFSET = 70;
    private final int TEXT_X_OFFSET = 30;
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 100;

    public PlayButton(Game _game) {
        this.game = _game;
        this.font = StdOps.initFont("src/resources/fonts/chargen.ttf", 24f);
        this.setX(Screen.gameHalfWidth - X_OFFSET);
        this.setY(this.game.getGameHeight() - Y_OFFSET);
        this.setWidth(BUTTON_WIDTH);
        this.setHeight(BUTTON_HEIGHT);
        this.setText("PLAY");
        this.setColor(Color.RED);
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
        StandardDraw.text(this.getText(), (this.getX() + (this.getWidth() / 2)) - TEXT_X_OFFSET,
                this.getY() + this.getHeight() / 2, this.font,
                this.font.getSize(), Color.WHITE);
    }

    @Override
    public void onMouseClick() {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        if (!DebugController.DEBUG_MODE) {
            this.game.setGameState(GameState.PREAMBLE);
            this.game.playWaveChangeSFX();
        } else {
            this.game.setGameState(GameState.RUNNING);
        }
        this.game.uponPlay();

    }

    @Override
    public void onMouseEnterHover() {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        this.setColor(Color.BLUE);
    }

    @Override
    public void onMouseExitHover() {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        this.setColor(Color.RED);
    }
}
