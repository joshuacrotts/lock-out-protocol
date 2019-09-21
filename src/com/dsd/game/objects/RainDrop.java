package com.dsd.game.objects;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Typical Rain Drop for the Rain effect.
 */
public class RainDrop extends StandardGameObject {

    private static final int ALPHA = 255 << 24;
    private static final int RED   = 255 << 16;
    private static final int GREEN = 255 << 8;
    private static final int BLUE  = 255;

    private final double gravity   = 0.25d;
    private final int vanish;

    public RainDrop (double x, double y, double direction, double speed, int vanish) {
        super(x, y, StandardID.Particle);

        //Solve for horizontal leg of right triangle formed by velocity vector
        this.setVelX(speed * Math.sin(direction));
        this.vanish = vanish;
    }

    @Override
    public void tick () {
        if (this.getY() > vanish) {
            this.setAlive(false);
        }

        this.setVelY(this.getVelY() + gravity);

        this.updatePosition();

    }

    @Override
    public void render (Graphics2D _g2) {
        int rg = 100;
        _g2.setColor(new Color(rg, rg, StdOps.rand(rg, 255)));
        _g2.drawLine((int) this.getX(), (int) this.getY(), (int) (this.getX() - this.getVelX() * 2), (int) (this.getY() - this.getVelY() * 2));
    }
}
