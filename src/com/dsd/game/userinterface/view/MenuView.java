package com.dsd.game.userinterface.view;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * This class represents the view displayed by the MenuScreen.
 *
 * @author Joshua
 */
public class MenuView implements Renderable, Updatable {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;
    private static BufferedImage background;

    //  Position, sizing and velocity of shadow.
    private final float shadowRadius = 800f;
    private float shadowXPos = 100f;
    private float shadowYPos = 100f;
    private float shadowVelX = 1f;
    private float shadowVelY = 1f;

    //  Visualization of the shadow.
    private RadialGradientPaint shadowPaint;
    private Point2D shadowCenter;
    private final Color[] colors;
    private final float[] shadowDistance;

    public MenuView (Game _game, MenuScreen _menuScreen) {
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.shadowCenter = new Point2D.Float(shadowXPos, shadowYPos);
        this.shadowDistance = new float[]{0.0f, 1.0f};
        this.colors = new Color[]{new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
        this.shadowPaint = new RadialGradientPaint(this.shadowCenter, this.shadowRadius, this.shadowDistance, this.colors);
        this.loadBackgroundImage();
    }

    @Override
    public void tick () {
        this.shadowCenter = new Point2D.Float(this.shadowXPos, this.shadowYPos);
        this.shadowPaint = new RadialGradientPaint(this.shadowCenter, this.shadowRadius, this.shadowDistance, this.colors);

        this.updateShadowPosition();
        this.checkShadowBounds();
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.drawImage(background, 0, 0, null);
        _g2.setPaint(this.shadowPaint);
        _g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
        _g2.fillRect(0, 0, this.game.getGameWidth(), this.game.getGameHeight());
    }

    public void loadBackgroundImage () {
        String path = "src/resources/img/bg/menu_" + Screen.gameWidth + "x" + Screen.gameHeight + ".png";
        background = StdOps.loadImage(path);
    }

    /**
     * Updates the x and y position of the shadow depending on the velocity.
     */
    private void updateShadowPosition () {
        this.shadowXPos += this.shadowVelX;
        this.shadowYPos += this.shadowVelY;
    }

    /**
     * Checks the bounds of the shadows; if they go off the screen, the
     * velocities are reversed.
     */
    private void checkShadowBounds () {
        if (this.shadowXPos <= 0 || this.shadowXPos > this.game.getGameWidth()) {
            this.shadowVelX = -this.shadowVelX;
        }

        if (this.shadowYPos <= 0 || this.shadowYPos > this.game.getGameHeight()) {
            this.shadowVelY = -this.shadowVelY;
        }
    }

}
