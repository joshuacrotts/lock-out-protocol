package com.dsd.game.controller;

import com.dsd.game.Game;
import com.dsd.game.enemies.Enemy;
import com.dsd.game.enemies.FemaleMonsterBoss;
import com.dsd.game.enemies.enums.EnemyType;
import com.dsd.game.userinterface.model.BossHealthBar;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;

/**
 * This class is somewhat similar to the SpawnerController, with the exception
 * that it handles boss mobs instead of regular mobs, which run on a timer.
 */
public class BossSpawnerController extends StandardGameObject {

    //  Miscellaneous reference variables
    private final StandardCollisionHandler parentContainer;

    //  Determines which boss to spawn.
    private final EnemyType spawnerID;
    private final Game game;

    public BossSpawnerController (int _x, int _y, EnemyType _id, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Spawner);
        this.game = _game;
        this.spawnerID = _id;
        this.parentContainer = _sch;
        this.addBoss();
    }

    private void addBoss () {
        int xPos = (int) this.getX();
        int yPos = (int) this.getY();
        Enemy enemy = null;

        //  Depending on what type of spawner we have, we spawn that type of monster.
        //  Eventually we will probably want to use reflection classes to make this easier/cleaner.
        switch (this.spawnerID) {
            case FEMALE_BOSS_MONSTER:
                enemy = new FemaleMonsterBoss(xPos, yPos, this.game, this.parentContainer);
                this.game.getHUDScreen().addInteractor(new BossHealthBar(game, enemy));
                break;
        }
        this.parentContainer.addEntity(enemy);
    }

    @Override
    public void tick () {
    }

    @Override
    public void render (Graphics2D gd) {
    }

}
