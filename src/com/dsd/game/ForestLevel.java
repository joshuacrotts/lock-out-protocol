package com.dsd.game;

import com.dsd.game.objects.Player;
import com.revivedstandards.model.StandardLevel;
import java.awt.Graphics2D;

/**
 * Demonstrates the concept of a very primitive level using the Standards API.
 */
public class ForestLevel extends StandardLevel {

    //
    //  Player of the game
    //
    private final Player player;

    //
    //  Variables used to track where the background image is drawn.
    //  The placement depends on the position and velocity of the player.
    //
    private int trackX;
    private int trackXX;
    private final double scrollXFactor = 0.25;

    public ForestLevel(Player player) {
        super(null, "src/res/img/bg/bg_1.png", null);

        this.player = player;
    }

    @Override
    public void loadLevelData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tick() {
        this.trackX -= (int) this.player.getVelX() * this.scrollXFactor;
    }

    @Override
    public void render(Graphics2D g2) {
        if (this.getBgImage() != null) {

            if (this.trackX <= 0) {
                g2.drawImage(this.getBgImage(), this.trackX, 0, null);
            } else {
                g2.drawImage(this.getBgImage(), 0, 0, null);
            }
        }
    }

}
