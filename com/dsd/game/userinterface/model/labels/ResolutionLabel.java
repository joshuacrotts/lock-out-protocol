package com.dsd.game.userinterface.model.labels;

import com.dsd.game.core.Game;
import com.dsd.game.objects.ResolutionEnum;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This label shows the current resolution/next/previous resolutions from within
 * the ResolutionView. When the user changes a resolution, it is updated on this
 * label.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class ResolutionLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;
    private static final float FONT_SIZE = 32f;

    public ResolutionLabel(Game _game, MenuScreen _menuScreen) {
        super(Screen.gameHalfWidth, Screen.gameHalfHeight,
                "", "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.setText(this.game.getGameWidth() + " x " + this.game.getGameHeight());
    }

    @Override
    public void tick() {
        this.setX(Screen.gameHalfWidth);
        this.setY(Screen.gameHalfHeight);
        this.setText(ResolutionEnum.getResolution());
    }

    @Override
    public void render(Graphics2D _g2) {
        _g2.setColor(Color.WHITE);
        super.render(_g2);
    }
}
