package com.dsd.game.userinterface.shop.weapons.views;

import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.shop.weapons.models.RifleButton;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 * Standard rifle shop button view.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class RifleButtonView extends ShopButtonView {

    public RifleButtonView(RifleButton _rifleButton) {
        super(_rifleButton, StdOps.loadImage("src/resources/img/items/icons/Rifle_icon.png"));
    }

    @Override
    public void tick() {
        //  Only update the text if the user has the rifle in their inventory.
        if (this.parentButton.getGame().getPlayer().getInventory().hasWeapon(WeaponType.RIFLE) == null) {
            this.text.setText("AK-47 $" + this.parentButton.getPrice());
        } else {
            this.text.setText("AK-47 AMMO (31/$" + this.parentButton.getPricePerMagazine() + ")");
        }
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }
}
