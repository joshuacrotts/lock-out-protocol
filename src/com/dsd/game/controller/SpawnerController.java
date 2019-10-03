/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.controller;

import com.dsd.game.Game;
import com.dsd.game.objects.BasicMonster;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Joshua
 */
public class SpawnerController extends StandardGameObject {

    private final StandardCollisionHandler parentContainer;
    private final StandardID spawnerID;
    private final Game game;
    private final Timer spawnerTimer;

    private final long delay;
    private final int radius;

    public SpawnerController (int _x, int _y, StandardID _id, long _delay, int _radius, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Tile1);

        this.game = _game;
        this.spawnerID = _id;
        this.parentContainer = _sch;
        this.delay = _delay;
        this.radius = _radius;
        this.spawnerTimer = new Timer(true);
        this.spawnerTimer.scheduleAtFixedRate(new SpawnerDelayTimer(this), this.delay, this.delay);
    }

    @Override
    public void tick () {
    }

    @Override
    public void render (Graphics2D _gd) {
    }

    protected void spawn (int _n) {

        for (int i = 0 ; i < _n ; i++) {
            int xPos = (int) StdOps.rand(this.getX() - this.radius, this.getX() + this.radius);
            int yPos = (int) StdOps.rand(this.getY() - this.radius, this.getY() + this.radius);

            switch (this.spawnerID) {
                case BasicMonster:
                    this.parentContainer.addEntity(new BasicMonster(xPos, yPos, this.game, this.game.getCamera(), this.parentContainer));
            }
        }
    }

    private class SpawnerDelayTimer extends TimerTask {

        private final SpawnerController spawnerController;

        public SpawnerDelayTimer (SpawnerController _spawnerController) {
            this.spawnerController = _spawnerController;
        }

        @Override
        public void run () {
            this.spawnerController.spawn(1);
        }
    }

}
