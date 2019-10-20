package com.dsd.game.controller;

import java.util.ArrayList;
import java.util.Timer;

/**
 * This class controls all timer objects in the game. When the game is reset,
 * they need to be halted/canceled.
 *
 * @author Joshua
 */
public class TimerController {

    private static final ArrayList<Timer> timers = new ArrayList<>();

    public static void addTimer (Timer _timer) {
        timers.add(_timer);
    }

    public static void stopTimers () {
        for (int i = 0 ; i < TimerController.timers.size() ; i++) {
            Timer currentTimer = timers.get(i);

            currentTimer.cancel();
        }
    }

    public static void findTimer (Timer _timer) {
        for (Timer t : timers) {
            if (_timer == t) {
                return;
            }
        }
    }
}
