package com.dsd.game.userinterface.model.labels;

import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.controller.TimerController;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.TimerInterface;
import com.dsd.game.util.Utilities;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will tell the user how long they've been playing, and what round
 * they're currently on.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/26/19
 */
public class TimeLabel extends StandardLabel implements TimerInterface {

    //  Miscellaneous reference variables.
    private final Game game;
    private Timer timer;

    //  Time variables.
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    //  Time information; its position and how often it should increment.
    private static final int TIME_Y_OFFSET = 15;
    private static final int TIME_INTERVAL = 1000;
    private final String waveString;

    public TimeLabel(Game _game) {
        super(Screen.gameHalfWidth, TimeLabel.TIME_Y_OFFSET, "00:00:00",
                "src/resources/fonts/chargen.ttf", 14f);
        this.game = _game;
        this.waveString = LanguageController.translate("Wave");
    }

    @Override
    public void tick() {
        if (!this.game.isInGameState()) {
            return;
        } else {
            if (this.timer == null) {
                this.timer = new Timer(true);
                this.timer.scheduleAtFixedRate(new TimerControl(this.game, this), 0, TIME_INTERVAL);
                TimerController.addTimer(this);
            }
            this.setText(String.format("%02d:%02d:%02d \u007C %s %s", this.hours, this.minutes, this.seconds, this.waveString,
                    Utilities.toRoman(this.game.getWaveNumber())));
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        this.setX((int) this.game.getCamera().getX());
        this.setY((int) (this.game.getCamera().getY() - Screen.gameHalfHeight + TIME_Y_OFFSET));
        _g2.setColor(Color.WHITE);
        super.render(_g2);
    }

    @Override
    public void cancelTimer() {
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        if (timer != null) {
            this.timer.cancel();
        }
        this.timer = null;
    }

    /**
     * I'm sure there's a better way to do this with system calls, but this is
     * accurate enough for now.
     */
    private void calculateTime() {
        this.seconds++;
        if (this.seconds % 60 == 0) {
            this.minutes++;
            this.seconds = 0;
        }
        if (this.minutes % 60 == 0 && this.minutes != 0) {
            this.hours++;
            this.minutes = 0;
        }
    }

    private class TimerControl extends TimerTask {

        private final Game game;
        private final TimeLabel timeLabel;

        public TimerControl(Game _game, TimeLabel _timeLabel) {
            this.game = _game;
            this.timeLabel = _timeLabel;
        }

        @Override
        public void run() {
            if (this.game.isPaused() || this.game.isShop()) {
                return;
            }
            this.timeLabel.calculateTime();
        }
    }
}
