package com.dsd.game.objects.weapons;

import com.dsd.game.objects.Player;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.model.StandardAnimation;

/**
 * This class is a subclass of Weapon; it is a standard knife with melee damage.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class Knife extends Weapon {

    private static final int walkingFPS = 10;
    private static final int attackKnifeFPS = 20;

    private final int delay = 1250;
    private final int knifeDamage = 2;

    public Knife (Player _player) {
        super("knife");
        //  Instantiates the animation controllers
        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, Utilities.loadFrames("src/res/img/player/player_walk_knife/", 6), walkingFPS));

        StandardAnimatorController shootingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, Utilities.loadFrames("src/res/img/player/player_attack_knife/", 8), attackKnifeFPS));

        this.setWalkFrames(walkingAnimation);
        this.setAttackFrames(shootingAnimation);
        super.setDamage(knifeDamage);
        super.setDelay(delay);
    }
}
