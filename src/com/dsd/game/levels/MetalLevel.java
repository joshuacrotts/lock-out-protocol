package com.dsd.game.levels;

import com.dsd.game.Game;
import com.dsd.game.enemies.enums.EnemyType;
import com.dsd.game.factories.SpawnerFactory;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.util.StdConsole;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardLevel;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 * Demonstrates the concept of a very primitive level using the Standards API.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class MetalLevel extends StandardLevel {

    //  Miscellaneous reference variables
    private final Game game;
    private final Player player;
    private final StandardCamera sc;

    /**
     * Variables used to track where the background image is drawn. The
     * placement depends on the position and velocity of the player.
     */
    private int trackX;
    private final double SCROLL_X_FACTOR = 0.25;
    //  Define camera scroll minimum constants
    private final int MIN_X = (int) (Screen.gameHalfWidth * 1.5);
    private final int MIN_Y = (int) (Screen.gameHalfHeight * 1.5);

    /**
     * All levels need to share the same collision handler so the player can
     * directly interact with other entities.
     *
     * @param _player
     * @param _sg
     * @param _sch
     */
    public MetalLevel (Player _player, Game _sg, StandardCollisionHandler _sch) {
        super("src/resources/img/bg/resized_bg/panel1.jpg");
        this.game = _sg;
        this.player = _player;
        this.sc = _sg.getCamera();
        this.setHandler(_sch);
        this.setCameraBounds(this.getBgImage().getWidth() - Screen.gameHalfWidth,
                             this.getBgImage().getHeight() - Screen.gameHalfHeight);
        StdConsole.println(StdConsole.GREEN, "Instantiating the level...");
    }

    @Override
    public void loadLevelData () {
        this.addEntity(SpawnerFactory.generateSpawner(EnemyType.BASIC_MONSTER,
                StdOps.rand(600, 3400), StdOps.rand(600, 3400), 10000, 150,
                this.game, (StandardCollisionHandler) this.getHandler()));
        this.addEntity(SpawnerFactory.generateSpawner(EnemyType.GREEN_MONSTER,
                StdOps.rand(600, 3400), StdOps.rand(600, 3400), 10000, 150,
                this.game, (StandardCollisionHandler) this.getHandler()));
        this.addEntity(SpawnerFactory.generateSpawner(EnemyType.DARK_FEMALE_MONSTER,
                StdOps.rand(600, 3400), StdOps.rand(600, 3400), 10000, 150,
                this.game, (StandardCollisionHandler) this.getHandler()));
    }

    @Override
    public void tick () {
        this.trackX -= (int) this.player.getVelX() * this.SCROLL_X_FACTOR;
    }

    @Override
    public void render (Graphics2D g2) {
        if (this.getBgImage() != null) {

            if (this.trackX <= 0) {
                g2.drawImage(this.getBgImage(), this.trackX, 0, null);
            }
            else {
                g2.drawImage(this.getBgImage(), 0, 0, null);
            }
        }
    }

//============================== SETTERS ==============================//
    /**
     * Sets the camera's field of view so as to prevent the camera from
     * scrolling too far to any of the sides
     */
    private void setCameraBounds (int _maxX, int _maxY) {
        this.sc.restrict(_maxX, _maxY, this.MIN_X, this.MIN_Y);
    }
}
