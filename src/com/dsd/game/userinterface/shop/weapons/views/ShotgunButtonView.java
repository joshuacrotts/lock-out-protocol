package com.dsd.game.userinterface.shop.weapons.views;

import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.shop.weapons.models.ShotgunButton;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 *
 * @author Joshua
 */
public class ShotgunButtonView extends ShopButtonView {

    public ShotgunButtonView (ShotgunButton _shotgunButton) {
        super(_shotgunButton, StdOps.loadImage("src/resources/img/items/icons/shotgun_icon.png"));
    }

    @Override
    public void tick () {
        //  Only update the text if the user has the rifle in their inventory.
        if (this.parentButton.getGame().getPlayer().getInventory().hasWeapon(WeaponType.SHOTGUN) == null) {
            this.text.setText("SPAS-12 $" + this.parentButton.getPrice());
        }
        else {
            this.text.setText("SPAS-12 AMMO (12/$" + this.parentButton.getPricePerMagazine() + ")");
        }

        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }
}
