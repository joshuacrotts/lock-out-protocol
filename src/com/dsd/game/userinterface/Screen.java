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

    private final Game game;
    private final StandardInteractorHandler sih;

    public static int gameHalfWidth;
    public static int gameHalfHeight;
    public static int gameDoubleWidth;
    public static int gameDoubleHeight;

    public Screen(Game _game) {
        this.game = _game;
        this.sih = new StandardInteractorHandler(this.game);
        this.addUIElementsAsListeners();

        gameHalfWidth = this.game.getGameWidth() / 2;
        gameHalfHeight = this.game.getGameHeight() / 2;
        gameDoubleWidth = this.game.getGameWidth() * 2;
        gameDoubleHeight = this.game.getGameHeight() * 2;
    }

    @Override
    public void tick() {
        StandardHandler.Handler(this.sih);
    }

    @Override
    public void render(Graphics2D _g2) {
        StandardDraw.Handler(this.sih);
    }

    public void addInteractor(Interactor _interactor) {
        this.sih.addInteractor(_interactor);
    }

    /**
     * Iterates through the list of UI elements and adds them as listeners to
     * the StandardGame.
     */
    private void addUIElementsAsListeners() {
        this.game.addMouseListener(this.sih);
        this.game.addMouseMotionListener(this.sih);
    }

//============================ GETTERS =================================//
    public Game getGame() {
        return this.game;
    }
}
