package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.view.PlayerView;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from the main menu to
 * the player gender sub-menu.
 *
 * @author rinty
 */
public class MalePlayerButton extends StandardButton implements MouseEventInterface {

    private final Game game;
    private final MenuScreen menuScreen;
    private static PlayerView playerView;

    private static final int BUTTON_X_OFFSET = 680;
    private static final int BUTTON_Y_OFFSET = 480;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 200;

    public MalePlayerButton (Game _game, MenuScreen _menuScreen) {
        super(MalePlayerButton.BUTTON_X_OFFSET, _game.getGameHeight() - MalePlayerButton.BUTTON_Y_OFFSET,
                MalePlayerButton.BUTTON_WIDTH, MalePlayerButton.BUTTON_HEIGHT);
        this.game = _game;
        this.menuScreen = _menuScreen;
        MalePlayerButton.playerView = new PlayerView(this.game, this.menuScreen, this, "male");
    }

    @Override
    public void tick () {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }

        this.setX(MalePlayerButton.BUTTON_X_OFFSET);
        this.setY(game.getGameHeight() - MalePlayerButton.BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }

        MalePlayerButton.playerView.render(_g2);
    }

    @Override
    public void onMouseClick () {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }

        this.game.getPlayer().setPlayerSex("male");
        this.menuScreen.setMenuState(MenuState.DIFFICULTY);
    }

    @Override
    public void onMouseEnterHover () {
    }

    @Override
    public void onMouseExitHover () {
    }
}
