/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.controller;

import java.util.ArrayList;
import java.util.Timer;

/**
 *
 * @author ljcrotts
 */
public class TimerController {

    private static final ArrayList<Timer> timers = new ArrayList<>();

    public static void addTimer (Timer _timer) {
        timers.add(_timer);
        System.out.println("Added a timer");
    }

    public static void stopTimers () {
        for (int i = 0 ; i < TimerController.timers.size() ; i++) {
            Timer currentTimer = timers.get(i);
            currentTimer.cancel();
        }
        System.out.println("Stopping all timers...");
    }
}
