package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from the main menu to
 * the resolution submenu.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class ResolutionMenuButton extends MenuButton implements MouseEventInterface {

    private static final int BUTTON_X_OFFSET = 0;
    private static final int BUTTON_Y_OFFSET = 680;
    private static final int TEXT_X_OFFSET = 25;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public ResolutionMenuButton (Game _game, MenuScreen _menuScreen) {
        super(ResolutionMenuButton.BUTTON_X_OFFSET, _game.getGameHeight() - ResolutionMenuButton.BUTTON_Y_OFFSET,
              ResolutionMenuButton.BUTTON_WIDTH, ResolutionMenuButton.BUTTON_HEIGHT, "CHANGE RESOLUTION", _game, _menuScreen);
    }

    @Override
    public void tick () {
        this.setX(ResolutionMenuButton.BUTTON_X_OFFSET);
        this.setY(this.getGame().getGameHeight() - ResolutionMenuButton.BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }

        super.render(_g2);
        StandardDraw.text(this.getText(), this.getX() + ResolutionMenuButton.TEXT_X_OFFSET,
                          this.getY() + ResolutionMenuButton.TEXT_Y_OFFSET, this.font, 24f, Color.WHITE);
    }

    @Override
    public void onMouseClick () {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }

        this.getMenuScreen().setMenuState(MenuState.RESOLUTION);
    }

    @Override
    public void onMouseEnterHover () {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover () {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
