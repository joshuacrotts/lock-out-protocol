package com.dsd.game.userinterface;

/**
 * This class will be implemented by any class that has a timer object. When the
 * game iterates through the timers to cancel them all, this method will be
 * called to do whatever that particular object needs to do when shutting down
 * the timer.
 *
 * @author Joshua
 */
public interface TimerInterface {

    public void cancelTimer ();
}
