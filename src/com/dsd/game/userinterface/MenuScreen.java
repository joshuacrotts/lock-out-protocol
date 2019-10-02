package com.dsd.game.userinterface;

import com.dsd.game.userinterface.model.PlayButton;
import com.dsd.game.Game;
import com.dsd.game.userinterface.model.ExitButton;
import com.dsd.game.userinterface.model.StandardLabel;
import java.awt.Graphics2D;

/**
 * MenuScreen controller. Decides what gets rendered when on the screen for when
 * the game is in the Menu state.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class MenuScreen extends Screen {

    public MenuScreen(Game _game) {
        super(_game);

        this.createUIElements();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }

    /**
     * Initializes the position of all the buttons for the user-interface when
     * the user is in the menu state.
     *
     * @TODO: Somehow define these without using magic numbers.
     */
    private void createUIElements() {
        //  Instantiates the play button
        super.addInteractor(new PlayButton(this.getGame()));

        //  Instantiates the exit button
        super.addInteractor(new ExitButton(this.getGame()));

        //  Instantiates the title label
        super.addInteractor(new StandardLabel(this.getGame().getGameWidth() / 2 - 150, 40, this.getGame().getWindow().getTitle(), "src/res/fonts/chargen.ttf", 32f));
    }
}
