package com.dsd.game.userinterface.shop.weapons.models;

import com.dsd.game.Game;
import com.dsd.game.objects.Inventory;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class is an abstract superclass of all shop buttons.
 *
 * @author Joshua
 */
public abstract class ShopButton extends Interactor {

    //  Miscellaneous reference buttons.
    private final Game game;
    private final Inventory playerInventory;
    private final ShopScreen shopScreen;

    //  Button images.
    protected BufferedImage onHoverButtonImg;
    protected BufferedImage buttonImg;
    protected BufferedImage activeImage;

    //  Default button dimensions.
    private static final int BUTTON_WIDTH = 375;
    private static final int BUTTON_HEIGHT = 82;

    //  Offsets for the button.
    private final int X_OFFSET;
    private final int Y_OFFSET;

    //  Price of the actual item.
    private final int PRICE;
    //  Price per magazine (if applicable)
    private final int PRICE_PER_MAGAZINE;

    public ShopButton (Game _game, ShopScreen _shopScreen, int _x, int _y, int _price) {
        super(_x, _y);
        this.game = _game;
        this.playerInventory = this.game.getPlayer().getInventory();
        this.shopScreen = _shopScreen;
        this.setWidth(BUTTON_WIDTH);
        this.setHeight(BUTTON_HEIGHT);

        this.X_OFFSET = _x;
        this.Y_OFFSET = _y;

        this.PRICE = _price;
        this.PRICE_PER_MAGAZINE = 0;

        this.initializeButtonImages();
        this.setScaled(true);
    }

    public ShopButton (Game _game, ShopScreen _shopScreen, int _x, int _y, int _price, int _pricePerMagazine) {
        super(_x, _y);
        this.game = _game;
        this.playerInventory = this.game.getPlayer().getInventory();
        this.shopScreen = _shopScreen;
        this.setWidth(BUTTON_WIDTH);
        this.setHeight(BUTTON_HEIGHT);

        this.X_OFFSET = _x;
        this.Y_OFFSET = _y;

        this.PRICE = _price;
        this.PRICE_PER_MAGAZINE = _pricePerMagazine;

        this.initializeButtonImages();
        this.setScaled(true);
    }

    @Override
    public void tick () {
        if (!this.getGame().isShop()) {
            return;
        }
        this.setX((int) this.getGame().getCamera().getX() - X_OFFSET);
        this.setY((int) this.getGame().getCamera().getY() - Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getGame().isShop()) {
            return;
        }
        _g2.drawImage(this.activeImage, this.getX(), this.getY(), null);
    }

    @Override
    public abstract void onMouseClick ();

    @Override
    public void onMouseEnterHover () {
        if (!this.game.isShop()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;

    }

    @Override
    public void onMouseExitHover () {
        if (!this.game.isShop()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }

    /**
     * Instantiates the two button templates for the shop item text and icon, as
     * well as when the user hovers over it.
     */
    private void initializeButtonImages () {
        this.buttonImg = StdOps.loadImage("src/resources/img/ui/shop_icons/shop_button.png");
        this.onHoverButtonImg = StdOps.loadImage("src/resources/img/ui/shop_icons/shop_buttonh.png");
        this.activeImage = this.buttonImg;
    }

//=============================== GETTERS ====================================//
    public Game getGame () {
        return this.game;
    }

    public int getPrice () {
        return this.PRICE;
    }

    public int getPricePerMagazine () {
        return this.PRICE_PER_MAGAZINE;
    }

    public Inventory getInventory () {
        return this.playerInventory;
    }
}
