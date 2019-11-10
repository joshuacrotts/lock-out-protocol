package com.dsd.game.controller;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.view.Renderable;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Debug mode controller; draws information about the game (such as coordinates,
 * handler information, particles, etc.).
 *
 * @author Joshua, Ronald, Rinty
 */
public class DebugController implements Renderable {

    //  Miscellaneous reference variables.
    private final Game game;
    private final Player player;
    private final StandardCamera camera;
    private final StandardCollisionHandler parentContainer;
    //  This variable determines if the game is in debug mode or not.
    public static boolean DEBUG_MODE = false;
    //  Offset variables for text.
    private final int GLOBAL_X_OFFSET = 300;
    private final int GLOBAL_Y_OFFSET = 150;
    //  Player text offsets.
    private final int PLAYER_Y_OFFSET = 20;
    private final int PLAYER_WIDTH_OFFSET = 40;
    private final int PLAYER_HEIGHT_OFFSET = 60;
    //  Camera text offsets.
    private final int CAMERA_MIN_X_OFFSET = 20;
    private final int CAMERA_MAX_X_OFFSET = 60;
    private final int CAMERA_MIN_Y_OFFSET = 40;
    private final int CAMERA_MAX_Y_OFFSET = 80;
    //  Offsets for drawing the actual information in the top-left.
    private final int DEBUG_MODE_TEXT_Y_OFFSET = 20;
    private final int ENTITY_COUNT_Y_OFFSET = 80;
    private final int CAMERA_COORDINATE_Y_OFFSET = 100;

    public DebugController (Game _game, StandardCollisionHandler _sch) {

        this.game = _game;
        this.parentContainer = _sch;
        this.camera = this.game.getCamera();
        this.player = this.game.getPlayer();
    }

    @Override
    public void render (Graphics2D _g2) {

        int posX = (int) (this.camera.getX() - Screen.gameFourthWidth) - GLOBAL_X_OFFSET;
        int posY = (int) (this.camera.getY() - Screen.gameFourthHeight) - GLOBAL_Y_OFFSET;

        this.renderPlayerCoordinates(_g2, posX, posY);
        this.renderEntityCount(_g2, posX, posY + ENTITY_COUNT_Y_OFFSET);
        this.renderCameraCoordinates(_g2, posX, posY + CAMERA_COORDINATE_Y_OFFSET);
        this.renderDebugText(_g2, (int) this.camera.getX(), (int) this.camera.getY() - Screen.gameHalfHeight);
    }

    /**
     * Draws the player's coordinates and dimensions to the screen when debug
     * mode is enabled.
     *
     * @param _g2
     * @param _x
     * @param _y
     */
    private void renderPlayerCoordinates (Graphics2D _g2, int _x, int _y) {

        _g2.setColor(Color.ORANGE);
        _g2.drawString("Player X: " + this.player.getX(), _x, _y);
        _g2.drawString("Player Y: " + this.player.getY(), _x, _y + PLAYER_Y_OFFSET);
        _g2.drawString("Player sprite width: " + this.player.getWidth(), _x, _y + PLAYER_WIDTH_OFFSET);
        _g2.drawString("Player sprite height: " + this.player.getHeight(), _x, _y + PLAYER_HEIGHT_OFFSET);
    }

    /**
     * Renders how many entities are in the current StandardCollisionHandler
     * when debug mode is enabled.
     *
     * @param _g2
     * @param _x
     * @param _y
     */
    private void renderEntityCount (Graphics2D _g2, int _x, int _y) {

        _g2.setColor(Color.GREEN);
        _g2.drawString("Entities in handler: " + this.parentContainer.getEntities().size(), _x, _y);
    }

    /**
     * Renders the camera coordinates (min x/y, max x/y) to the screen (showing
     * the viewport) when debug mode is enabled.
     *
     * @param _g2
     * @param _x
     * @param _y
     */
    private void renderCameraCoordinates (Graphics2D _g2, int _x, int _y) {

        _g2.setColor(Color.ORANGE);
        _g2.drawString("Camera coordinates: ", _x, _y);
        _g2.drawString("Camera min x: " + (this.camera.getX() - Screen.gameHalfWidth), _x, _y + CAMERA_MIN_X_OFFSET);
        _g2.drawString("Camera min y: " + (this.camera.getY() - Screen.gameHalfHeight), _x, _y + CAMERA_MIN_Y_OFFSET);
        _g2.drawString("Camera max x: " + (this.camera.getX() + Screen.gameHalfWidth), _x, _y + CAMERA_MAX_X_OFFSET);
        _g2.drawString("Camera max y: " + (this.camera.getY() + Screen.gameHalfHeight), _x, _y + CAMERA_MAX_Y_OFFSET);
    }

    /**
     * Renders the DEBUG MODE text label at the top of the screen when debug
     * mode is enabled.
     *
     * @param _g2
     * @param _x
     * @param _y
     */
    private void renderDebugText (Graphics2D _g2, int _x, int _y) {

        _g2.setColor(Color.YELLOW);
        _g2.drawString("DEBUG MODE", _x, _y + DEBUG_MODE_TEXT_Y_OFFSET);
    }

}
