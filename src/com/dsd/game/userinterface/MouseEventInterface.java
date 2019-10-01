package com.dsd.game.userinterface;

/**
 * This interface defines the events for interactors when the mouse is either
 * clicked on them, or the mouse is hovered over them.
 *
 * This "hover" and "click" "on them" is defined as the mouse existing within
 * the rectangular bounds of the interactor (set by the user).
 * 
 * [Group Name: Data Structure Deadheads]
 * @author Joshua, Ronald, Rinty 
 */
public interface MouseEventInterface {

    public void onMouseClick();

    public void onMouseEnterHover();

    public void onMouseExitHover();
}
