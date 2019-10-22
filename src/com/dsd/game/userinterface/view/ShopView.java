package com.dsd.game.userinterface.view;

import com.dsd.game.Game;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.main.StandardCamera;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Shop background view.
 *
 * @author Joshua
 */
public class ShopView extends Interactor {

    //  Miscellaneous reference variables.
    private final Game game;
    private final StandardCamera camera;

    //  The background for the corresponding shop view.
    private final Color shopRectColor;
    private Rectangle shopRectangle;

    public ShopView (Game _game) {
        this.game = _game;
        this.camera = _game.getCamera();

        this.shopRectColor = new Color(0, 0, 0, 0.8f);
        this.shopRectangle = new Rectangle(0, 0, this.game.getGameWidth(), this.game.getGameHeight());

        this.setWidth(this.game.getGameWidth());
        this.setHeight(this.game.getGameHeight());

        this.setScaled(true);
    }


    @Override
    public void tick () {
        this.setWidth(this.game.getGameWidth());
        this.setHeight(this.game.getGameHeight());

        this.shopRectangle = new Rectangle((int) this.camera.getX() - Screen.gameHalfWidth,
                                           (int) this.camera.getY() - Screen.gameHalfHeight,
                                           this.getWidth(), this.getHeight());
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.setColor(this.shopRectColor);
        _g2.fill(this.shopRectangle);
    }

    @Override
    public void onMouseClick () {
    }

    @Override
    public void onMouseEnterHover () {
    }

    @Override
    public void onMouseExitHover () {
    }
}
