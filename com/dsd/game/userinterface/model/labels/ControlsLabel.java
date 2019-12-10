package com.dsd.game.userinterface.model.labels;

import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * This class represents the text shown on the screen when the user presses the
 * ESCAPE button (thus pausing the game).
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class ControlsLabel extends StandardLabel {

    //  Miscellaneous reference variable.
    private final Game game;

    //  Label x and y positioning offset.
    private final int LABEL_X_OFFSET = 40;
    private final int LABEL_Y_OFFSET = 100;
    private final int CONTROL_X_OFFSET = 130;
    private final int CONTROL_Y_OFFSET = 32;

    //  View element factors.
    private static final float FONT_SIZE = 32f;
    
    //  Amount of controls for the controls label.
    private static final String[] stringControls;
    private static final int CONTROLS = 6;

    public ControlsLabel(Game _game) {
        super((int) Screen.gameHalfWidth,
                (int) Screen.gameHalfHeight,
                LanguageController.translate("CONTROLS"),
                "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.game = _game;
    }

    @Override
    public void tick() {
        if (!this.game.isHelp()) {
            return;
        }
        this.setX((int) this.game.getCamera().getX() - this.LABEL_X_OFFSET);
        this.setY((int) this.game.getCamera().getY() - this.LABEL_Y_OFFSET);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.game.isHelp()) {
            return;
        }
        Color oldColor = _g2.getColor();
        Font oldFont = _g2.getFont();
        _g2.setColor(Color.WHITE);
        _g2.setFont(this.getFont());
        super.render(_g2);
        this.drawControlsString(_g2);
        _g2.setColor(oldColor);
        _g2.setFont(oldFont);
    }

    /**
     * Draws the controls for the screen.
     *
     * @param _g2
     */
    private void drawControlsString(Graphics2D _g2) {
        for (int i = 1; i <= ControlsLabel.CONTROLS; i++) {
            _g2.drawString(ControlsLabel.stringControls[i - 1],
                    (int) this.game.getCamera().getX() - this.CONTROL_X_OFFSET,
                    (int) this.game.getCamera().getY() + (i * this.CONTROL_Y_OFFSET));
        }
    }

    //  Temporary solution for displaying the controls?
    static {
        StringBuilder controlsString = new StringBuilder();
        controlsString.append("Move Forward: W").append(";");
        controlsString.append("Move Backward: S").append(";");
        controlsString.append("Change Weapon Forward: C").append(";");
        controlsString.append("Change Weapon Backward: X").append(";");
        controlsString.append("Use Weapon: SPACE").append(";");
        controlsString.append("Pause/Main Menu: ESC").append(";");
        stringControls = controlsString.toString().split(";");
    }
}
