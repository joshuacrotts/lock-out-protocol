package com.dsd.game.objects.powerups;

/**
 * This class simply groups all Powerups together, and allows the collision
 * handler to call the activate() method when the user walks over a powerup.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/12/19
 */
public interface Powerup {

    /**
     * Each class that implements Powerup will override this method, detailing
     * how the powerup should affect the player/game.
     */
    public void activate ();
}
