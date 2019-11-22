package com.dsd.game.controller;

import com.dsd.game.core.Game;
import com.dsd.game.api.TranslatorAPI;
import com.dsd.game.objects.Snowflake;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Color;
import java.awt.Graphics2D;
import org.apache.commons.math3.util.FastMath;

/*
 * This class calls the weather API adapter and if it is snowing in real life,
 * then it will snow in the game.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/12/19
 */
public class SnowController implements Renderable, Updatable {

    //  Reference variables.
    private final Game game;
    private final StandardParticleHandler sph;
    private final StandardCamera sc;

    //  Creates a hazy color over the screen.
    private final Color hazeColor = new Color(0xFF, 0xFF, 0xFF, 60);

    //  If it is snowing, this boolean is toggled true.
    private boolean isSnowing;

    //  Serves as a debugging feature (if enabled, it ignores the API call and
    //  automatically enables snow).
    private static final boolean toggleDownfall = true;

    //  Defines the range in which snow can spawn for the user.
    private static final int X_BORDER = Screen.gameDoubleWidth;
    private static final int Y_BORDER = Screen.gameHeight;

    //  Velocity constants and factors for the snow drop object.
    private static final double SNOW_DIRECTION = -FastMath.PI * 1.5;
    private static final int VEL_FACTOR = 5;
    private static final int Y_BOUND_FACTOR = 2;
    private static final int Y_BOUND_OFFSET = 50;

    //  Constants for how many snow particles should spawn.
    private static final int MAX_SNOW_PARTICLES = 5000;

    public SnowController(Game _game) {
        this.game = _game;
        this.sc = _game.getCamera();
        this.sph = new StandardParticleHandler(MAX_SNOW_PARTICLES);
        // Be sure to always set the SPH camera or it'll throw a NPE
        this.sph.setCamera(this.sc);
        try {
            this.isSnowing = TranslatorAPI.getWeather().contains("snow") | SnowController.toggleDownfall;
        } catch (Exception ex) {
            System.err.println("Could not connect; continuing without snow.");
            this.isSnowing = false;
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.isSnowing()) {
            this.sph.render(_g2);
            this.drawSnowHaze(_g2);
        }
    }

    @Override
    public void tick() {
        if (this.isSnowing()) {
            // Generates the min/max points for the snow to spawn
            int xGenMin = (int) (this.sc.getX() - SnowController.X_BORDER);
            int xGenMax = (int) (this.sc.getX() + SnowController.X_BORDER);
            int yGenMin = (int) (this.sc.getY() - SnowController.Y_BORDER);
            int yGenMax = (int) (this.sc.getY() - SnowController.Y_BORDER + Y_BOUND_OFFSET);
            this.sph.addEntity(this.generateSnowflake(xGenMin, xGenMax, yGenMin, yGenMax));
            this.sph.tick();
        }
    }

    /**
     * Generates a snowflake object given a range to spawn them in.
     *
     * @param _xGenMin
     * @param _xGenMax
     * @param _yGenMin
     * @param _yGenMax
     * @return
     */
    private Snowflake generateSnowflake(int _xGenMin, int _xGenMax, int _yGenMin, int _yGenMax) {
        int xPos = StdOps.rand(_xGenMin, _xGenMax);
        int yPos = StdOps.rand(_yGenMin, _yGenMax);
        return new Snowflake(xPos, yPos, SNOW_DIRECTION, this.getRandomSpeed(VEL_FACTOR),
                (int) (this.sc.getY() + this.sc.getVph() * Y_BOUND_FACTOR));
    }

    /**
     * Puts a constant "snowy" haze effect over the screen.
     *
     * @param _g2
     */
    private void drawSnowHaze(Graphics2D _g2) {
        Color oldColor = _g2.getColor();
        _g2.setColor(this.hazeColor);
        _g2.fillRect((int) (this.game.getCamera().getX() - Screen.gameHalfWidth),
                (int) (this.game.getCamera().getY() - Screen.gameHalfHeight),
                (int) (this.game.getCamera().getX() + Screen.gameDoubleWidth),
                (int) (this.game.getCamera().getY() + Screen.gameDoubleHeight));
        _g2.setColor(oldColor);
    }

//========================== GETTERS ==============================//
    /**
     * Returns a random speed between [0, speedFactor). Determines how fast each
     * snow drop will fall.
     *
     * @param speedFactor
     * @return
     */
    private double getRandomSpeed(int speedFactor) {
        return Math.random() * speedFactor;
    }

//========================== SETTERS =======================//
    public boolean isSnowing() {
        return this.isSnowing;
    }
}
