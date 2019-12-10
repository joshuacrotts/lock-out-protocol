package com.dsd.game.controller;

import com.dsd.game.userinterface.TimerInterface;
import java.util.ArrayList;

/**
 * This class controls all TimerInterface objects in the game. When the game is
 * reset, they need to be halted/canceled.
 * 
 * [Group Name: Data Structure Deadheads]
 * 
 * @author Joshua
 * 
 * @updated 12/10/19
 */
public class TimerController {

    private static final ArrayList<TimerInterface> timers = new ArrayList<>();

    /**
     * Adds a timer to the ArrayList of timers.
     *
     * @param _timer
     */
    public static void addTimer(TimerInterface _timer) {
        timers.add(_timer);
    }

    // Stops all timers that are in the timer controller.
    public static void stopTimers() {
        for (int i = 0; i < TimerController.timers.size(); i++) {
            TimerInterface currentTimer = timers.get(i);
            if (currentTimer != null) {
                currentTimer.cancelTimer();
            }
        }
    }

}
