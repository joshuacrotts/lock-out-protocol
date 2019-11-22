package com.dsd.game.objects.powerups;

import com.dsd.game.core.Game;
import com.dsd.game.controller.TimerController;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.Weapon;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.TimerInterface;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.controller.StandardFadeController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Health object for when the player is damaged, they can pick it up and restore
 * some health.
 *
 * @author Joshua
 */
public class BerserkPowerup extends StandardGameObject implements TimerInterface, Powerup {

    //  Miscellaneous reference variables
    private final Game game;
    private final Player player;
    private final StandardCamera camera;
    private final StandardCollisionHandler parentContainer;
    private Timer powerupTimer;

    //  View
    private final StandardFadeController color;
    private static final BufferedImage[] BERSERK_FRAMES;
    private static final int BERSERK_FPS = 10;
    private static final int DAMAGE_INCREASE_FACTOR = 2;
    private static final int RECT_STROKE = 20;
    private static final int STROKE_X_OFFSET = (int) (RECT_STROKE * 1.5);
    private static final int STROKE_Y_OFFSET = (int) (RECT_STROKE * 2.4);
    //  Timer for how long the powerup is active (in milliseconds)

    private int timer = 10000;
    private boolean isActivated = false;
    private boolean isCollected = false;

    public BerserkPowerup(int _x, int _y, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Item1);
        this.game = _game;
        this.camera = _game.getCamera();
        this.player = _game.getPlayer();
        this.parentContainer = _sch;
        StandardAnimatorController berserkAnimation = new StandardAnimatorController(this, BERSERK_FRAMES, BERSERK_FPS);
        this.setAnimation(berserkAnimation);
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
        this.color = new StandardFadeController(Color.red, Color.yellow, 0.05f);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            this.getAnimationController().tick();
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.isAlive()) {
            this.getAnimationController().renderFrame(_g2);
        } else if (this.isActivated) {
            this.drawFlashingBorder(_g2);
        }
    }

    /**
     * Turns the timer on and instantiates the associated timer task.
     */
    @Override
    public void activate() {
        if (this.isActivated) {
            return;
        }
        this.playBerserkSFX();
        this.setCollected();
        /**
         * We need to instantiate a new timer in the event that the previous one
         * was canceled.
         */
        this.powerupTimer = new Timer(true);
        this.powerupTimer.schedule(new BerserkTimer(this), timer);
        TimerController.addTimer(this);
        this.isActivated = true;
        this.activateDamageBoost();
    }

    @Override
    public void cancelTimer() {
        this.powerupTimer.cancel();
    }

    /**
     * Adds the 2x damage multiplier to the player's current weapon.
     */
    private void activateDamageBoost() {
        Weapon curr = this.game.getPlayer().getInventory().getCurrentWeapon();
        curr.setDamageFactor(DAMAGE_INCREASE_FACTOR);
    }

    /**
     * Removes the 2x damage multiplier from the player's current weapon.
     */
    private void deactivateDamageBoost() {
        Weapon curr = this.game.getPlayer().getInventory().getCurrentWeapon();
        curr.setDamageFactor(DAMAGE_INCREASE_FACTOR >> 1);
    }

    /**
     * Plays the sound effect associated with collecting the berserk item.
     */
    public void playBerserkSFX() {
        if (this.isCollected) {
            return;
        }
        StandardAudioController.play("src/resources/audio/sfx/berserk.wav", StandardAudioType.SFX);
    }

    /**
     * Draws the flashing border while the powerup is active.
     *
     * @param _g2
     */
    private void drawFlashingBorder(Graphics2D _g2) {
        _g2.setColor(this.getTransparentColor(this.color.combine()));
        Stroke oldStroke = _g2.getStroke();
        _g2.setStroke(new BasicStroke(RECT_STROKE));
        Rectangle view = new Rectangle((int) this.camera.getX() - Screen.gameHalfWidth + RECT_STROKE / 2,
                (int) this.camera.getY() - Screen.gameHalfHeight + RECT_STROKE / 2,
                this.game.getGameWidth() - (int) STROKE_X_OFFSET,
                this.game.getGameHeight() - (int) STROKE_Y_OFFSET);
        _g2.draw(view);
        _g2.setStroke(oldStroke);
    }

    private Color getTransparentColor(Color _c) {
        return new Color(_c.getRed(), _c.getGreen(), _c.getBlue(), 127);
    }

//============================= SETTERS ====================================//
    public void setCollected() {
        this.isCollected = true;
    }

    static {
        BERSERK_FRAMES = Utilities.loadFrames("src/resources/img/items/drops/berserk/", 6);
    }

    private class BerserkTimer extends TimerTask {

        private final BerserkPowerup powerup;

        public BerserkTimer(BerserkPowerup _powerup) {
            this.powerup = _powerup;
        }

        @Override
        public void run() {
            this.powerup.setAlive(false);
            this.powerup.isActivated = false;
            this.powerup.deactivateDamageBoost();
        }
    }

}
