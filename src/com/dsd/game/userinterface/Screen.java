package com.dsd.game.userinterface;

import com.dsd.game.Game;
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
 * @author Joshua
 */
public abstract class Screen implements Renderable, Updatable {

    private final Game sg;
    private final StandardInteractorHandler sih;

    protected static int GAME_HALF_WIDTH;
    protected static int GAME_HALF_HEIGHT;
    protected static int GAME_DOUBLE_WIDTH;
    protected static int GAME_DOUBLE_HEIGHT;

    public Screen (Game _sg) {
        this.sg = _sg;
        this.sih = new StandardInteractorHandler(this.sg);
        this.addUIElementsAsListeners();

        GAME_HALF_WIDTH = this.sg.getGameWidth() / 2;
        GAME_HALF_HEIGHT = this.sg.getGameHeight() / 2;
        GAME_DOUBLE_WIDTH = this.sg.getGameWidth() * 2;
        GAME_DOUBLE_HEIGHT = this.sg.getGameHeight() * 2;
    }

    @Override
    public void tick () {
        StandardHandler.Handler(this.sih);
    }

    @Override
    public void render (Graphics2D _g2) {
        StandardDraw.Handler(this.sih);
    }

    public void addInteractor (Interactor _interactor) {
        this.sih.addInteractor(_interactor);
    }

    /**
     * Iterates through the list of UI elements and adds them as listeners to
     * the StandardGame.
     */
    private void addUIElementsAsListeners () {
        this.sg.addMouseListener(this.sih);
        this.sg.addMouseMotionListener(this.sih);
    }

//============================ GETTERS =================================//
    public Game getGame () {
        return this.sg;
    }
}
