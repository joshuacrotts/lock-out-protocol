package com.dsd.game.userinterface.model.labels;

import com.dsd.game.Game;
import com.dsd.game.LanguageEnum;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * @author Joshua
 */
public class LanguageLabel extends StandardLabel {

    private final Game game;
    private final MenuScreen menuScreen;

    public LanguageLabel (Game _game, MenuScreen _menuScreen) {
        super(Screen.gameHalfWidth, Screen.gameHalfHeight,
                "", new Font(Font.SANS_SERIF, Font.PLAIN, 32));
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.setText(LanguageEnum.getLanguage());
    }

    @Override
    public void tick () {
        this.setX(Screen.gameHalfWidth);
        this.setY(Screen.gameHalfHeight);
        this.setText(LanguageEnum.getLanguage());
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.setColor(Color.WHITE);
        super.render(_g2);
    }
}
