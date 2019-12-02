package com.dsd.game.particles;

import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.ShapeType;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class represents the slowing blood particle object.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 12/1/19
 */
public class SlowingBoxParticle extends StandardBoxParticle {

    //  Randomness for the scatter of the blood particles.
    //  This the value at which the blood particles can scatter.
    private final double SCATTER_RANGE = 0.95;

    //  Variables for changing the speed of the  blood particles as they disperse.
    private static final double VEL_LOWER_BOUND = 0.05;
    private static final double VEL_UPPER_BOUND = 2.5;

    public SlowingBoxParticle(double _x, double _y, double _dimension, Color _color, double _life, StandardParticleHandler _sph, double _angle, ShapeType _type) {
        super(_x, _y, _dimension, 0, 0, _color, _life, _sph, _angle, _type, false);
        this.setVelX(StdOps.randBounds(-SlowingBoxParticle.VEL_UPPER_BOUND, -SlowingBoxParticle.VEL_LOWER_BOUND,
                SlowingBoxParticle.VEL_LOWER_BOUND, SlowingBoxParticle.VEL_UPPER_BOUND));
        this.setVelY(StdOps.randBounds(-SlowingBoxParticle.VEL_UPPER_BOUND, -SlowingBoxParticle.VEL_LOWER_BOUND,
                SlowingBoxParticle.VEL_LOWER_BOUND, SlowingBoxParticle.VEL_UPPER_BOUND));
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            super.tick();
            this.slowVelocities();
            this.updatePosition();
        } else {
            this.getHandler().removeEntity(this);
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }

    /**
     * Slows the velocity of the blood particles gradually.
     */
    private void slowVelocities() {
        this.setVelX(this.getVelX() * this.SCATTER_RANGE);
        this.setVelY(this.getVelY() * this.SCATTER_RANGE);
    }

}
