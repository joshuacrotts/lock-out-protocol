package com.dsd.game.controller;

import com.dsd.game.Game;
import com.dsd.game.enemies.BasicMonster;
import com.dsd.game.enemies.DarkFemaleMonster;
import com.dsd.game.enemies.GreenMonster;
import com.dsd.game.enemies.RedHeadMonster;
import com.dsd.game.enemies.TinyMonster;
import com.dsd.game.enemies.enums.EnemyType;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is a "mob-spawner". As in, using a pre-specified radius and
 * duration, it will randomly spawn n EnemyType objects within the radius.
 *
 * @author Joshua
 */
public class SpawnerController extends StandardGameObject {

    //  Miscellaneous reference variables
    private final StandardCollisionHandler parentContainer;
    private final EnemyType spawnerID;
    private final Game game;

    //  Timer object controlling the spawn-rate.
    private final Timer spawnerTimer;

    //  Delay and radius of the timer.
    private final long delay;
    private final int radius;

    public SpawnerController(int _x, int _y, EnemyType _id, long _delay, int _radius, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Spawner);
        this.game = _game;
        this.spawnerID = _id;
        this.parentContainer = _sch;
        this.delay = _delay;
        this.radius = _radius;
        this.spawnerTimer = new Timer(true);
        this.spawnerTimer.scheduleAtFixedRate(new SpawnerDelayTimer(this, this.game), this.delay, this.delay);

        TimerController.addTimer(spawnerTimer);
    }

    @Override
    public void tick() {
        //throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics2D gd) {
        //throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Spawns _n mobs randomly in the specified radius of the spawner.
     *
     * @param _n
     */
    protected void spawn(int _n) {

        for (int i = 0; i < _n; i++) {
            int xPos = (int) StdOps.rand(this.getX() - this.radius, this.getX() + this.radius);
            int yPos = (int) StdOps.rand(this.getY() - this.radius, this.getY() + this.radius);

            //  Depending on what type of spawner we have, we spawn that type of monster.
            //  Eventually we will probably want to use reflection classes to make this easier/cleaner.
            switch (this.spawnerID) {
                case BASIC_MONSTER:
                    this.parentContainer.addEntity(new BasicMonster(xPos, yPos, this.game, this.parentContainer));
                    break;
                case GREEN_MONSTER:
                    this.parentContainer.addEntity(new GreenMonster(xPos, yPos, this.game, this.parentContainer));
                    break;
                case DARK_FEMALE_MONSTER:
                    this.parentContainer.addEntity(new DarkFemaleMonster(xPos, yPos, this.game, this.parentContainer));
                    break;
                case RED_HEAD_MONSTER:
                    this.parentContainer.addEntity(new RedHeadMonster(xPos, yPos, this.game, this.parentContainer));
                    break;
                case TINY_MONSTER:
                    this.parentContainer.addEntity(new TinyMonster(xPos, yPos, this.game, this.parentContainer));
            }
        }
    }

    /**
     * Very similar to the AttackCommand, we need a delay timer for mobs
     * spawning. We only want mobs to spawn at a certain interval, so this
     * allows for that.
     */
    private class SpawnerDelayTimer extends TimerTask {

        private final SpawnerController spawnerController;
        private final Game game;

        public SpawnerDelayTimer(SpawnerController _spawnerController, Game _game) {
            this.spawnerController = _spawnerController;
            this.game = _game;
        }

        @Override
        public void run() {
            /**
             * If we're not paused AND the game isn't in its preamble state, we
             * can spawn the entities.
             */
            if (this.game.isPaused() || this.game.isPreamble()) {
                return;
            }
            this.spawnerController.spawn(StdOps.rand(1, 2));
        }
    }
}
