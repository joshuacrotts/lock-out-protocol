package com.dsd.game.userinterface;

import com.dsd.game.userinterface.model.StandardButton;
import com.dsd.game.userinterface.model.PlayButton;
import com.dsd.game.Game;
import com.dsd.game.userinterface.model.ExitButton;
import com.dsd.game.userinterface.model.Interactor;
import com.dsd.game.userinterface.model.StandardLabel;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * MenuScreen controller. Decides what gets rendered when on the screen for when
 * the game is in the Menu state.
 *
 * @author Joshua
 */
public class MenuScreen implements Renderable, Updatable {

    private final Game sg;
    private final StandardInteractorHandler sih;

    public MenuScreen (Game _sg) {
        this.sg = _sg;
        this.sih = new StandardInteractorHandler();

        this.createUIElements();
        this.addUIElementsAsListeners();
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
     * Initializes the position of all the buttons for the user-interface when
     * the user is in the menu state.
     */
    private void createUIElements () {
        //  Instantiates the play button
        this.sih.addInteractor(new PlayButton(this.sg, 100, this.sg.getGameHeight() - 200, 200, 100, "PLAY", Color.RED));

        //  Instantiates the exit button
        this.sih.addInteractor(new ExitButton(this.sg, this.sg.getGameWidth() - 300, this.sg.getGameHeight() - 200, 200, 100, "EXIT", Color.RED));

        //  Instantiates the title label
        this.sih.addInteractor(new StandardLabel(this.sg.getGameWidth() / 2 - 100, 40, "CSC-340-Game", "src/res/fonts/chargen.ttf", 32f));
    }

    /**
     * Iterates through the list of UI elements and adds them as listeners to
     * the StandardGame.
     */
    private void addUIElementsAsListeners () {
        for (int i = 0 ; i < this.sih.getInteractors().size() ; i++) {
            Interactor inter = this.sih.getInteractors().get(i);

            if (inter instanceof StandardButton) {
                this.sg.addKeyListener((StandardButton) inter);
                this.sg.addMouseListener((StandardButton) inter);
                this.sg.addMouseMotionListener((StandardButton) inter);
            }
        }
    }
}
