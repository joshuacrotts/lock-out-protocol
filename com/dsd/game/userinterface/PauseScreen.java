package com.dsd.game.userinterface;

import com.dsd.game.core.Game;
import com.dsd.game.commands.PauseCommand;
import com.dsd.game.userinterface.model.buttons.HelpButton;
import com.dsd.game.userinterface.model.buttons.MainMenuButton;
import com.dsd.game.userinterface.model.buttons.SaveButton;
import com.dsd.game.userinterface.model.labels.PauseLabel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Pause Screen displays a string "Paused" on the screen when the user presses
 * the escape key.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class PauseScreen extends Screen {

    private final PauseCommand pauseCommand;
    private final Color transparentBlack = new Color(0f, 0f, 0f, 0.5f);

    public PauseScreen(Game _game) {
        super(_game);
        this.pauseCommand = new PauseCommand(_game);
        this.createUIElements();
    }

    @Override
    public void tick() {
        if (this.getGame().isMenu()) {
            return;
        }
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
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
    private void drawTransparentScreen(Graphics2D _g2) {
        Color oldColor = _g2.getColor();
        _g2.setColor(this.transparentBlack);
        _g2.fillRect((int) (this.getGame().getCamera().getX() - Screen.gameHalfWidth),
                (int) (this.getGame().getCamera().getY() - Screen.gameHalfHeight),
                (int) (this.getGame().getCamera().getX() + Screen.gameDoubleWidth),
                (int) (this.getGame().getCamera().getY() + Screen.gameDoubleHeight));
        _g2.setColor(oldColor);
    }

    private void createUIElements() {
        super.addInteractor(new PauseLabel(this.getGame()));
        super.addInteractor(new SaveButton(this.getGame(), null));
        super.addInteractor(new HelpButton(this.getGame(), null));
        super.addInteractor(new MainMenuButton(this.getGame(), null));
    }

}
