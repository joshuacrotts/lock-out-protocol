package com.dsd.game.userinterface;

/**
 * This interface defines the events for interactors when the mouse is either
 * clicked on them, or the mouse is hovered over them.
 *
 * This "hover" and "click" "on them" is defined as the mouse existing within
 * the rectangular bounds of the interactor (set by the user).
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public interface MouseEventInterface {

    /**
     * When the user's mouse is overtop of the bounds set by the interactor, and
     * the user clicks their mouse, this method is called.
     *
     * It uses the Java Swing MouseListener interface to listen for clicks.
     */
    public void onMouseClick();

    /**
     * When the user's mouse is overtop of the bounds set by the interactor,
     * this method is called.
     */
    public void onMouseEnterHover();

    /**
     * When the user's mouse leaves the bounds set by the interactor, this
     * method is called.
     */
    public void onMouseExitHover();
}
