package com.dsd.game.userinterface;

import com.dsd.game.userinterface.model.PlayButton;
import com.dsd.game.Game;
import com.dsd.game.userinterface.model.ExitButton;
import com.dsd.game.userinterface.model.StandardLabel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * MenuScreen controller. Decides what gets rendered when on the screen for when
 * the game is in the Menu state.
 *
 * @author Joshua
 */
public class MenuScreen extends Screen{

    public MenuScreen(Game _sg) {
        super(_sg);

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
     */
    private void createUIElements() {
        //  Instantiates the play button
        super.addInteractor(new PlayButton(this.getGame(), 100, this.getGame().getGameHeight() - 200, 200, 100, "PLAY", Color.RED));

        //  Instantiates the exit button
        super.addInteractor(new ExitButton(this.getGame(), this.getGame().getGameWidth() - 300, this.getGame().getGameHeight() - 200, 200, 100, "EXIT", Color.RED));

        //  Instantiates the title label
        super.addInteractor(new StandardLabel(this.getGame().getGameWidth() / 2 - 150, 40, this.getGame().getWindow().getTitle(), "src/res/fonts/chargen.ttf", 32f));
    }
}
