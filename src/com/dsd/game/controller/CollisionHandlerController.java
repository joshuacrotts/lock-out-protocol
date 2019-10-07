package com.dsd.game.controller;

import com.dsd.game.Game;
import com.dsd.game.PlayerState;
import com.dsd.game.objects.BulletGameObject;
import com.dsd.game.enemies.Enemy;
import com.dsd.game.objects.Health;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.items.Coin;
import com.dsd.game.userinterface.StandardInteractorHandler;
import com.dsd.game.userinterface.model.DamageText;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 * StandardCollisionHandler has a method integrated in it: handleCollision(obj1,
 * obj2), and it needs a subclass to override it so the handler knows what to do
 * when two SGO's collide.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class CollisionHandlerController extends StandardCollisionHandler {

    //
    //  We can probably decouple this later, but this is the handler
    //  that holds all damageText objects.
    //
    private static StandardInteractorHandler damageText;

    public CollisionHandlerController (Game _game) {
        super(_game.getCamera());
        CollisionHandlerController.damageText = new StandardInteractorHandler(_game);
    }

    @Override
    public void tick () {
        super.tick();
        damageText.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
        damageText.render(_g2);
    }

    /**
     * This method will be called from the SCH; it is designed to allow the user
     * to implement any collision reaction they want. If a direct collision
     * occurs, this method is called, which has the two entities that collided.
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
        if (_obj1.getId() == StandardID.Bullet && _obj2 instanceof Enemy) {
            this.handleBulletEnemyCollision((BulletGameObject) _obj1, (Enemy) _obj2);
        }
    }

    /**
     * This method will be called from the SCH; it is designed to allow the user
     * to implement any AABB collision reaction they want. If the bounds of
     * _obj1 intersects the bounds of _obj2 (as opposed to just touching), this
     * method is called.
     *
     * @param _obj1
     * @param _obj2
     */
    @Override
    public void handleBoundsCollision (StandardGameObject _obj1, StandardGameObject _obj2) {
        if (_obj1.getId() == StandardID.Player && _obj2 instanceof Enemy && _obj2.isAlive()) {
            this.handlePlayerMonsterCollision((Player) _obj1, (Enemy) _obj2);
        }
        else if (_obj1.getId() == StandardID.Player && _obj2.getId() == StandardID.Coin && _obj2.isAlive()) {
            this.handlePlayerCoinCollision((Player) _obj1, (Coin) _obj2);
        }
        else if (_obj1.getId() == StandardID.Player && _obj2 instanceof Health) {
            this.handlePlayerHealthCollision((Player) _obj1, (Health) _obj2);
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
    private void handleBulletEnemyCollision (BulletGameObject _bullet, Enemy _monster) {

        // Sets the bullet to dead
        _bullet.setAlive(false);

        // Casts the obj2 to a Monster so we can deduct health from it
        _monster.setHealth(_monster.getHealth() - _bullet.getDamage());

        if (_monster.isAlive()) {
            // Plays random monster hurt sfx
            _monster.generateHurtSound(StdOps.rand(1, 5));
            this.addDamageText(_monster, _bullet.getDamage());
        }
    }

    /**
     *
     * If a monster hits the player, the player will take damage according to
     * the monster's type (for now?).
     *
     * @param _player
     * @param _monster
     */
    private void handlePlayerMonsterCollision (Player _player, Enemy _monster) {
        System.out.println("here");
        System.out.println(_player.isAttacking());
        _player.setHealth(_player.getHealth() - _monster.getDamage());
        if (_player.isAttacking()) {
            int dmg = (int) _player.getInventory().getCurrentWeapon().getDamage();
            this.addDamageText(_monster, dmg);

            _monster.setHealth(_monster.getHealth() - dmg);
            _monster.generateHurtSound(StdOps.rand(1, 5));
            _player.setPlayerState(PlayerState.STANDING);
        }
        System.out.println("after "+_player.isAttacking());
    }

    /**
     * Collision for when the player runs into a coin object.
     *
     * @param _player
     * @param _coin
     */
    private void handlePlayerCoinCollision (Player _player, Coin _coin) {
        _player.setMoney(_player.getMoney() + _coin.getValue());
        _coin.setAlive(false);
        StandardAudioController.play("src/resources/audio/sfx/coin.wav");
    }

    private void handlePlayerHealthCollision (Player _player, Health _health) {
        _health.addHealth();
        _health.setAlive(false);
    }

    /**
     * Adds the text _damage above the Enemy _monster's body.
     *
     * @param _monster
     * @param _damage
     */
    private void addDamageText (Enemy _monster, int _damage) {
        damageText.addInteractor(new DamageText((int) _monster.getX() + _monster.getWidth() / 2,
                (int) _monster.getY(), "-" + _damage, damageText));
    }
}
