package com.dsd.game.userinterface.model.labels;

import com.dsd.game.Game;
import com.dsd.game.controller.TimerController;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.TimerInterface;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will tell the user how long they've been playing, and what round
 * they're currently on.
 *
 * @author Joshua
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

    public TimeLabel (Game _game) {
        super(Screen.gameHalfWidth, TimeLabel.TIME_Y_OFFSET, "00:00:00",
                "src/resources/fonts/chargen.ttf", 14f);

        this.game = _game;
    }

    @Override
    public void tick () {
        if (!this.game.isInGameState()) {
            return;
        }
        else {
            if (this.timer == null) {
                this.timer = new Timer(true);
                this.timer.scheduleAtFixedRate(new TimerControl(this), 0, TIME_INTERVAL);
                TimerController.addTimer(this);
            }

            this.setText(String.format("%02d:%02d:%02d \u007C Wave %d", this.hours, this.minutes, this.seconds, this.game.getWaveNumber()));
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        this.setX((int) this.game.getCamera().getX());
        this.setY((int) (this.game.getCamera().getY() - Screen.gameHalfHeight + TIME_Y_OFFSET));

        _g2.setColor(Color.WHITE);
        super.render(_g2);
    }

    @Override
    public void cancelTimer () {
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        this.timer.cancel();
        this.timer = null;
    }

    /**
     * I'm sure there's a better way to do this with system calls, but this is
     * accurate enough for now.
     */
    private void calculateTime () {
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

        private final TimeLabel timeLabel;

        public TimerControl (TimeLabel _timeLabel) {
            this.timeLabel = _timeLabel;
        }

        @Override
        public void run () {
            this.timeLabel.calculateTime();
        }
    }
}