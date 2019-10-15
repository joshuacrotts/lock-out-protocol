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
public class FemalePlayerButton extends StandardButton implements MouseEventInterface {

    private final Game game;
    private final MenuScreen menuScreen;
    private static PlayerView playerView;

    private static final int BUTTON_WIDTH = 500;
    private static final int BUTTON_HEIGHT = 500;
    private static final int BUTTON_X_OFFSET = 85;
    private static final int BUTTON_Y_OFFSET = 650;

    public FemalePlayerButton (Game _game, MenuScreen _menuScreen) {
        super(FemalePlayerButton.BUTTON_X_OFFSET, _game.getGameHeight() - FemalePlayerButton.BUTTON_Y_OFFSET,
                FemalePlayerButton.BUTTON_WIDTH, FemalePlayerButton.BUTTON_HEIGHT);
        this.game = _game;
        this.menuScreen = _menuScreen;
        FemalePlayerButton.playerView = new PlayerView(this.game, this.menuScreen, this, "female");
    }

    @Override
    public void tick () {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }

        this.setX(FemalePlayerButton.BUTTON_X_OFFSET);
        this.setY(this.game.getGameHeight() - FemalePlayerButton.BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }
        FemalePlayerButton.playerView.render(_g2);
    }

    @Override
    public void onMouseClick () {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }
        this.game.getPlayer().setPlayerSex("female");
        this.menuScreen.setMenuState(MenuState.DIFFICULTY);
    }

    @Override
    public void onMouseEnterHover () {
        FemalePlayerButton.playerView.setMouseOver(true);
    }

    @Override
    public void onMouseExitHover () {
        FemalePlayerButton.playerView.setMouseOver(false);
    }
}
