package com.dsd.game.controller;

import com.dsd.game.Game;
import com.dsd.game.objects.RainDrop;
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

    //  Reference variables
    private final StandardParticleHandler sph;
    private final StandardCamera sc;
    private final Game game;
    //  Serves as a debugging feature
    private static final boolean toggleDownfall = false;
    //  If it is raining, this boolean is toggled true.
    private final boolean isRaining;
    //  Defines the range in which rain can spawn for the user
    private static final int X_BORDER = 600;
    private static final int Y_BORDER = 400;
    //  Velocity constants and factors for the rain drop object.
    private static final double RAIN_DIRECTION = -FastMath.PI * 1.5;
    private static final int VEL_FACTOR = 5;
    private static final int Y_BOUND_FACTOR = 2;
    //  Constants for how many rain particles should spawn
    private static final int MAX_RAIN_PARTICLES = 5000;

    public RainController (Game _game, String _weather) {
        this.game = _game;
        this.sc = _game.getCamera();
        this.sph = new StandardParticleHandler(MAX_RAIN_PARTICLES);
        // Be sure to always set the SPH camera or it'll throw a NPE
        this.sph.setCamera(this.sc);
        this.isRaining = _weather.contains("rain") | RainController.toggleDownfall;
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isRaining()) {
            this.sph.render(_g2);
        }
    }

    @Override
    public void tick () {

        if (this.isRaining()) {
            // Generates the min/max points for the rain to spawn
            int xGenMin = (int) (this.sc.getX() - RainController.X_BORDER);
            int xGenMax = (int) (this.sc.getX() + RainController.Y_BORDER);
            int yGenMin = (int) (this.sc.getY() - RainController.Y_BORDER);
            int yGenMax = (int) (this.sc.getY() + RainController.Y_BORDER / 4);

            this.sph.addEntity(this.generateRainDrop(xGenMin, xGenMax, yGenMin, yGenMax));
            this.sph.tick();
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
