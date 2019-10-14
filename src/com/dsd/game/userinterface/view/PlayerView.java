package com.dsd.game.userinterface.view;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.model.buttons.StandardButton;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This is the view that will be shown when the user wants to change their
 * player gender.
 *
 * @author rinty
 */
public class PlayerView implements Renderable, Updatable {

    private final Game game;
    private final MenuScreen menuScreen;
    private final StandardButton parentButton;

    private final BufferedImage icon;
    private final String sex;

    private final int imageWidth;
    private final int imageHeight;

    public PlayerView (Game _game, MenuScreen _menuScreen, StandardButton _parentButton, String _sex) {
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.parentButton = _parentButton;
        this.icon = StdOps.loadImage("src/resources/img/player/player_icon/" + _sex + "_icon.png");
        this.sex = _sex;
        this.imageWidth = _parentButton.getWidth();
        this.imageHeight = _parentButton.getHeight();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }

        _g2.drawImage(this.icon, this.parentButton.getX(), this.parentButton.getY(), this.imageWidth, this.imageHeight, null);
    }

    @Override
    public void tick () {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }
    }
}
