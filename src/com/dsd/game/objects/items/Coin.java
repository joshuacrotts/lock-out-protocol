package com.dsd.game.objects.items;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.powerups.Powerup;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class represents the coin object; there are three values a coin can
 * take; small, medium, and large. The three parameters passed in the
 * constructor (the last three) designate the rarity of these.
 *
 * @author Joshua, Ronald, Rinty
 */
public class Coin extends StandardGameObject implements Powerup {

    //  Handler for the coins
    private final StandardCollisionHandler parentContainer;
    private final Player player;

    //  Frames of animation for the coins
    private static final BufferedImage[] coinOneFrames;
    private static final BufferedImage[] coinTwoFrames;
    //  Randomness for the scatter of the coin
    //  This the value at which the coins can scatter
    private final double SCATTER_RANGE = 0.99;
    //  Variables for changing the speed of the coins as they disperse
    private static final double VEL_LOWER_BOUND = 0.5;
    private static final double VEL_UPPER_BOUND = 1.5;
    private final int COIN_FPS = 5;
    private int value = 0;
    //  Max number of sound effects for coins.
    private static final int MAX_COIN_SFX = 1;

    /**
     * The _small, _medium, and _large parameters should be sequential, and go
     * up to 1.0. Essentially, _small = 0.7, _mid = 0.9, _large = 1.0 can be
     * your coin rarity, in that 70% of the time, a small coin will drop. 20% of
     * the time, a medium coin will drop (0.9-0.7). 10% for the large.
     *
     * @param _x
     * @param _y
     * @param _small
     * @param _medium
     * @param _large
     * @param _sch
     */
    public Coin(Game _game, int _x, int _y, double _small, double _medium, double _large, StandardCollisionHandler _sch) {
        super(_x, _y, StandardID.Coin);
        this.parentContainer = _sch;
        this.player = _game.getPlayer();
        this.generateCoinType(_small, _medium, _large);
        this.setVelX(StdOps.randBounds(-Coin.VEL_UPPER_BOUND, -Coin.VEL_LOWER_BOUND,
                Coin.VEL_LOWER_BOUND, Coin.VEL_UPPER_BOUND));
        this.setVelY(StdOps.randBounds(-Coin.VEL_UPPER_BOUND, -Coin.VEL_LOWER_BOUND,
                Coin.VEL_LOWER_BOUND, Coin.VEL_UPPER_BOUND));
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            
            this.getAnimationController().tick();
            this.slowVelocities();
            this.updatePosition();
        } else {
            this.parentContainer.removeEntity(this);
        }
        
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.isAlive()) {
            
            this.getAnimationController().renderFrame(_g2);
        }
        
    }

    @Override
    public void activate() {
        this.player.setMoney(this.player.getMoney() + this.getValue());
        this.playCoinSFX();
    }

    /**
     * Plays a random coin collection sfx.
     */
    private void playCoinSFX() {
        StandardAudioController.play("src/resources/audio/sfx/coin0.wav", StandardAudioType.SFX);
    }

    /**
     * Slows the velocity of the coins gradually.
     */
    private void slowVelocities() {
        this.setVelX(this.getVelX() * this.SCATTER_RANGE);
        this.setVelY(this.getVelY() * this.SCATTER_RANGE);
    }

    /**
     * Generates a coin type depending on the probability of the coins.
     *
     * @param _small
     * @param _medium
     * @param _large
     */
    private void generateCoinType(double _small, double _medium, double _large) {
        int coin = StdOps.rand(0, 100);
        
        if (coin < _small * 100) {
            
            this.setAnimation(new StandardAnimatorController(this, Coin.coinOneFrames, this.COIN_FPS));
            this.value = 1;
        } else {
            this.setAnimation(new StandardAnimatorController(this, Coin.coinTwoFrames, this.COIN_FPS));
            this.value = 5;
        }
        
    }

//================================= GETTERS ==================================//
    public int getValue() {
        return this.value;
    }

    //static value
    static {
        
        coinOneFrames = Utilities.loadFrames("src/resources/img/items/coin/small", 4);
        coinTwoFrames = Utilities.loadFrames("src/resources/img/items/coin/medium", 4);
    }
    
}
