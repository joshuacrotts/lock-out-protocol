package com.dsd.game.objects.powerups;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Health object for when the player is damaged, they can pick it up and restore
 * some health.
 *
 * @author Joshua
 */
public class HealthPowerup extends StandardGameObject implements Powerup{

    private final Player player;
    private final StandardCollisionHandler parentContainer;
    private static final BufferedImage[] HEALTH_FRAMES;

    private static final int HEALTH_FPS = 10;
    private static final int HEALTH_INCREASE = 25;

    public HealthPowerup (int _x, int _y, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Powerup);
        this.player = _game.getPlayer();
        this.parentContainer = _sch;
        StandardAnimatorController healthAnimation = new StandardAnimatorController(new StandardAnimation(this, HEALTH_FRAMES, HEALTH_FPS, 12));
        this.setAnimation(healthAnimation);
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
    }

    @Override
    public void tick () {
        if (this.isAlive()) {
            this.getAnimationController().tick();
        }
        else {
            StandardAudioController.play("src/resources/audio/sfx/restore_health.wav", StandardAudioType.SFX);
            this.parentContainer.removeEntity(this);
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isAlive()) {
            this.getAnimationController().renderFrame(_g2);
        }
    }
    
    public void activate() {
        this.addHealth();
    }

    /**
     * Adds health back to the player. If the value goes above 200, we clamp it
     * to 200.
     */
    public void addHealth () {
        int pHealth = (int) (this.player.getHealth() + HEALTH_INCREASE);
        pHealth = Utilities.clamp(pHealth, 0, this.player.getMaxHealth());
        this.player.setHealth(pHealth);
    }

    //static value
    static {
        HEALTH_FRAMES = Utilities.loadFrames("src/resources/img/items/drops/health/", 22);
    }
}
