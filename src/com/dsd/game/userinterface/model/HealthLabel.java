/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.model;

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

    //
    //  StandardFade object for the color of the rectangle representing
    //  the health. Also variables about the color and layout of the bar.
    //
    private final StandardFadeController healthBarColor;
    private final Color darkGreen;
    private final Color lightGreen;
    private final int transparency = 127;
    private final float fadeInterval = 0.005f;
    //
    //  Position and sizing of health elements
    //
    private final int healthBarYOffset = 5;
    private final int healthBarHeight = 35;
    private final int healthXOffset = 150;
    private final int healthYOffset = 100;
    private final int arcWidth = 10;
    private final int arcHeight = 10;

    public HealthLabel(Game _game, Player _player) {
        super((int) (Screen.gameHalfWidth - Screen.gameHalfWidth),
                (int) (Screen.gameHalfHeight + Screen.gameHalfHeight / 2), "Health: ", "src/res/fonts/chargen.ttf", 32f);

        this.darkGreen = new Color(0.0f, 0.5f, 0.0f);
        this.lightGreen = new Color(0.0f, 1.0f, 0.0f);

        this.healthBarColor = new StandardFadeController(this.darkGreen, this.lightGreen, this.fadeInterval);
        this.game = _game;
        this.player = _player;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D _g2) {

        //  Update positioning here because the timing is crucial to the rendering;
        //  delegating it to tick() will cause flickering problems.
        this.setX((int) this.game.getCamera().getX() - Screen.gameHalfWidth);
        this.setY((int) ((this.game.getCamera().getY() + Screen.gameHalfHeight / 2) + healthYOffset));

        this.drawHealthText(_g2);
        this.drawHealthBar(_g2);
    }

    private void drawHealthText(Graphics2D _g2) {
        StandardDraw.text("HEALTH: ", this.getX(), this.getY(), this.getFont(), this.getFont().getSize(), Color.WHITE);
    }

    private void drawHealthBar(Graphics2D _g2) {
        //
        //  Draw the green portion (health of actual player).
        //
        _g2.setColor(this.makeColorTransparent(this.healthBarColor.combine()));
        _g2.fillRoundRect(this.getX() + healthXOffset, this.getY() + healthBarYOffset - healthBarHeight,
                (int) this.player.getHealth(), this.healthBarHeight,
                this.arcWidth, this.arcHeight);

        //
        //  Draw the black outline.
        //
        _g2.setColor(Color.BLACK);
        _g2.drawRoundRect(this.getX() + this.healthXOffset, this.getY() + healthBarYOffset - this.healthBarHeight,
                (int) 200, this.healthBarHeight, this.arcWidth, this.arcHeight);
    }

    private Color makeColorTransparent(Color _c) {
        return new Color(_c.getRed(), _c.getGreen(), _c.getBlue(), this.transparency);
    }
}
