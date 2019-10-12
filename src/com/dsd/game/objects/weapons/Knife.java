package com.dsd.game.objects.weapons;

import com.dsd.game.objects.weapons.enums.WeaponType;
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

    private static final int WALKING_FPS = 10;
    private static final int ATTACK_KNIFE_FPS = 20;
    private final int DELAY = 1250;
    private final int KNIFE_DAMAGE = 20;

    public Knife (Player _player) {
        super(WeaponType.KNIFE);
        //  Instantiates the animation controllers
        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, Utilities.loadFrames("src/resources/img/player/player_walk_knife/", 6), WALKING_FPS));
        StandardAnimatorController shootingAnimation = new StandardAnimatorController(
                new StandardAnimation(_player, Utilities.loadFrames("src/resources/img/player/player_attack_knife/", 8), ATTACK_KNIFE_FPS));
        this.setWalkFrames(walkingAnimation);
        this.setAttackFrames(shootingAnimation);
        super.setDamage(this.KNIFE_DAMAGE);
        super.setDelay(this.DELAY);
    }
}
