package com.dsd.game.userinterface.shop.weapons.views;

import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.model.labels.ShopTextLabel;
import com.dsd.game.userinterface.shop.weapons.models.RifleButton;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joshua
 */
public class RifleButtonView implements Updatable, Renderable {

    private final RifleButton parentButton;
    private final ShopTextLabel text;

    private static final BufferedImage rifleIconImage;

    public RifleButtonView (RifleButton _rifleButton) {
        this.parentButton = _rifleButton;
        this.text = new ShopTextLabel(_rifleButton.getGame(), this.parentButton.getX(), this.parentButton.getY());

    }

    @Override
    public void tick () {
        //  Only update the text if the user has the rifle in their inventory.
        if (this.parentButton.getGame().getPlayer().getInventory().hasWeapon(WeaponType.RIFLE) == null) {
            this.text.setText("AK-47 $250");
        }
        else {
            this.text.setText("AK-47 AMMO (31/$30)");
        }
        this.text.tick();

    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.drawImage(rifleIconImage, this.parentButton.getX(), this.parentButton.getY(), null);
        this.text.render(_g2);
    }

    static {
        rifleIconImage = StdOps.loadImage("src/resources/img/items/icons/rifle_icon.png");
    }
}
