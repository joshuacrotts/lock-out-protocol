package com.dsd.game.userinterface.shop.weapons.views;

import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.shop.weapons.models.GrenadeLauncherButton;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;

/**
 * Grenade launcher shop button view.
 *
 * @author Joshua
 */
public class GrenadeLauncherButtonView extends ShopButtonView {

    public GrenadeLauncherButtonView(GrenadeLauncherButton _grenadeButton) {
        super(_grenadeButton, StdOps.loadImage("src/resources/img/items/icons/GrenadeLauncher_icon.png"));
    }

    @Override
    public void tick() {
        //  Only update the text if the user has the rifle in their inventory.
        if (this.parentButton.getGame().getPlayer().getInventory().hasWeapon(WeaponType.GRENADE_LAUNCHER) == null) {
            this.text.setText("GREN. LAUNCHER $" + this.parentButton.getPrice());
        } else {
            this.text.setText("GRENADE AMMO (4/$" + this.parentButton.getPricePerMagazine() + ")");
        }
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
    }
}
