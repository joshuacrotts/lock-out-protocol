package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from Menu to the account
 * information screen when selected.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/14/19
 */
public class BackButton extends MenuButton implements MouseEventInterface {

    //  Button position and dimension offsets.
    private static final int BUTTON_X_OFFSET = 155;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int TEXT_X_OFFSET = 115;
    private static final int TEXT_Y_OFFSET = 45;

    public BackButton(Game _game, MenuScreen _menuScreen) {
        super(_game.getGameWidth() - BUTTON_X_OFFSET - BUTTON_WIDTH / 2,
                _game.getGameHeight() - BUTTON_Y_OFFSET,
                LanguageController.translate("BACK"), _game, _menuScreen);
    }

    public BackButton(int _x, int _y, Game _game, MenuScreen _menuScreen) {
        super(_x, _y, LanguageController.translate("BACK"), _game, _menuScreen);
    }

    @Override
    public void tick() {
        this.setX(this.getGame().getGameWidth() - BUTTON_X_OFFSET - BUTTON_WIDTH / 2);
        this.setY(this.getGame().getGameHeight() - BUTTON_Y_OFFSET);
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick() {
        if (!this.getGame().isMenu() || this.getMenuScreen().isOnMainMenu()) {
            return;
        }
        super.onMouseClick();
        this.getMenuScreen().setMenuState(this.getMenuScreen().popMenuStack());
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover() {
        if (!this.getGame().isMenu()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }
}
