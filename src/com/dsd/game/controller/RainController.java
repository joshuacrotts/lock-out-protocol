package com.dsd.game.controller;

import com.dsd.game.Game;
import com.dsd.game.LightningFlash;
import com.dsd.game.api.TranslatorAPI;
import com.dsd.game.handlers.LightningHandler;
import com.dsd.game.objects.RainDrop;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import org.apache.commons.math3.util.FastMath;

/**
 * RainController will spawn different rain (blue) particles if it is raining in
 * the location provided by the user.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class RainController implements Renderable, Updatable {

    //  Reference variables.
    private final Game game;
    private final StandardParticleHandler sph;
    private final LightningHandler lightningHandler;
    private final StandardCamera sc;

    //  Serves as a debugging feature.
    private static final boolean toggleDownfall = true;

    //  If it is raining, this boolean is toggled true.
    private boolean isRaining;

    //  Defines the range in which rain can spawn for the user.
    private static final int X_BORDER = Screen.gameDoubleWidth;
    private static final int Y_BORDER = Screen.gameDoubleHeight;

    //  Velocity constants and factors for the rain drop object.
    private static final double RAIN_DIRECTION = -FastMath.PI * 1.5;
    private static final int VEL_FACTOR = 5;
    private static final int Y_BOUND_FACTOR = 2;

    //  Constants for how many rain particles should spawn.
    private static final int MAX_RAIN_PARTICLES = 5000;

    //  Constant for how "often" lightning should spawn. The higher, the rarer.
    private static final int LIGHTNING_INT = 2500;

    public RainController (Game _game) {
        this.game = _game;
        this.sc = _game.getCamera();
        this.sph = new StandardParticleHandler(MAX_RAIN_PARTICLES);
        this.lightningHandler = new LightningHandler();

        // Be sure to always set the SPH camera or it'll throw a NPE
        this.sph.setCamera(this.sc);
        try {
            this.isRaining = TranslatorAPI.getWeather().contains("rain") | RainController.toggleDownfall;
        }
        catch (Exception ex) {
            System.err.println("Could not connect; continuing without rain.");
            this.isRaining = false;
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isRaining()) {
            this.sph.render(_g2);
            this.lightningHandler.render(_g2);
        }
    }

    @Override
    public void tick () {

        if (this.isRaining()) {
            // Generates the min/max points for the rain to spawn
            int xGenMin = (int) (this.sc.getX() - RainController.X_BORDER);
            int xGenMax = (int) (this.sc.getX() + RainController.X_BORDER);
            int yGenMin = (int) (this.sc.getY() - RainController.Y_BORDER);
            int yGenMax = (int) (this.sc.getY() + RainController.Y_BORDER / 4);

            this.sph.addEntity(this.generateRainDrop(xGenMin, xGenMax, yGenMin, yGenMax));
            this.sph.tick();

            this.generateLightning();
            this.lightningHandler.tick();
        }
    }

    /**
     * Generates a rain drop object given a range to spawn them in.
     *
     * @param _xGenMin
     * @param _xGenMax
     * @param _yGenMin
     * @param _yGenMax
     * @return
     */
    private RainDrop generateRainDrop (int _xGenMin, int _xGenMax, int _yGenMin, int _yGenMax) {

        int xPos = StdOps.rand(_xGenMin, _xGenMax);
        int yPos = StdOps.rand(_yGenMin, _yGenMax);

        return new RainDrop(xPos, yPos, RAIN_DIRECTION, this.getRandomSpeed(VEL_FACTOR),
                (int) (this.sc.getY() + this.sc.getVph() * Y_BOUND_FACTOR));
    }

    /**
     * Generates a random lightning flash.
     */
    private void generateLightning () {
        if ((int) (Math.random() * LIGHTNING_INT) < 2) {
            this.lightningHandler.getHandler().add(new LightningFlash(game, this.lightningHandler.getHandler()));
            this.lightningHandler.playLightningSFX();
        }
    }

//========================== GETTERS ==============================//
    /**
     * Returns a random speed between [0, speedFactor). Determines how fast each
     * rain drop will fall.
     *
     * @param speedFactor
     * @return
     */
    private double getRandomSpeed (int speedFactor) {
        return Math.random() * speedFactor;
    }

//========================== SETTERS =======================//
    public boolean isRaining () {
        return this.isRaining;
    }
}
