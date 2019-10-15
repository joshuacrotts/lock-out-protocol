/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This button will allow the user to save their game and resume the game once
 * they come back.
 *
 * @author rinty
 */
public class SaveButton extends MenuButton implements MouseEventInterface {

    private final MenuScreen menuScreen;
    private final Game game;

    private static final int BUTTON_X_OFFSET = 0;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int TEXT_X_OFFSET = 60;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public SaveButton(Game _game, MenuScreen _menuScreen) {
        super(BUTTON_X_OFFSET, _game.getGameHeight() - BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, "SAVE GAME", _game, _menuScreen);
        this.game = _game;
        this.menuScreen = _menuScreen;
    }

    @Override
    public void tick() {
        this.setX(BUTTON_X_OFFSET);
        this.setY(this.getGame().getGameHeight() - BUTTON_Y_OFFSET);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.getGame().isMenu() || !this.getGame().isRunning()) {
            return;
        }

        super.render(_g2);
        StandardDraw.text(this.getText(), this.getX() + TEXT_X_OFFSET,
                this.getY() + TEXT_Y_OFFSET, this.font, 24f, Color.WHITE);
    }

    @Override
    public void onMouseClick() {
        if (!this.getGame().isMenu() || !this.getGame().isRunning()) {
            return;
        }
        super.onMouseClick();
        //  Once the user presses the save button, it will stop the game.
        this.getGame().stopGame();
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.getGame().isMenu() || !this.getGame().isRunning()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover() {
        if (!this.getGame().isMenu() || !this.getGame().isRunning()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
