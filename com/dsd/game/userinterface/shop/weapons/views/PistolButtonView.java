package com.dsd.game.userinterface.shop.weapons.views;

import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.shop.weapons.models.PistolButton;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 * This is the view for purchasing the Fast Rifle (PPSh-41), or purchasing its
 * ammunition.
 *
 * @author Joshua
 */
public class PistolButtonView extends ShopButtonView {

    public PistolButtonView(PistolButton _pistolButton) {
        super(_pistolButton, StdOps.loadImage("src/resources/img/items/icons/pistol_icon.png"));
    }

    @Override
    public void tick() {
        //  Only update the text if the user has the rifle in their inventory.
        if (this.parentButton.getGame().getPlayer().getInventory().hasWeapon(WeaponType.PISTOL) == null) {
            this.text.setText("Glock-17 $" + this.parentButton.getPrice());
        } else {
            this.text.setText("Glock-17 AMMO (16/$" + this.parentButton.getPricePerMagazine() + ")");
        }
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }
}
