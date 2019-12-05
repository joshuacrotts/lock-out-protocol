package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.core.GameState;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The HelpBackButton is a class designed to handle a return to the Paused Game
 * State, which consequently relates to the PauseScreen of the active game.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Ronald, Josh, Rinty
 *
 * @updated 11/21/19
 */
public class HelpBackButton extends MenuButton implements MouseEventInterface {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;

    //  Button offsets and dimensions + a centerer offset for X and Y.
    private static final int BUTTON_X_OFFSET = 155;
    private static final int BUTTON_X_CENTERER = 50;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int BUTTON_Y_CENTERER = 2;
    private static final int TEXT_X_OFFSET = 115;
    private static final int TEXT_Y_OFFSET = 45;

    public HelpBackButton(Game _game, MenuScreen _menuScreen) {
        super(BUTTON_X_OFFSET, BUTTON_Y_OFFSET,
                LanguageController.translate("BACK"), _game, _menuScreen);
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.setX((int) this.game.getCamera().getX() - (BUTTON_X_OFFSET - BUTTON_X_CENTERER));
        this.setY((int) this.game.getCamera().getY() + BUTTON_Y_OFFSET * BUTTON_Y_CENTERER);
        this.setScaled(true);
    }

    @Override
    public void tick() {
        if (!this.game.isHelp()) {
            return;
        }
        this.setX((int) this.game.getCamera().getX() - (BUTTON_X_OFFSET - BUTTON_X_CENTERER));
        this.setY((int) this.game.getCamera().getY() + BUTTON_Y_OFFSET * BUTTON_Y_CENTERER);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.game.isHelp()) {
            return;
        }
        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick() {
        if (!this.game.isHelp()) {
            return;
        }
        super.onMouseClick();
        this.getGame().setGameState(GameState.PAUSED);
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.game.isHelp()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover() {
        if (!this.game.isHelp()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
