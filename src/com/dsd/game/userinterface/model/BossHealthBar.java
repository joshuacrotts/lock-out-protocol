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
 * This class represents the health of the boss character; it appears at the top
 * of the screen when battling.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/8/2019.
 */
public class BossHealthBar extends Interactor {

    //  Miscellaneous reference variables.
    private final Game game;
    private final Enemy parentBoss;

    //  Backing portion with just the outline.
    private Rectangle bossRect;

    //  This rect is for their actual [normalized] health.
    private Rectangle bossHealthRect;

    //  Fade color for the health of the boss.
    private final StandardFadeController healthColor;
    private final int TRANSPARENCY = 127;
    private final float FADE_INTERVAL = 0.005f;
    private final Color darkRed = new Color(255, 65, 65, TRANSPARENCY);
    private final Color lightRed = new Color(255, 0, 0, TRANSPARENCY);

    //  Positioning offsets.
    private final int MAX_HEALTH_X = 1035;
    private final int HEALTH_Y_OFFSET = 25;
    private final int HEALTH_X_OFFSET = 45;
    private final int HEALTH_HEIGHT = 20;
    private final int ARC_WIDTH = 10;
    private final int ARC_HEIGHT = 10;

    public BossHealthBar (Game _game, Enemy _parentBoss) {
        super((int) (Screen.gameHalfWidth - Screen.gameHalfWidth),
                (int) (Screen.gameHalfHeight - Screen.gameFourthHeight), false);
        this.game = _game;
        this.parentBoss = _parentBoss;
        this.healthColor = new StandardFadeController(this.darkRed, this.lightRed, this.FADE_INTERVAL);
    }

    @Override
    public void tick () {
        if (!this.parentBoss.isAlive() || this.parentBoss == null) {
            return;
        }
        this.bossRect = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void render (Graphics2D _g2) {
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

    /**
     * Draws the health of the boss (the rectangle itself), along with the
     * backing black outline of said rectangle.
     *
     * @param _g2
     */
    private void drawHealthRectangle (Graphics2D _g2) {
        int rMin = 0;
        int rMax = this.parentBoss.getInitialHealth();
        //Scale min
        int min = 0;
        //Scale max
        int max = MAX_HEALTH_X;
        _g2.setColor(this.healthColor.combine());

        float normalizedHealth = Utilities.normalize((float) this.parentBoss.getHealth(), rMin, rMax, min, max);
        _g2.fillRoundRect(this.getX(), this.getY(), (int) normalizedHealth, HEALTH_HEIGHT, ARC_WIDTH, ARC_HEIGHT);

        _g2.setColor(Color.BLACK);
        _g2.drawRoundRect(this.getX(), this.getY(), (int) Utilities.normalize((float) this.parentBoss.getInitialHealth(), rMin, rMax, min, max), 20, ARC_WIDTH, ARC_HEIGHT);
    }

    @Override
    public void onMouseClick () {
    }

    @Override
    public void onMouseEnterHover () {
    }

    @Override
    public void onMouseExitHover () {
    }

}
