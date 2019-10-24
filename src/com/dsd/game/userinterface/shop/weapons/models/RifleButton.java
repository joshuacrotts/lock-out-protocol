package com.dsd.game.userinterface.shop.weapons.models;

import com.dsd.game.Game;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.Rifle;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.shop.weapons.views.RifleButtonView;
import java.awt.Graphics2D;

/**
 * This button is for buying either the weapon for a rifle, or buying more rifle
 * ammunition.
 *
 * @author Joshua
 */
public class RifleButton extends ShopButton {

    //  Rifle button view.
    private final RifleButtonView rifleButtonView;

    //  Button offsets.
    public static final int X_OFFSET = -50;
    public static final int Y_OFFSET = 176;

    //  Price to purchase the gun.
    private static final int RIFLE_PRICE = 250;

    //  Price per magazine.
    private static final int RIFLE_AMMO_PRICE = 30;

    public RifleButton (Game _game, ShopScreen _shopScreen) {
        super(_game, _shopScreen, RifleButton.X_OFFSET, RifleButton.Y_OFFSET,
                RIFLE_PRICE,
                RIFLE_AMMO_PRICE);

        this.rifleButtonView = new RifleButtonView(this);
    }

    @Override
    public void tick () {
        super.tick();
        this.rifleButtonView.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
        this.rifleButtonView.render(_g2);
    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isShop()) {
            return;
        }
        //  If we have enough money...

        Gun _weapon = (Gun) this.getInventory().hasWeapon(WeaponType.RIFLE);

        //  If we don't have the weapon, add it to the user's inventory.
        if (_weapon == null && this.getGame().getPlayer().getMoney() >= this.getPrice()) {
            this.getGame().getPlayer().setMoney(this.getGame().getPlayer().getMoney() - this.getPrice());

            this.getInventory().addWeapon(new Rifle(this.getGame(),
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
