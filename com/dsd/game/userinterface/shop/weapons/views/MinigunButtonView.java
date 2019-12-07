package com.dsd.game.userinterface.shop.weapons.views;

import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.shop.weapons.models.MinigunButton;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 *
 * @author Joshua
 */
public class MinigunButtonView extends ShopButtonView {

    public MinigunButtonView(MinigunButton _minigunButton) {
        super(_minigunButton, StdOps.loadImage("src/resources/img/items/icons/Minigun_icon.png"));
    }

    @Override
    public void tick() {
        //  Only update the text if the user has the rifle in their inventory.
        if (this.parentButton.getGame().getPlayer().getInventory().hasWeapon(WeaponType.MINIGUN) == null) {
            this.text.setText("MINIGUN $" + this.parentButton.getPrice());
        } else {
            this.text.setText("MINIGUN AMMO (100/$" + this.parentButton.getPricePerMagazine() + ")");
        }
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }
}
