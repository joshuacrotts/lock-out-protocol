package com.dsd.game.controller;

import com.dsd.game.util.StdConsole;
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
        StdConsole.println(StdConsole.YELLOW, "Added a timer");
    }

    public static void stopTimers () {
        for (int i = 0 ; i < TimerController.timers.size() ; i++) {
            Timer currentTimer = timers.get(i);

            currentTimer.cancel();
        }
        StdConsole.println(StdConsole.YELLOW, "Stopping all timers...");
    }

    public static void findTimer (Timer _timer) {
        for (Timer t : timers) {
            if (_timer == t) {
                StdConsole.println(StdConsole.GREEN, "Found your timer!");
                return;
            }
        }
        StdConsole.println(StdConsole.RED, "Did not find your timer.");
    }
}
