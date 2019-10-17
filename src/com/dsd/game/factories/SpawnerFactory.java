package com.dsd.game.factories;

import com.dsd.game.Game;
import com.dsd.game.controller.SpawnerController;
import com.dsd.game.enemies.enums.EnemyType;
import com.revivedstandards.handlers.StandardCollisionHandler;

/**
 * This class, when supplied with the information, generates a SpawnerController
 * to add to the game handler.
 *
 * @author Joshua
 */
public class SpawnerFactory {

    public static SpawnerController generateSpawner (EnemyType _id, int xRange, int yRange, long delay,
            int radius, Game _game, StandardCollisionHandler _sch) {
        return new SpawnerController(xRange, yRange, _id, delay, radius, _game, _sch);
    }
}
