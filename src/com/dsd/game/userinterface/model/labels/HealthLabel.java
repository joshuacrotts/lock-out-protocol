/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.model.labels;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.controller.StandardFadeController;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class draws two things: a health label string, and a box denoting how
 * much health the player has. The health bar decreases in size as the player
 * takes more and more damage.
 *
 * @author Joshua
 */
public class HealthLabel extends StandardLabel {

    private final Game game;
    private final Player player;

    /**
     * StandardFade object for the color of the rectangle representing the
     * health. Also variables about the color and layout of the bar.
     */
    private final StandardFadeController healthBarColor;
    private final Color darkGreen;
    private final Color lightGreen;
    private final int TRANSPARENCY = 127;
    private final float FADE_INTERVAL = 0.005f;
    //  Position and sizing of health elements
    private final int MAX_HEALTH = 200;
    private final int HEALTH_BAR_Y_OFFSET = 5;
    private final int HEALTH_BAR_HEIGHT = 35;
    private final int HEALTH_X_OFFSET = 150;
    private final int HEALTH_Y_OFFSET = 40;
    private final int ARC_WIDTH = 10;
    private final int ARC_HEIGHT = 10;

    public HealthLabel (Game _game, Player _player) {
        super((int) (Screen.gameHalfWidth - Screen.gameHalfWidth),
                (int) (Screen.gameHalfHeight + Screen.gameFourthHeight), "Health: ", "src/resources/fonts/chargen.ttf", 32f);
        this.darkGreen = new Color(0.0f, 0.5f, 0.0f);
        this.lightGreen = new Color(0.0f, 1.0f, 0.0f);
        this.healthBarColor = new StandardFadeController(this.darkGreen, this.lightGreen, this.FADE_INTERVAL);
        this.game = _game;
        this.player = _player;
    }

    @Override
    public void tick () {
        this.setX(Screen.gameHalfWidth - Screen.gameHalfWidth);
        this.setY(Screen.gameHalfHeight + Screen.gameFourthHeight);
    }

    @Override
    public void render (Graphics2D _g2) {
        /**
         * Update positioning here because the timing is crucial to the
         * rendering; delegating it to tick() will cause flickering problems.
         */
        this.setX((int) this.game.getCamera().getX() - Screen.gameHalfWidth);
        this.setY((int) ((this.game.getCamera().getY() + Screen.gameHalfHeight) - HEALTH_Y_OFFSET));
        this.drawHealthText(_g2);
        this.drawHealthBar(_g2);
    }

    private void drawHealthText (Graphics2D _g2) {
        StandardDraw.text("HEALTH: ", this.getX(), this.getY(), this.getFont(), this.getFont().getSize(), Color.WHITE);
    }

    private void drawHealthBar (Graphics2D _g2) {
        //  Draw the green portion (health of actual player).
        this.drawGreenBar(_g2);
        //  Draw the black outline.
        this.drawBlackBarOutline(_g2);
    }

    /**
     * Draws the green portion of the health bar (representing the player's
     * health).
     *
     * @param _g2
     */
    private void drawGreenBar (Graphics2D _g2) {
        _g2.setColor(this.makeColorTransparent(this.healthBarColor.combine()));
        _g2.fillRoundRect(this.getX() + HEALTH_X_OFFSET, this.getY() + HEALTH_BAR_Y_OFFSET - HEALTH_BAR_HEIGHT,
                (int) this.player.getHealth(), this.HEALTH_BAR_HEIGHT,
                this.ARC_WIDTH, this.ARC_HEIGHT);
    }

    /**
     * Draws the black outline around the green health bar.
     *
     * @param _g2
     */
    private void drawBlackBarOutline (Graphics2D _g2) {
        _g2.setColor(Color.BLACK);
        _g2.drawRoundRect(this.getX() + this.HEALTH_X_OFFSET, this.getY() + HEALTH_BAR_Y_OFFSET - this.HEALTH_BAR_HEIGHT,
                MAX_HEALTH, this.HEALTH_BAR_HEIGHT, this.ARC_WIDTH, this.ARC_HEIGHT);
    }

    private Color makeColorTransparent (Color _c) {
        return new Color(_c.getRed(), _c.getGreen(), _c.getBlue(), this.TRANSPARENCY);
    }
}
