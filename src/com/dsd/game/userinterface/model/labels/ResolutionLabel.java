package com.dsd.game.userinterface.model.labels;

import com.dsd.game.Game;
import com.dsd.game.ResolutionEnum;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author Joshua
 */
public class ResolutionLabel extends StandardLabel {

    private final Game game;
    private final MenuScreen menuScreen;
    private static final int BUTTON_X_OFFSET = 0;
    private static final int BUUTTON_Y_OFFSET = 0;
    private static final int TITLE_X_OFFSET = 140;
    private static final int TITLE_Y_OFFSET = 40;

    public ResolutionLabel (Game _game, MenuScreen _menuScreen) {
        super(Screen.gameHalfWidth, Screen.gameHalfHeight,
                "", "src/resources/fonts/chargen.ttf", 32f);
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.setText(this.game.getGameWidth() + " x " + this.game.getGameHeight());
    }

    @Override
    public void tick () {
        this.setX(Screen.gameHalfWidth);
        this.setY(Screen.gameHalfHeight);
        this.setText(ResolutionEnum.getResolution());
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.setColor(Color.WHITE);
        super.render(_g2);
    }
}
