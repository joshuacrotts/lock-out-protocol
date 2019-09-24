package com.dsd.game.userinterface;

/**
 * This interface defines the events for interactors when the mouse is either
 * clicked on them, or the mouse is hovered over them.
 *
 * This "hover" and "click" "on them" is defined as the mouse existing within
 * the rectangular bounds of the interactor (set by the user).
 */
public interface MouseEventInterface {

    public void onMouseClick();

    public void onMouseHover();
}
