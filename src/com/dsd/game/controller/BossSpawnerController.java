/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.controller;

import com.dsd.game.Game;
import com.dsd.game.enemies.enums.EnemyType;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;

/**
 *
 * @author LJCROTTS
 */
public class BossSpawnerController extends StandardGameObject {

    //  Miscellaneous reference variables
    private final StandardCollisionHandler parentContainer;
    private final EnemyType spawnerID;
    private final Game game;

    public BossSpawnerController(int _x, int _y, EnemyType _id, long _delay, int _radius, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Spawner);
        this.game = _game;
        this.spawnerID = _id;
        this.parentContainer = _sch;
        this.addBoss();
    }
    
    private void addBoss() {
        
    }
    
    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D gd) {
    }

}
