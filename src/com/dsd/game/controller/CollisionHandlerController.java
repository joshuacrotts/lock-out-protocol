package com.dsd.game.controller;

import com.dsd.game.objects.BulletGameObject;
import com.dsd.game.objects.Monster;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;

/**
 * StandardCollisionHandler has a method integrated in it:
 * handleCollision(obj1, obj2), and it needs a subclass to override
 * it so the handler knows what to do when two SGO's collide.
 */
public class CollisionHandlerController extends StandardCollisionHandler {

    public CollisionHandlerController (StandardCamera sc) {
        super(sc);
    }

    @Override
    public void tick () {
        super.tick();
    }

    @Override
    public void render (Graphics2D g2) {
        super.render(g2);
    }

    @Override
    public void handleCollision (StandardGameObject obj1, StandardGameObject obj2) {
        if (obj1 instanceof BulletGameObject && obj2 instanceof Monster) {
            // Sets the bullet to dead
            BulletGameObject bullet = (BulletGameObject) obj1;
            bullet.setAlive(false);

            // Casts the obj2 to a Monster so we can deduct health from it
            Monster monster = (Monster) obj2;
            monster.setHealth(monster.getHealth() - bullet.getDamage());

            // If the monster's health is less than 0, we can flag it as dead.
            monster.setAlive(monster.getHealth() > 0);
        }
    }
}
