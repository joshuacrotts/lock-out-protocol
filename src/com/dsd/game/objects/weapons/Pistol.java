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
 * This class is a subclass of Gun; it acts as a standard pistol.
 *
 * @author Joshua
 */
public class Pistol extends Gun {

    private static final BufferedImage[] walkPistolImages;
    private static final BufferedImage[] shootPistolImages;

    private static final int walkingFPS = 10;
    private static final int shootGunFPS = 20;

    private final int delay = 1250;

    public Pistol (Game _game, Player _player, StandardCollisionHandler _sch) {
        super("pistol", 16, _game, _player, _sch);

        //  Instantiates the animation controllers
        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, walkPistolImages, walkingFPS));

        StandardAnimatorController shootingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, shootPistolImages, shootGunFPS));

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
        walkPistolImages = Utilities.loadFrames("src/res/img/player/player_walk_gun/", 6);
        shootPistolImages = Utilities.loadFrames("src/res/img/player/player_shoot_gun/", 5);
    }
}