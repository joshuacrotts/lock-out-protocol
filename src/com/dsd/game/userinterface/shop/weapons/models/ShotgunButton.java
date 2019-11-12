package com.dsd.game.userinterface.shop.weapons.models;

import com.dsd.game.core.Game;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.Shotgun;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.shop.weapons.views.ShotgunButtonView;
import java.awt.Graphics2D;

/**
 * This button is for buying either the weapon for a rifle, or buying more rifle
 * ammunition.
 *
 * @author Joshua
 */
public class ShotgunButton extends ShopButton {

    //  Associated view with the shop button.
    private final ShotgunButtonView shotgunButtonView;

    //  Offset positions.
    private static final int X_OFFSET = -50;
    private static final int Y_OFFSET = 76;

    //  Price to purchase the gun.
    private static final int SHOTGUN_PRICE = 400;

    //  Price per magazine.
    private static final int SHOTGUN_AMMO_PRICE = 80;

    public ShotgunButton (Game _game, ShopScreen _shopScreen) {
        super(_game, _shopScreen, ShotgunButton.X_OFFSET,
                ShotgunButton.Y_OFFSET,
                SHOTGUN_PRICE,
                SHOTGUN_AMMO_PRICE);

        this.shotgunButtonView = new ShotgunButtonView(this);
    }

    @Override
    public void tick () {
        super.tick();
        this.shotgunButtonView.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
        this.shotgunButtonView.render(_g2);
    }

    @Override
    public void onMouseClick () {
        //  If we have enough money...
        if (!this.getGame().isShop()) {
            return;
        }
        Gun _weapon = (Gun) this.getInventory().hasWeapon(WeaponType.SHOTGUN);

        //  If we don't have the weapon, add it to the user's inventory.
        if (_weapon == null && this.getGame().getPlayer().getMoney() >= this.getPrice()) {
            this.getGame().getPlayer().setMoney(this.getGame().getPlayer().getMoney() - this.getPrice());

            this.getInventory().addWeapon(new Shotgun(this.getGame(),
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
