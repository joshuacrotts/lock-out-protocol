package com.dsd.game.userinterface.model.labels;

import com.dsd.game.core.Game;
import com.dsd.game.core.LanguageEnum;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * This class shows what language the user is currently selecting/has selected.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class LanguageLabel extends StandardLabel {

    //  Miscellaneous game and menu screen references.
    private final Game game;
    private final MenuScreen menuScreen;

    //  View element factors.
    private static final int FONT_SIZE = 32;

    public LanguageLabel(Game _game, MenuScreen _menuScreen) {
        super(Screen.gameHalfWidth, Screen.gameHalfHeight,
                "", new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE));
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.setText(LanguageEnum.getLanguage());
    }

    @Override
    public void tick() {
        this.setX(Screen.gameHalfWidth);
        this.setY(Screen.gameHalfHeight);
        this.setText(LanguageEnum.getLanguage());
    }

    @Override
    public void render(Graphics2D _g2) {
        _g2.setColor(Color.WHITE);
        super.render(_g2);
    }
}
