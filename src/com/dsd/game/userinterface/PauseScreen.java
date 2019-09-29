package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.commands.PauseCommand;
import com.dsd.game.userinterface.model.PauseLabel;
import java.awt.Graphics2D;

/**
 * Pause Screen displays a string "Paused" on the screen when the user presses
 * the escape key.
 *
 * @author Joshua
 */
public class PauseScreen extends Screen {

    private final PauseLabel pauseLabel;
    private final PauseCommand pauseCommand;


    public PauseScreen (Game _sg) {
        super(_sg);

        this.pauseLabel = new PauseLabel(_sg);
        this.pauseCommand = new PauseCommand(_sg);

        this.addInteractor(this.pauseLabel);
    }

    @Override
    public void tick () {
        this.pauseLabel.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        this.pauseLabel.render(_g2);
    }

}
