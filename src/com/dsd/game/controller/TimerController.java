package com.dsd.game.controller;

import com.dsd.game.userinterface.TimerInterface;
import java.util.ArrayList;

/**
 * This class controls all TimerInterface objects in the game. When the game is
 * reset, they need to be halted/canceled.
 *
 * @author Joshua, Ronald, Rinty
 */
public class TimerController {

    private static final ArrayList<TimerInterface> timers = new ArrayList<>();

    public static void addTimer (TimerInterface _timer) {

        timers.add(_timer);
    }

    public static void stopTimers () {

        for (int i = 0 ; i < TimerController.timers.size() ; i++) {

            TimerInterface currentTimer = timers.get(i);

            if (currentTimer != null) {

                currentTimer.cancelTimer();
            }

        }

    }

    public static void findTimer (TimerInterface _timer) {

        for (TimerInterface t : timers) {

            if (_timer == t) {

                return;
            }

        }

    }

}
