package com.dsd.game.userinterface;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;

/**
 * This abstract class represents a "screen" [view] for the game. It has a
 * StandardInteractorHandler for UI elements such as labels, buttons, etc.
 *
 * The user can add new elements if desired.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public abstract class Screen implements Renderable, Updatable {

    //  Miscellaneous reference variables
    private static Game game;
    private final StandardInteractorHandler sih;

    //  Variables for getting quickly-modified screen dimensions.
    public static int gameFourthWidth;
    public static int gameFourthHeight;
    public static int gameHalfWidth;
    public static int gameHalfHeight;
    public static int gameDoubleWidth;
    public static int gameDoubleHeight;
    public static int gameWidth;
    public static int gameHeight;

    public Screen (Game _game) {
        Screen.game = _game;
        this.sih = new StandardInteractorHandler(Screen.game);
        this.addUIElementsAsListeners();
        Screen.setGameDimensions();
    }

    @Override
    public void tick () {
        StandardHandler.Handler(this.sih);
    }

    @Override
    public void render (Graphics2D _g2) {
        StandardDraw.Handler(this.sih);
    }

    /**
     * Adds a default interactor to the list of interactors.
     *
     * @param _interactor
     */
    public void addInteractor (Interactor _interactor) {
        this.sih.addInteractor(_interactor);
    }

    /**
     * Iterates through the list of UI elements and adds them as listeners to
     * the StandardGame.
     */
    private void addUIElementsAsListeners () {
        Screen.game.addMouseListener(this.sih);
        Screen.game.addMouseMotionListener(this.sih);
    }

    /**
     * Sets the game dimensions
     */
    public static void setGameDimensions () {
        gameWidth = game.getGameWidth();
        gameHeight = game.getGameHeight();
        gameHalfWidth = game.getGameWidth() >> 1;
        gameHalfHeight = game.getGameHeight() >> 1;
        gameDoubleWidth = game.getGameWidth() << 1;
        gameDoubleHeight = game.getGameHeight() << 1;
        gameFourthWidth = game.getGameWidth() >> 2;
        gameFourthHeight = game.getGameHeight() >> 2;
    }

//============================ GETTERS =================================//
    public Game getGame () {
        return Screen.game;
    }
}
