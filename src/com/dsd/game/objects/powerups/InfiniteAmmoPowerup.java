package com.dsd.game.objects.powerups;

import com.dsd.game.Game;
import com.dsd.game.controller.TimerController;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.Gun;
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
 * @author Joshua, Ronald, Rinty
 */
public class InfiniteAmmoPowerup extends StandardGameObject implements TimerInterface, Powerup {

    // Miscellaneous reference variables
    private final Game game;
    private final Player player;
    private final StandardCamera camera;
    private final StandardCollisionHandler parentContainer;
    private Timer powerupTimer;
    //  View
    private final StandardFadeController color;
    private static final BufferedImage[] INFINITE_AMMO_FRAMES;
    private static final int INF_AMMO_FPS = 13;
    private static final int RECT_STROKE = 20;
    private static final int STROKE_X_OFFSET = (int) (RECT_STROKE * 1.5);
    private static final int STROKE_Y_OFFSET = (int) (RECT_STROKE * 2.4);
    //  Timer for how long the powerup is active (in milliseconds)
    private int timer = 10000;
    private boolean isActivated = false;
    private boolean isCollected = false;

    public InfiniteAmmoPowerup (int _x, int _y, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Item2);
        this.game = _game;
        this.camera = _game.getCamera();
        this.player = _game.getPlayer();
        this.parentContainer = _sch;
        StandardAnimatorController berserkAnimation = new StandardAnimatorController(this, INFINITE_AMMO_FRAMES, INF_AMMO_FPS);
        this.setAnimation(berserkAnimation);
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
        this.color = new StandardFadeController(Color.blue, Color.green, 0.05f);
    }

    @Override
    public void tick () {
        if (this.isAlive()) {
            
            this.getAnimationController().tick();
        }
        
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isAlive()) {
            
            this.getAnimationController().renderFrame(_g2);
        }
        else if (this.isActivated) {
            this.drawFlashingBorder(_g2);
            this.activateInfiniteAmmo();
        }
        
    }

    /**
     * Turns the timer on and instantiates the associated timer task.
     */
    @Override
    public void activate () {
        if (this.isActivated) {
            
            return;
        }

        /**
         * We need to instantiate a new timer in the event that the previous one
         * was canceled.
         */
        this.powerupTimer = new Timer(true);
        this.powerupTimer.schedule(new InfiniteAmmoTimer(this), timer);
        TimerController.addTimer(this);
        this.isActivated = true;
        this.playInfAmmoSFX();
    }

    @Override
    public void cancelTimer () {
        this.powerupTimer.cancel();
    }

    /**
     * Sets the player's ammo to their current magazine ammount, thus simulating
     * infinite ammo.
     */
    private void activateInfiniteAmmo () {
        Weapon curr = this.game.getPlayer().getInventory().getCurrentWeapon();
        
        if (!(curr instanceof Gun)) {
            
            return;
        }

        Gun gun = (Gun) curr;
        gun.setCurrentAmmo(gun.getMagazineCapacity());
    }

    /**
     * Plays the sound effect associated with collecting the infinite ammo item.
     */
    public void playInfAmmoSFX () {
        if (this.isCollected) {
            
            return;
        }

        StandardAudioController.play("src/resources/audio/sfx/pickup.wav", StandardAudioType.SFX);
    }

    /**
     * Draws the border around the screen while the powerup is active.
     *
     * @param _g2
     */
    private void drawFlashingBorder (Graphics2D _g2) {
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

    private Color getTransparentColor (Color _c) {
        return new Color(_c.getRed(), _c.getGreen(), _c.getBlue(), 127);
    }

    public void setCollected () {
        this.isCollected = true;
    }

    static {
        INFINITE_AMMO_FRAMES = Utilities.loadFrames("src/resources/img/items/drops/infammo/", 27);
    }

    /**
     * Private class for the infinite ammo. Once the player picks up the
     * powerup, the timer starts and continues until x milliseconds have passed,
     * then deactivates the powerup.
     */
    private class InfiniteAmmoTimer extends TimerTask {

        private final InfiniteAmmoPowerup powerup;

        public InfiniteAmmoTimer (InfiniteAmmoPowerup _powerup) {
            this.powerup = _powerup;
        }

        @Override
        public void run () {
            this.powerup.setAlive(false);
            this.powerup.isActivated = false;
        }
        
    }

//========================== GETTERS ============================================
    private Color getTransparentColor (Color _c) {
        
        return new Color(_c.getRed(), _c.getGreen(), _c.getBlue(), 127);
    }

//============================= SETTERS =========================================
    public void setCollected () {
        
        this.isCollected = true;
    }

    static {
        INFINITE_AMMO_FRAMES = Utilities.loadFrames("src/resources/img/items/drops/infammo/", 27);
    }

}
