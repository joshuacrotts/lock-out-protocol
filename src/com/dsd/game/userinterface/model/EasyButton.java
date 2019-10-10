package com.dsd.game.userinterface.model;

import com.dsd.game.DifficultyType;
import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.controller.DebugController;
import com.dsd.game.controller.DifficultyController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from Menu to Running
 * when the user clicks it.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class EasyButton extends MenuButton implements MouseEventInterface {

    private static final int BUTTON_X_OFFSET = 125;
    private static final int BUTTON_Y_OFFSET = -180;
    private static final int TEXT_X_OFFSET = 50;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public EasyButton (Game _game, MenuScreen _menuScreen) {
        super(Screen.gameHalfWidth - BUTTON_X_OFFSET,
                Screen.gameHalfHeight + BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, DifficultyType.EASY.getDifficultyLabel(), _game, _menuScreen);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getMenuScreen().isOnDifficulty()) {
            return;
        }

        super.render(_g2);
        StandardDraw.text(this.getText(), this.getX() + TEXT_X_OFFSET,
                this.getY() + TEXT_Y_OFFSET, this.font, 24f, Color.WHITE);
    }

    @Override
    public void onMouseClick () {
        if (this.getGame().getGameState() != GameState.MENU || !this.getMenuScreen().isOnDifficulty()) {
            return;
        }
        if (!DebugController.DEBUG_MODE) {
            this.getGame().setGameState(GameState.PREAMBLE);
            this.getGame().playWaveChangeSFX();
        }
        else {
            this.getGame().setGameState(GameState.RUNNING);
        }
        DifficultyController.difficultyType = DifficultyType.EASY;
        this.getGame().uponPlay();
    }

    @Override
    public void onMouseEnterHover () {
        if (this.getGame().getGameState() != GameState.MENU || !this.getMenuScreen().isOnDifficulty()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;

    }

    @Override
    public void onMouseExitHover () {
        if (this.getGame().getGameState() != GameState.MENU) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
