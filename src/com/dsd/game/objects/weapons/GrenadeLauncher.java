package com.dsd.game.objects.weapons;

import com.dsd.game.Game;
import com.dsd.game.objects.weapons.projectiles.GrenadeBulletObject;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardAnimation;

/**
 * This class is a subclass of Gun; it's an automatic rifle.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class GrenadeLauncher extends Gun {

    //  FPS variables for how fast the rifle frames animate.
    private static final int WALKING_FPS = 10;
    private static final int SHOOT_GUN_FPS = 20;

    //  Delay between shooting one bullet and the next (in ms).
    private final int DELAY = 2500;

    //  Damage from the rifle.
    private static final int BULLET_DAMAGE = 250;

    public GrenadeLauncher (Game _game, Player _player, StandardCollisionHandler _sch) {
        super(WeaponType.GRENADE_LAUNCHER, 4, _game, _player, _sch);
        //  Instantiates the animation controllers.
        this.loadAssets(_player);
        super.setDelay(DELAY);
    }

    @Override
    public void shoot () {
        this.addBullet();
        super.deductAmmo();
    }

    /**
     * Adds a bullet to the global handler.
     */
    private void addBullet () {
        super.getHandler().addEntity(new GrenadeBulletObject(
                (int) super.getPlayer().getX() + super.getPlayer().getWidth() / 2,
                (int) super.getPlayer().getY() + super.getPlayer().getHeight() / 2,
                super.getPlayer().getAngle(), BULLET_DAMAGE * this.getDamageFactor(),
                super.getGame(), super.getHandler(), super.getPlayer()));
    }

    @Override
    public void loadAssets (Player _player) {
        //  Instantiates the animation controllers.
        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, Utilities.loadFrames("src/resources/img/player/player_" + _player.getPlayerSex() + "/player_walk_grenade_launcher/", 6), WALKING_FPS));
        StandardAnimatorController shootingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, Utilities.loadFrames("src/resources/img/player/player_" + _player.getPlayerSex() + "/player_shoot_grenade_launcher/", 4), SHOOT_GUN_FPS));
        super.setWalkFrames(walkingAnimation);
        super.setAttackFrames(shootingAnimation);
    }
}
