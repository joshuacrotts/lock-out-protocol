package com.dsd.game.userinterface.shop;

import com.dsd.game.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.model.labels.*;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Title label for the shop.
 *
 * @author Joshua
 */
public class ShopTitleLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final Game game;
    private final ShopScreen shopScreen;

    private static final int TITLE_X_OFFSET = 30;
    private static final int TITLE_Y_OFFSET = 280;

    public ShopTitleLabel (Game _game, ShopScreen _shopScreen) {
        super(Screen.gameHalfWidth - ShopTitleLabel.TITLE_X_OFFSET,
                ShopTitleLabel.TITLE_Y_OFFSET, LanguageController.translate("SHOP"),
                "src/resources/fonts/chargen.ttf", 64f);

        this.game = _game;
        this.shopScreen = _shopScreen;
        this.setScaled(true);
    }

    @Override
    public void tick () {
        this.setX((int) this.game.getCamera().getX() - TITLE_X_OFFSET);
        this.setY((int) this.game.getCamera().getY() - (int) (Screen.gameFourthHeight * 1.575));
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.setColor(Color.WHITE);
        super.render(_g2);
    }
}
