package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from the main menu to
 * the resolution submenu.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/14/19
 */
public class ResolutionMenuButton extends MenuButton implements MouseEventInterface {

    //  Button position and dimension offsets.
    private static final int BUTTON_X_OFFSET = 141;
    private static final int BUTTON_Y_OFFSET = 580;
    private static final int TEXT_X_OFFSET = 25;
    private static final int TEXT_Y_OFFSET = 45;

    public ResolutionMenuButton(Game _game, MenuScreen _menuScreen) {
        super(ResolutionMenuButton.BUTTON_X_OFFSET, _game.getGameHeight() - ResolutionMenuButton.BUTTON_Y_OFFSET,
                LanguageController.translate("CHANGE RESOLUTION"),
                _game, _menuScreen);
    }

    @Override
    public void tick() {
        this.setX(Screen.gameHalfWidth - ResolutionMenuButton.BUTTON_X_OFFSET);
        this.setY(this.getGame().getGameHeight() - ResolutionMenuButton.BUTTON_Y_OFFSET);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick() {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        super.onMouseClick();
        this.getMenuScreen().pushMenuStack(MenuState.OPTIONS);
        this.getMenuScreen().setMenuState(MenuState.RESOLUTION);
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover() {
        if (!this.getMenuScreen().isOnOptions() || !this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
