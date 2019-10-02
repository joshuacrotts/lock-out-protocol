/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Joshua
 */
public class Minimap extends Interactor {

    private final Game game;
    private final StandardCollisionHandler globalHandler;
    private final int MINIMAP_SCALE = 20;

    private final int MMX_OFFSET = 230;
    private final int MMY_OFFSET = 20;

    private final int MAP_DIMENSION = 200;
    private final int OBJECT_DIMENTION = 5;

    private final Color transparentBlack = new Color(0f, 0f, 0f, 0.5f);

    public Minimap(Game _game, StandardCollisionHandler _sch) {
        this.game = _game;
        this.globalHandler = _sch;
    }

    @Override
    public void render(Graphics2D _g2) {
        this.drawMapBackground(_g2);

        for (int i = 0; i < this.globalHandler.size(); i++) {
            StandardGameObject obj = this.globalHandler.get(i);
            if (obj.isAlive()) {
                if (obj.getId() == StandardID.Player) {
                    this.drawObject(_g2, obj, Color.BLUE);

                } else if (obj.getId() == StandardID.Enemy3) {
                    this.drawObject(_g2, obj, Color.RED);

                }
            }
        }
    }

    private void drawMapBackground(Graphics2D _g2) {
        _g2.setColor(transparentBlack);
        _g2.fillRect((int) this.game.getCamera().getX() + Screen.gameHalfWidth - this.MMX_OFFSET,
                (int) this.game.getCamera().getY() - Screen.gameHalfHeight + this.MMY_OFFSET,
                MAP_DIMENSION, this.MAP_DIMENSION);
    }

    private void drawObject(Graphics2D _g2, StandardGameObject obj, Color color) {
        _g2.setColor(color);
        _g2.fillRect((int) (this.game.getCamera().getX() + Screen.gameHalfWidth - this.MMX_OFFSET + (obj.getX() / this.MINIMAP_SCALE)),
                (int) (this.game.getCamera().getY() - Screen.gameHalfHeight + this.MMY_OFFSET + (obj.getY() / this.MINIMAP_SCALE)),
                OBJECT_DIMENTION, OBJECT_DIMENTION);

    }

    @Override
    public void tick() {
    }

    @Override
    public void onMouseClick() {
    }

    @Override
    public void onMouseEnterHover() {
    }

    @Override
    public void onMouseExitHover() {
    }

}
