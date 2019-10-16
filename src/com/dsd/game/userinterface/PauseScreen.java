package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.commands.PauseCommand;
import com.dsd.game.userinterface.model.labels.PauseLabel;
import com.dsd.game.userinterface.model.buttons.SaveButton;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Pause Screen displays a string "Paused" on the screen when the user presses
 * the escape key.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class PauseScreen extends Screen {

    private final PauseCommand pauseCommand;
    private final Color transparentBlack;

    public PauseScreen (Game _game) {
        super(_game);
        this.pauseCommand = new PauseCommand(_game);
        this.transparentBlack = new Color(0f, 0f, 0f, 0.5f);
        this.createUIElements();
    }

    @Override
    public void tick () {
        if (this.getGame().isMenu()) {
            return;
        }
        super.tick();

    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.getGame().isMenu()) {
            return;
        }
        this.drawTransparentScreen(_g2);
        super.render(_g2);
    }

    /**
     * Draws the black transparent screen as an overlay.
     *
     * @param _g2
     */
    private void drawTransparentScreen (Graphics2D _g2) {
        Color oldColor = _g2.getColor();
        _g2.setColor(this.transparentBlack);
        _g2.fillRect((int) (this.getGame().getCamera().getX() - Screen.gameHalfWidth),
                (int) (this.getGame().getCamera().getY() - Screen.gameHalfHeight),
                (int) (this.getGame().getCamera().getX() + Screen.gameDoubleWidth),
                (int) (this.getGame().getCamera().getY() + Screen.gameDoubleHeight));
        _g2.setColor(oldColor);
    }

    private void createUIElements () {
        this.initializePauseLabel();
        this.initializeSaveButton();
    }

    private void initializePauseLabel () {
        super.addInteractor(new PauseLabel(this.getGame()));
    }

    private void initializeSaveButton () {
        super.addInteractor(new SaveButton(this.getGame(), null));
    }
}
