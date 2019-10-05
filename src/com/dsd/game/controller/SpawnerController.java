package com.dsd.game.controller;

import com.dsd.game.EnemyType;
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
 * This class is a "mob-spawner". As in, using a pre-specified radius and
 * duration, it will randomly spawn n EnemyType objects within the radius.
 *
 * @author Joshua
 */
public class SpawnerController extends StandardGameObject {

    //
    //  Miscellaneous reference variables
    //
    private final StandardCollisionHandler parentContainer;
    private final EnemyType spawnerID;
    private final Game game;

    //
    //  Timer object controlling the spawn-rate.
    //
    private final Timer spawnerTimer;

    //
    //  Delay and radius of the timer.
    //
    private final long delay;
    private final int radius;

    public SpawnerController (int _x, int _y, EnemyType _id, long _delay, int _radius, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Enemy);

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

    /**
     * Spawns _n mobs randomly in the specified radius of the spawner.
     *
     * @param _n
     */
    protected void spawn (int _n) {

        for (int i = 0 ; i < _n ; i++) {
            int xPos = (int) StdOps.rand(this.getX() - this.radius, this.getX() + this.radius);
            int yPos = (int) StdOps.rand(this.getY() - this.radius, this.getY() + this.radius);

            switch (this.spawnerID) {
                case BASIC_MONSTER:
                    this.parentContainer.addEntity(new BasicMonster(xPos, yPos, this.game, this.parentContainer));
            }
        }
    }

    //
    //  Very similar to the AttackCommand, we need a delay
    //  slash timer for mobs spawning. We only want mobs to
    //  spawn at a certain interval, so this allows for that.
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
