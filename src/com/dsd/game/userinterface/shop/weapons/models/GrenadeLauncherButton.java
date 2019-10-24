package com.dsd.game.userinterface.shop.weapons.models;

import com.dsd.game.Game;
import com.dsd.game.objects.weapons.GrenadeLauncher;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.shop.weapons.views.GrenadeLauncherButtonView;
import java.awt.Graphics2D;

/**
 * This button is for buying either the weapon for a rifle, or buying more rifle
 * ammunition.
 *
 * @author Joshua
 */
public class GrenadeLauncherButton extends ShopButton {

    //  Associated view with the shop button.
    private final GrenadeLauncherButtonView grenadeLauncherView;

    //  Offset positions.
    private static final int X_OFFSET = 400;
    private static final int Y_OFFSET = 76;

    //  Price to purchase the gun.
    private static final int GRENADE_LAUNCHER_PRICE = 1500;

    //  Price per magazine.
    private static final int GRENADE_LAUNCHER_AMMO_PRICE = 150;

    public GrenadeLauncherButton (Game _game, ShopScreen _shopScreen) {
        super(_game, _shopScreen, GrenadeLauncherButton.X_OFFSET,
                GrenadeLauncherButton.Y_OFFSET,
                GRENADE_LAUNCHER_PRICE,
                GRENADE_LAUNCHER_AMMO_PRICE);

        this.grenadeLauncherView = new GrenadeLauncherButtonView(this);
    }

    @Override
    public void tick () {
        super.tick();
        this.grenadeLauncherView.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
        this.grenadeLauncherView.render(_g2);
    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isShop()) {
            return;
        }
        //  If we have enough money...

        Gun _weapon = (Gun) this.getInventory().hasWeapon(WeaponType.GRENADE_LAUNCHER);

        //  If we don't have the weapon, add it to the user's inventory.
        if (_weapon == null && this.getGame().getPlayer().getMoney() >= this.getPrice()) {
            this.getGame().getPlayer().setMoney(this.getGame().getPlayer().getMoney() - this.getPrice());

            this.getInventory().addWeapon(new GrenadeLauncher(this.getGame(),
                    this.getGame().getPlayer(), this.getGame().getPlayer().getHandler()));
        }
        //  Otherwise, add to the ammunition.
        else if (this.getGame().getPlayer().getMoney() >= this.getPricePerMagazine()) {
            this.getGame().getPlayer().setMoney(this.getGame().getPlayer().getMoney() - this.getPricePerMagazine());

            _weapon.setTotalAmmo(_weapon.getTotalAmmo() + _weapon.getMagazineCapacity());
        }
    }

    @Override
    public void onMouseEnterHover () {
        super.onMouseEnterHover();
    }

    @Override
    public void onMouseExitHover () {
        super.onMouseExitHover();
    }
}
