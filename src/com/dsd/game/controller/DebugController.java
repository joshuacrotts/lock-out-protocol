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
 * handler information, particles, etc.)
 *
 * @author Joshua
 */
public class DebugController implements Renderable {

    private final Game game;
    private final Player player;
    private final StandardCamera camera;
    private final StandardCollisionHandler parentContainer;

    public static boolean DEBUG_MODE = false;

    public DebugController (Game _game, StandardCollisionHandler _sch) {
        this.game = _game;
        this.parentContainer = _sch;
        this.camera = this.game.getCamera();
        this.player = this.game.getPlayer();
    }

    @Override
    public void render (Graphics2D _g2) {
        int posX = (int) (this.camera.getX() - Screen.gameHalfWidth / 2) - 300;
        int posY = (int) (this.camera.getY() - Screen.gameHalfHeight / 2) - 150;

        this.renderPlayerCoordinates(_g2, posX, posY);
        this.renderEntityCount(_g2, posX, posY + 40);
        this.renderCameraCoordinates(_g2, posX, posY + 60);
        this.renderDebugText(_g2, (int) this.camera.getX(), (int) this.camera.getY() - Screen.gameHalfHeight);

    }

    private void renderPlayerCoordinates (Graphics2D _g2, int _x, int _y) {
        _g2.setColor(Color.ORANGE);
        _g2.drawString("Player X: " + this.player.getX(), _x, _y);
        _g2.drawString("Player Y: " + this.player.getY(), _x, _y + 20);
    }

    private void renderEntityCount (Graphics2D _g2, int _x, int _y) {
        _g2.setColor(Color.GREEN);
        _g2.drawString("Entities in handler: " + this.parentContainer.getEntities().size(), _x, _y);
    }

    private void renderCameraCoordinates (Graphics2D _g2, int _x, int _y) {
        _g2.setColor(Color.ORANGE);
        _g2.drawString("Camera coordinates: ", _x, _y);
        _g2.drawString("Camera min x: " + (this.camera.getX() - Screen.gameHalfWidth), _x, _y + 20);
        _g2.drawString("Camera min y: " + (this.camera.getY() - Screen.gameHalfHeight), _x, _y + 40);
        _g2.drawString("Camera max x: " + (this.camera.getX() + Screen.gameHalfWidth), _x, _y + 60);
        _g2.drawString("Camera max y: " + (this.camera.getY() + Screen.gameHalfHeight), _x, _y + 80);
    }

    private void renderDebugText (Graphics2D _g2, int _x, int _y) {
        _g2.setColor(Color.YELLOW);
        _g2.drawString("DEBUG MODE", _x, _y + 25);
    }
}
