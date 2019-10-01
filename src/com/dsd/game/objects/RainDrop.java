package com.dsd.game.objects;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;
import org.apache.commons.math3.util.FastMath;

/**
 * Typical Rain Drop for the Rain effect.
 * 
 * [Group Name: Data Structure Deadheads]
 * @author Joshua, Ronald, Rinty 
 */
public class RainDrop extends StandardGameObject {

    //
    //  Gravity pulling the rain drop to the bottom of the screen,
    //  as well as its vanish factor (how long it lasts on screen
    //  before it dies.
    //
    private final double gravity = 0.25d;
    private final int vanish;
    private final int blueColor = 100;
    private final int velFactor = 2;

    public RainDrop(double _x, double _y, double _direction, double _speed, int _vanish) {
        super(_x, _y, StandardID.Particle);

        //Solve for horizontal leg of right triangle formed by velocity vector
        this.setVelX(_speed * FastMath.sin(_direction));
        this.vanish = _vanish;
    }

    @Override
    public void tick() {
        if (this.getY() > this.vanish) {
            this.setAlive(false);
        }

        this.setVelY(this.getVelY() + this.gravity);

        this.updatePosition();

    }

    @Override
    public void render(Graphics2D _g2) {
        _g2.setColor(new Color(blueColor, blueColor, StdOps.rand(blueColor, 0xFF)));
        _g2.drawLine((int) this.getX(), (int) this.getY(), (int) (this.getX() - this.getVelX() * velFactor),
                (int) (this.getY() - this.getVelY() * velFactor));
    }
}
