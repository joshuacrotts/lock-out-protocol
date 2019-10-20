package com.dsd.game.userinterface.view;

import com.dsd.game.Game;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joshua
 */
public class ShopView extends Interactor {

    private static BufferedImage backingImage;
    private final Game game;
    private final StandardCamera camera;

    public ShopView (Game _game) {
        this.game = _game;
        this.camera = _game.getCamera();
        ShopView.backingImage = StdOps.loadImage("src/resources/img/ui/shop.png");
        this.setWidth(this.game.getGameWidth());
        this.setHeight(this.game.getGameHeight());
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

    @Override
    public void render (Graphics2D _g2) {
        _g2.drawImage(backingImage, (int) this.camera.getX() - Screen.gameHalfWidth, (int) this.camera.getY() - Screen.gameHalfHeight, this.getWidth(), this.getHeight(), null);

    }

    @Override
    public void tick () {
    }
}
