package com.dsd.game.particles;

import com.revivedstandards.handlers.StandardParticleHandler;
import com.revivedstandards.model.StandardBoxParticle;
import com.revivedstandards.view.ShapeType;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class represents the same as a standard box particle, with the exception
 * that this class eventually disappears after a certain amount of time.
 *
 * @group [Data Structure Deadheads]
 *
 * @author Joshua, Rinty, Ronald
 *
 * @updated 11/30/19
 */
public class ShrinkingBoxParticle extends StandardBoxParticle {

    private final double SHRINK_INT;

    private double currentShrinkInterval;

    public ShrinkingBoxParticle(double _x, double _y, double _dimension, double _velX, double _velY, Color _color, double _life, StandardParticleHandler _sph, double _angle, ShapeType _type, double _shrinkInt) {
        super(_x, _y, _dimension, _velX, _velY, _color, _life, _sph, _angle, _type, false);
        this.SHRINK_INT = _shrinkInt;
        this.currentShrinkInterval = _dimension;
    }

    @Override
    public void tick() {
        super.tick();

        this.currentShrinkInterval -= this.SHRINK_INT;
        this.setWidth((int) this.currentShrinkInterval);
        this.setHeight((int) this.currentShrinkInterval);

        if (this.currentShrinkInterval <= 0) {
            this.setAlive(false);
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }
}
