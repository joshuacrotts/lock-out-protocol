package com.dsd.game.objects.weapons;

import com.dsd.game.Game;
import com.dsd.game.objects.BulletGameObject;
import com.dsd.game.objects.Player;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardAnimation;
import java.awt.image.BufferedImage;

/**
 * This class is a subclass of Gun; it's an automatic rifle.
 *
 * @author Joshua
 */
public class Rifle extends Gun {

    //
    //  @TODO: Refactor these into static methods to reduce instance vars
    //
    private static final BufferedImage[] walkRifleImages;
    private static final BufferedImage[] shootRifleImages;

    private static final int walkingFPS = 10;
    private static final int shootGunFPS = 20;

    private final int delay = 100;

    public Rifle (Game _game, Player _player, StandardCollisionHandler _sch) {
        super("rifle", 31, _game, _player, _sch);

        //  Instantiates the animation controllers
        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, walkRifleImages, walkingFPS));

        StandardAnimatorController shootingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, shootRifleImages, shootGunFPS));

        super.setWalkFrames(walkingAnimation);
        super.setAttackFrames(shootingAnimation);
        super.setDelay(delay);

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
        super.getHandler().addEntity(new BulletGameObject(
                (int) super.getPlayer().getX() + super.getPlayer().getWidth() / 2,
                (int) super.getPlayer().getY() + super.getPlayer().getHeight() / 2,
                super.getPlayer().getAngle(), super.getGame(), super.getHandler(),
                super.getPlayer().getCamera(), super.getPlayer()));
    }

    //
    //  Initializes the images used in the animation frames
    //
    static {
        walkRifleImages = Utilities.loadFrames("src/res/img/player/player_walk_rifle/", 6);
        shootRifleImages = Utilities.loadFrames("src/res/img/player/player_shoot_rifle/", 4);
    }
}
