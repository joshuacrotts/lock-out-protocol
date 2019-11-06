/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.enemies.Enemy;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardFadeController;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author LJCROTTS
 */
public class BossHealthBar extends Interactor {

    //  Miscellaneous reference variables.
    private Game game;
    private Enemy parentBoss;

    //  Backing portion with just the outline.
    private Rectangle bossRect;

    //  This rect is for their actual [normalized] health.
    private Rectangle bossHealthRect;

    //  Fade color for the health of the boss.
    private StandardFadeController healthColor;
    private final Color darkRed;
    private final Color lightRed;
    private final int TRANSPARENCY = 127;
    private final float FADE_INTERVAL = 0.005f;

    private final int MAX_HEALTH_X = 950;
    private final int HEALTH_Y_OFFSET = 25;
    private final int HEALTH_X_OFFSET = 45;
    private final int ARC_WIDTH = 10;
    private final int ARC_HEIGHT = 10;

    public BossHealthBar(Game _game, Enemy _parentBoss) {
        super((int) (Screen.gameHalfWidth - Screen.gameHalfWidth),
                (int) (Screen.gameHalfHeight - Screen.gameFourthHeight), false);
        this.game = _game;
        this.parentBoss = _parentBoss;
        this.lightRed = new Color(255, 65, 65, TRANSPARENCY);
        this.darkRed = new Color(255, 0, 0, TRANSPARENCY);
        this.healthColor = new StandardFadeController(this.darkRed, this.lightRed, this.FADE_INTERVAL);
    }

    @Override
    public void tick() {
        if (!this.parentBoss.isAlive() || this.parentBoss == null) {
            return;
        }
        this.bossRect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.parentBoss.isAlive() || this.parentBoss == null) {
            return;
        }
        /**
         * Update positioning here because the timing is crucial to the
         * rendering; delegating it to tick() will cause flickering problems.
         */
        this.setX((int) (this.game.getCamera().getX() - Screen.gameHalfWidth + HEALTH_X_OFFSET));
        this.setY((int) (this.game.getCamera().getY() - Screen.gameHalfHeight + HEALTH_Y_OFFSET));

        this.drawHealthRectangle(_g2);
    }

    private void drawHealthRectangle(Graphics2D _g2) {
        int rMin = 0;
        int rMax = this.parentBoss.getInitialHealth();
        int min = 0;//Scale min
        int max = MAX_HEALTH_X;//Scale max
        _g2.setColor(this.healthColor.combine());

        float normalizedHealth = Utilities.normalize((float) this.parentBoss.getHealth(), rMin, rMax, min, max);
        _g2.fillRoundRect(this.getX(), this.getY(), (int) normalizedHealth, 20, ARC_WIDTH, ARC_HEIGHT);

        _g2.setColor(Color.BLACK);
        _g2.drawRoundRect(this.getX(), this.getY(), (int) Utilities.normalize((float) this.parentBoss.getInitialHealth(), rMin, rMax, min, max), 20, ARC_WIDTH, ARC_HEIGHT);
    }

    @Override
    public void onMouseClick() {
    }

    @Override
    public void onMouseEnterHover() {
    }

    @Override
    public void onMouseExitHover() {
    }

}
