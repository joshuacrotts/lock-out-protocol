package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MenuState;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.view.PlayerView;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudioType;
import java.awt.Graphics2D;

/**
 * Subclass of StandardButton - switches the game state from the main menu to
 * the player gender sub-menu.
 *
 * @author rinty
 */
public class MalePlayerButton extends StandardButton implements MouseEventInterface {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;
    private static PlayerView playerView;

    //  Male Player Button dimensions and position.
    private static final int BUTTON_WIDTH = 350;
    private static final int BUTTON_HEIGHT = 350;
    private static final int BUTTON_X_OFFSET = 85;
    private static final int BUTTON_Y_OFFSET = 550;

    public MalePlayerButton (Game _game, MenuScreen _menuScreen) {
        super(Screen.gameWidth - BUTTON_WIDTH - BUTTON_X_OFFSET, _game.getGameHeight() - MalePlayerButton.BUTTON_Y_OFFSET,
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

        this.setX(Screen.gameWidth - BUTTON_WIDTH - BUTTON_X_OFFSET);
        this.setY(game.getGameHeight() - MalePlayerButton.BUTTON_Y_OFFSET);

        MalePlayerButton.playerView.tick();
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
        
        StandardAudioController.play("src/resources/audio/sfx/menuselect.wav", StandardAudioType.SFX);
        this.game.getPlayer().setPlayerSex("male");
        this.menuScreen.pushMenuStack(MenuState.PLAYER_GENDER);
        this.menuScreen.setMenuState(MenuState.DIFFICULTY);
    }

    @Override
    public void onMouseEnterHover () {
        MalePlayerButton.playerView.setMouseOver(true);
    }

    @Override
    public void onMouseExitHover () {
        MalePlayerButton.playerView.setMouseOver(false);
    }
}
