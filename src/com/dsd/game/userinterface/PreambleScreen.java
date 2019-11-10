package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.GameState;
import com.dsd.game.controller.TimerController;
import com.dsd.game.userinterface.model.LightningModel;
import com.dsd.game.userinterface.model.labels.WaveLabel;
import com.dsd.game.util.Utilities;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class, conveniently named "PreambleScreen", will draw a lightning effect
 * while showing what level/wave the player is on. Completely cosmetic.
 *
 * @author Joshua, Ronald, Rinty
 */
public class PreambleScreen extends Screen implements TimerInterface {

    // Models for this screen.
    private final LightningModel lightningEffect;
    private final WaveLabel waveModel;
    // Transparency effect of all models on the screen.
    private final float ALPHA_TIMER = 0.005f;
    private float alpha = 0f;
    /**
     * Timer that decides how long the text stays fully visible on the screen
     * before fading out.
     */
    private Timer preambleTimer;
    private final long PREAMBLE_TIMER_DURATION = 4000;
    // State that the menu is currently on (in terms of fading in/out).
    private PreambleScreenState state;

    public PreambleScreen (Game _game) {
        
        super(_game);
        this.lightningEffect = new LightningModel(_game);
        this.waveModel = new WaveLabel(_game, _game.getLogicalCurrentLevelID());
        this.preambleTimer = new Timer(true);
        TimerController.addTimer(this);
        this.state = PreambleScreenState.FADE_IN;
    }

    @Override
    public void tick () {
        
        if (this.getGame().isMenu()) {
            
            return;
        }
        
        this.lightningEffect.tick();
        this.waveModel.tick();
        this.changeAlpha();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.getGame().isMenu()) {
            return;
        }

        AlphaComposite oldComposite = (AlphaComposite) _g2.getComposite();
        _g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha));
        this.lightningEffect.render(_g2);
        this.waveModel.render(_g2);
        _g2.setComposite(oldComposite);
    }

    /**
     * If we are fading in, we increase the alpha transparency until it's maxed
     * out. Once we're there, we schedule a new preambletimer to fade the
     * transparency back out after *preambleTimerDuration* time.
     */
    private void changeAlpha () {
        
        /**
         * We can fade in if we are in the fade-in state, and our alpha is lower
         * than 1.
         */
        if (this.state == PreambleScreenState.FADE_IN && this.alpha < 1.0f) {
            
            this.alpha += ALPHA_TIMER;
        }
        
        /**
         * Once we hit an alpha of 1, we can schedule our timer to wait for x
         * seconds, then decrease the text/bolt effects.
         */
        else {
            
            this.preambleTimer.schedule(new PreambleTimer(this), PREAMBLE_TIMER_DURATION);
            
            if (this.state == PreambleScreenState.FADE_OUT && this.alpha > 0.0f) {
                
                this.alpha -= ALPHA_TIMER;
            }
            
            /**
             * Finally, once our alpha is below or equal to 0.0, we can flag the
             * game as running.
             */
            else if (this.alpha <= 0.0f) {
                
                this.getGame().setGameState(GameState.RUNNING);
                return;
            }
            
        }
        
        /**
         * We don't want our alpha to go above the 1f and below the 0f values,
         * so we can clamp it.
         */
        this.alpha = Utilities.clampFloat(this.alpha, 0f, 1f);
    }

    /**
     * Resets the alpha of the transparency, and begins to re-fade in the timer.
     */
    public void resetPreambleScreen () {
        
        this.alpha = 0;
        this.state = PreambleScreenState.FADE_IN;
        this.waveModel.setWaveNumber(this.getGame().getWaveNumber());
        this.preambleTimer = new Timer(true);
    }

    @Override
    public void cancelTimer () {
        
        this.preambleTimer.cancel();
    }

    /**
     * Timer class to keep the alpha transparency the same until we begin to
     * fade back out.
     */
    private class PreambleTimer extends TimerTask {

        private final PreambleScreen screen;

        public PreambleTimer (PreambleScreen _screen) {
            
            this.screen = _screen;
        }

        @Override
        public void run () {
            
            screen.state = PreambleScreenState.FADE_OUT;
        }
        
    }

    //  Enum for specifying the state of the preamble screen.
    private enum PreambleScreenState {
        
        FADE_IN, FADE_OUT, STAGNANT;
    }
    
}
