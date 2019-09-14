package com.dsd.game.objects;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Typical Rain Drop for the Rain effect.
 *
 * @author Andrew Matzureff
 * @version (5/23/2017)
 */
public class RainDrop extends StandardGameObject {

    private static final int alpha = 255 << 24;
    private static final int red = 255 << 16;
    private static final int green = 255 << 8;
    private static final int blue = 255;
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

        this.updatePosition();

        this.setVelY(this.getVelY() + 0.25d);
    }

    @Override
    public void render (Graphics2D g2) {
        int rg = 100;

        g2.setColor(new Color(rg, rg, StdOps.rand(rg, 255)));

        g2.drawLine((int) this.getX(), (int) this.getY(), (int) (this.getX() - this.getVelX() * 2), (int) (this.getY() - this.getVelY() * 2));
    }
}
