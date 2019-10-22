package com.dsd.game.userinterface.shop.weapons;

import com.dsd.game.Game;
import com.dsd.game.userinterface.ShopScreen;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 *
 * @author Joshua
 */
public class RifleButton extends ShopButton {

    private static final int X_OFFSET = 50;
    private static final int Y_OFFSET = 176;

    public RifleButton (Game _game, ShopScreen _shopScreen) {
        super(_game, _shopScreen, X_OFFSET, Y_OFFSET);
        super.setImage(StdOps.loadImage("src/resources/img/ui/shop_icons/rifle_icon.png"));
    }

    @Override
    public void tick () {
        this.setX((int) this.getGame().getCamera().getX() - X_OFFSET);
        this.setY((int) this.getGame().getCamera().getY() - Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
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
