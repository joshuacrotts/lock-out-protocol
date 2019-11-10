package com.dsd.game.factories;

import com.dsd.game.Game;
import com.dsd.game.controller.BossSpawnerController;
import com.dsd.game.controller.SpawnerController;
import com.dsd.game.enemies.enums.EnemyType;
import com.revivedstandards.handlers.StandardCollisionHandler;

/**
 * This class, when supplied with the information, generates a SpawnerController
 * to add to the game handler.
 *
 * @author Joshua, Ronald, Rinty
 */
public class SpawnerFactory {

    /**
     * Spawns an arbitrary enemy spawner at some location in the world.
     *
     * @param _id
     * @param _xRange
     * @param _yRange
     * @param _delay
     * @param _radius
     * @param _game
     * @param _sch
     * @return
     */
    public static SpawnerController generateSpawner (EnemyType _id, int _xRange,
            int _yRange, long _delay, int _radius, Game _game, StandardCollisionHandler _sch) {
        
        return new SpawnerController(_xRange, _yRange, _id, _delay, _radius, _game, _sch);
    }

    /**
     * Spawns a boss spawner at some location in the world.
     *
     * @param _bossID
     * @param _xRange
     * @param _yRange
     * @param _game
     * @param _sch
     * @return
     */
    public static BossSpawnerController generateBossSpawner (EnemyType _bossID,
            int _xRange, int _yRange, Game _game, StandardCollisionHandler _sch) {
        
        return new BossSpawnerController(_xRange, _yRange, _bossID, _game, _sch);
    }
    
}
