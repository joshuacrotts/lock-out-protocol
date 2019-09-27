package com.dsd.game.controller;

import com.dsd.game.objects.BulletGameObject;
import com.dsd.game.objects.Monster;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 * StandardCollisionHandler has a method integrated in it: handleCollision(obj1,
 * obj2), and it needs a subclass to override it so the handler knows what to do
 * when two SGO's collide.
 */
public class CollisionHandlerController extends StandardCollisionHandler {

    public CollisionHandlerController (StandardCamera _sc) {
        super(_sc);
    }

    @Override
    public void tick () {
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }

    /**
     * This method will be called from the SCH; it is designed to allow the user
     * to implement any collision reaction they want. If a collision occurs,
     * this method is called, which has the two entities that collided.
     *
     * @param _obj1
     * @param _obj2
     */
    @Override
    public void handleCollision (StandardGameObject _obj1, StandardGameObject _obj2) {
        //
        //  Handles bullet to monster collision
        //  (kills bullet and takes damage away from monster).
        //
        if (_obj1 instanceof BulletGameObject && _obj2 instanceof Monster) {
            this.handleBulletMonsterCollision((BulletGameObject) _obj1, (Monster) _obj2);
        }
    }

    /**
     * If a bullet hits a monster (shot by the player), we will destroy the
     * bullet, deduct the bullet's damage from the monster, and determine if the
     * monster is alive or not.
     *
     * @param bullet
     * @param monster
     */
    private void handleBulletMonsterCollision (BulletGameObject bullet, Monster monster) {

        // Sets the bullet to dead
        bullet.setAlive(false);

        // Casts the obj2 to a Monster so we can deduct health from it
        monster.setHealth(monster.getHealth() - bullet.getDamage());

        // If the monster's health is less than 0, we can flag it as dead.
        monster.setAlive(monster.getHealth() > 0);

        if (monster.isAlive()) {
            // Plays random monster hurt sfx
            monster.generateHurtSound(StdOps.rand(1, 5));
        }
    }
}
