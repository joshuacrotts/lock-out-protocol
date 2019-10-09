package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Subclass of StandardButton - switches the game state from Menu to Running
 * when the user clicks it.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class HelpOrOptionsButton extends StandardButton implements MouseEventInterface {

    private final Game game;
    private final MenuScreen menuScreen;
    private final Font font;
    private final int Y_OFFSET = 175;
    private final int X_OFFSET = 0;
    private final int TEXT_X_OFFSET = 85;
    private final int BUTTON_WIDTH = 300;
    private final int BUTTON_HEIGHT = 200;

    private BufferedImage onHoverButtonImg;
    private BufferedImage buttonImg;
    private BufferedImage activeImage;

    public HelpOrOptionsButton (Game _game, MenuScreen _menuScreen) {
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.font = StdOps.initFont("src/resources/fonts/chargen.ttf", 24f);
        this.setX(X_OFFSET);
        this.setY(this.game.getGameHeight() - Y_OFFSET);
        this.setWidth(BUTTON_WIDTH);
        this.setHeight(BUTTON_HEIGHT);
        this.setText("Help/Options");
        this.initializeButtonImages();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.menuScreen.isOnMainMenu()) {
            return;
        }
        _g2.drawImage(this.activeImage, (int) (this.getX()),
                (int) (this.getY()), BUTTON_WIDTH, BUTTON_HEIGHT, game);
        StandardDraw.text(this.getText(), (this.getX() + (this.getWidth() / 2)) - TEXT_X_OFFSET,
                this.getY() + this.getHeight() / 2, this.font,
                this.font.getSize(), Color.WHITE);
    }

    @Override
    public void onMouseClick () {
    }

    @Override
    public void onMouseEnterHover () {
        if (this.game.getGameState() != GameState.MENU || !this.menuScreen.isOnMainMenu()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;

    }

    @Override
    public void onMouseExitHover () {
        if (this.game.getGameState() != GameState.MENU) {
            return;
        }
        this.activeImage = this.buttonImg;
    }

    private void initializeButtonImages () {
        this.buttonImg = StdOps.loadImage("src/resources/img/ui/buttonStock1.png");
        this.onHoverButtonImg = StdOps.loadImage("src/resources/img/ui/buttonStock1h.png");
    }
}
