package com.dsd.game.userinterface.shop.weapons.views;

import com.dsd.game.userinterface.model.labels.ShopTextLabel;
import com.dsd.game.userinterface.shop.weapons.models.ShopButton;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class is the superclass to all shop button views. Each shop-button has a
 * corresponding item paired with it, which is displayed in the gray/black box
 * to the left of the green one.
 *
 * @author Joshua
 */
public abstract class ShopButtonView implements Updatable, Renderable {

    //  Parent button and text label for button information.
    protected final ShopButton parentButton;
    protected final ShopTextLabel text;

    //  Icon image for the corresponding button.
    private final BufferedImage buttonIconImage;

    public ShopButtonView (ShopButton _shopButton, BufferedImage _image) {
        this.parentButton = _shopButton;
        this.text = new ShopTextLabel(_shopButton.getGame(), this.parentButton.getX(), this.parentButton.getY());
        this.buttonIconImage = _image;
    }

    @Override
    public void tick () {
        this.text.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.drawImage(this.buttonIconImage, this.parentButton.getX(), this.parentButton.getY(), null);
        this.text.render(_g2);
    }
}
