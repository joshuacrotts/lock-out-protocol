package com.dsd.game;

import com.dsd.game.objects.Player;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardLevel;
import java.awt.Graphics2D;

/**
 * Demonstrates the concept of a very primitive level using the Standards API.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class ForestLevel extends StandardLevel {

    //
    //  Player of the game
    //
    private final Player player;
    private final StandardCamera sc;

    //
    //  Variables used to track where the background image is drawn.
    //  The placement depends on the position and velocity of the player.
    //
    private int trackX;
    private final double SCROLL_X_FACTOR = 0.25;

    //
    //  Define camera scroll minimum constants
    //
    private final int MIN_X = 640;
    private final int MIN_Y = 350;

    public ForestLevel(Player _player, Game _sg, StandardCamera _sc) {
        super(null, "src/res/img/bg/resized_bg/panel1.jpg", null);

        this.player = _player;
        this.sc = _sc;

        this.setCameraBounds(this.getBgImage().getWidth() - _sg.getGameWidth() / 2,
                this.getBgImage().getHeight() - _sg.getGameHeight() / 2);
    }

    @Override
    public void loadLevelData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tick() {
        this.trackX -= (int) this.player.getVelX() * this.SCROLL_X_FACTOR;
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

    /**
     * Sets the camera's field of view so as to prevent the camera from
     * scrolling too far to any of the sides
     */
    private void setCameraBounds(int _maxX, int _maxY) {
        this.sc.restrict(_maxX, _maxY, this.MIN_X, this.MIN_Y);
    }

}
