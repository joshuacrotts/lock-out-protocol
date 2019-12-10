package com.dsd.game.objects;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;
import org.apache.commons.math3.util.FastMath;

/**
 * Typical Snowflake for the snow effect.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/2019
 */
public class Snowflake extends StandardGameObject {

    /**
     * Gravity pulling the snow drop to the bottom of the screen, as well as its
     * vanish factor (how long it lasts on screen before it dies.
     */
    private final Color snowColor;
    private final double GRAVITY = 0.10d;
    private final double WHITE_COLOR = 0.8;
    private final int vanish;
    private final int VEL_FACTOR = 2;

    public Snowflake(double _x, double _y, double _direction, double _speed, int _vanish) {
        super(_x, _y, StandardID.Particle);
        //Solve for horizontal leg of right triangle formed by velocity vector
        this.setVelX(_speed * FastMath.sin(_direction));
        this.setWidth(StdOps.rand(3, 8));
        this.setHeight(this.getWidth());
        this.vanish = _vanish;
        this.snowColor = Color.getHSBColor(1.0f, 0.0f, (float) StdOps.rand(WHITE_COLOR, 1d));
    }

    @Override
    public void tick() {
        if (this.getY() > this.vanish) {
            this.setAlive(false);
        }
        this.setVelY(this.getVelY() + this.GRAVITY);
        this.updatePosition();
    }

    @Override
    public void render(Graphics2D _g2) {
        _g2.setColor(this.snowColor);
        _g2.fillOval((int) this.getX(), (int) this.getY(), this.getWidth(), this.getHeight());
    }
}
