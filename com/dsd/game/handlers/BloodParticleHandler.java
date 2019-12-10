package com.dsd.game.handlers;

import com.dsd.game.core.Game;
import com.dsd.game.particles.BloodType;
import static com.dsd.game.particles.BloodType.STANDARD;
import com.dsd.game.particles.SlowingBoxParticle;
import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.ShapeType;
import java.awt.Color;

/**
 * This class handles all blood particles for when the player hurts/shoots the
 * enemies.
 *
 * @group [Data Structure Deadheads]
 *
 * @author Joshua, Rinty, Ronald
 *
 * @updated 12/3/19
 */
public class BloodParticleHandler extends StandardParticleHandler {

    private final Game game;
    private static final int MAX_BLOOD_PARTICLES = 10000;

    //  Other particle dimension and positioning information.
    private static final float PARTICLE_MIN_SIZE = 1.0f;
    private static final float PARTICLE_MAX_SIZE = 10.0f;
    private static final float GROUND_PARTICLE_MAX_SIZE = 5.0f;
    private static final float PARTICLE_SCATTER_OFFSET = 10.0f;
    private static final float MIN_PARTICLE_OFFSET = 10.0f;
    private static final float MAX_PARTICLE_OFFSET = 28.0f;
    private static final float PARTICLE_LIFE = 20f;

    public BloodParticleHandler(Game _game) {
        super(MAX_BLOOD_PARTICLES);

        this.game = _game;
        this.setCamera(this.game.getCamera());
    }

    /**
     * Adds a type of particle at a given position to the blood particle
     * handler.
     *
     * @param _type
     * @param _x
     * @param _y
     * @param _angle
     * @param _bloodColor
     */
    public void addBloodParticle(BloodType _type, double _x, double _y, double _angle, Color _bloodColor) {
        switch (_type) {
            //  The standard blood particle will scatter softly, but not wildly.
            case STANDARD:
                this.addEntity(new StandardBoxParticle(
                        _x + StdOps.rand(-PARTICLE_SCATTER_OFFSET, PARTICLE_SCATTER_OFFSET),
                        _y + StdOps.rand(-PARTICLE_SCATTER_OFFSET, PARTICLE_SCATTER_OFFSET),
                        StdOps.rand(PARTICLE_MIN_SIZE, PARTICLE_MAX_SIZE), 0, 0, _bloodColor, PARTICLE_LIFE, this,
                        _angle, ShapeType.CIRCLE, false));
                break;

            //  The slowing blood particle's velocity will decrease over time,
            //  eventually coming to a stop.
            case SLOWING:
                this.addEntity(new SlowingBoxParticle(_x, _y, StdOps.rand(PARTICLE_MIN_SIZE, PARTICLE_MAX_SIZE),
                        _bloodColor, PARTICLE_LIFE, this, _angle, ShapeType.CIRCLE));
                break;

            //  Scattered particles will go in random directions, but stay in
            //  that specified direction forever, without slowning down.
            case SCATTERED:
                this.addEntity(new StandardBoxParticle(
                        _x + StdOps.randBounds(-MAX_PARTICLE_OFFSET - PARTICLE_SCATTER_OFFSET,
                                -MIN_PARTICLE_OFFSET, MIN_PARTICLE_OFFSET,
                                MAX_PARTICLE_OFFSET + PARTICLE_SCATTER_OFFSET),
                        _y + StdOps.randBounds(MAX_PARTICLE_OFFSET - PARTICLE_SCATTER_OFFSET,
                                -MIN_PARTICLE_OFFSET, MIN_PARTICLE_OFFSET,
                                MAX_PARTICLE_OFFSET + PARTICLE_SCATTER_OFFSET),
                        StdOps.rand(PARTICLE_MIN_SIZE, GROUND_PARTICLE_MAX_SIZE), 0, 0, _bloodColor, PARTICLE_LIFE, this,
                        _angle, ShapeType.CIRCLE, false));
            default:
                break;
        }
    }
}
