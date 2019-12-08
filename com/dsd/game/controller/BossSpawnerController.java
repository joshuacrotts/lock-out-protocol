package com.dsd.game.controller;

import com.dsd.game.core.Game;
import com.dsd.game.enemies.Enemy;
import com.dsd.game.enemies.FemaleMonsterBoss;
import com.dsd.game.enemies.enums.EnemyType;
import com.dsd.game.userinterface.TimerInterface;
import com.dsd.game.userinterface.model.BossHealthBar;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is somewhat similar to the SpawnerController, with the exception
 * that it handles boss mobs instead of regular mobs, which run on a timer.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/12/19
 */
public class BossSpawnerController extends StandardGameObject implements TimerInterface {

    //  Miscellaneous reference variables.
    private final Game game;
    private final StandardCollisionHandler parentContainer;

    //  Determines which boss to spawn.
    private final EnemyType spawnerID;

    //  Delay for the spawning of the boss.
    private final int bossSpawnDelay = 10000;

    //  Timer that continuously checks if we're ready to spawn a boss.
    private final Timer bossTimer;

    //  If we already have a boss currently summoned (and actively battling),
    //  we shouldn't add another one.
    protected boolean hasBoss = false;

    public BossSpawnerController(int _x, int _y, EnemyType _id, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Spawner);
        this.game = _game;
        this.spawnerID = _id;
        this.parentContainer = _sch;
        this.bossTimer = new Timer(true);
        this.bossTimer.scheduleAtFixedRate(new BossSpawnerController.BossSpawnerDelayTimer(this, this.game),
                this.bossSpawnDelay, this.bossSpawnDelay);
        TimerController.addTimer(this);
    }

    /**
     * Adds a boss to the game. This method only runs once per wave instead of a
     * continuous timer like spawners for regular enemies do.
     */
    public void addBoss() {
        int xPos = (int) this.getX();
        int yPos = (int) this.getY();
        Enemy enemy = null;
        //  Depending on what type of spawner we have, we spawn that type of monster.
        //  Eventually we will probably want to use reflection classes to make this easier/cleaner.
        switch (this.spawnerID) {
            case FEMALE_BOSS_MONSTER:
                enemy = new FemaleMonsterBoss(xPos, yPos, this.game, this.parentContainer);
                this.game.getHUDScreen().addInteractor(new BossHealthBar(this.game, enemy));
                break;
            default:
                throw new IllegalStateException("Invalid boss spawner type!");
        }
        this.parentContainer.addEntity(enemy);
    }

    @Override
    public void cancelTimer() {
        this.bossTimer.cancel();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D _gd) {
    }

    /**
     * The only reason we need a delay timer is to continue checking the game's
     * level controller to determine if we're on a level that can spawn a boss
     * or not. If so, then we go ahead and do so.
     */
    private class BossSpawnerDelayTimer extends TimerTask {

        private final BossSpawnerController spawnerController;
        private final Game game;

        public BossSpawnerDelayTimer(BossSpawnerController _spawnerController, Game _game) {
            this.spawnerController = _spawnerController;
            this.game = _game;
        }

        @Override
        public void run() {
            /**
             * If we're not paused AND the game isn't in its preamble state, AND
             * we don't already HAVE A boss AND we're on the correct wave, spawn
             * one.
             */
            if (this.game.isPaused() || this.game.isPreamble()
                    || this.game.isShop() || this.spawnerController.hasBoss) {
                return;
            } else if (this.game.getLevelController().getWaveNumber()
                    % this.game.getLevelController().getBossSpawnInterval() != 0) {
                return;
            }
            this.spawnerController.addBoss();
            this.spawnerController.hasBoss = true;
        }
    }
}
