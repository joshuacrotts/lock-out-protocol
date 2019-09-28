package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.commands.PauseCommand;
import com.dsd.game.userinterface.model.StandardLabel;
import com.revivedstandards.main.StandardCamera;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Pause Screen displays a string "Paused" on the screen when the user presses
 * the escape key.
 *
 * @author Joshua
 */
public class PauseScreen extends Screen {

    private final StandardLabel pauseLabel;
    private final PauseCommand pauseCommand;

    //
    //  The camera is needed so we know where to draw the paused text.
    //
    private final StandardCamera camera;
    private final Color transparentBlack;

    public PauseScreen (Game _sg) {
        super(_sg);

        this.pauseLabel = new StandardLabel(Screen.GAME_HALF_WIDTH, Screen.GAME_HALF_HEIGHT,
                "Paused", "src/res/fonts/chargen.ttf", 20f);
        this.pauseCommand = new PauseCommand(_sg);

        this.transparentBlack = new Color(0f, 0f, 0f, 0.5f);

        this.camera = _sg.getCamera();

        this.addInteractor(this.pauseLabel);
    }

    @Override
    public void tick () {
        this.pauseLabel.setX((int) this.camera.getX());
        this.pauseLabel.setY((int) this.camera.getY());
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.setColor(this.transparentBlack);
        _g2.fillRect((int) (this.camera.getX() - Screen.GAME_HALF_WIDTH),
                (int) (this.camera.getY() - Screen.GAME_HALF_HEIGHT),
                (int) (this.camera.getX() + Screen.GAME_DOUBLE_WIDTH),
                (int) (this.camera.getY() + Screen.GAME_DOUBLE_HEIGHT));

        super.render(_g2);
    }

}
