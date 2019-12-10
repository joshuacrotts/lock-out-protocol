package com.dsd.game.userinterface.shop.weapons.models;

import com.dsd.game.core.Game;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.Shotgun;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.shop.weapons.views.MinigunButtonView;
import java.awt.Graphics2D;

/**
 * This button is for buying either the weapon for a rifle, or buying more rifle
 * ammunition.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class MinigunButton extends ShopButton {

    //  Associated view with the shop button.
    private final MinigunButtonView minigunButtonView;

    //  Offset positions.
    private static final int X_OFFSET = -50;
    private static final int Y_OFFSET = -24;

    //  Price to purchase the gun.
    private static final int MINIGUN_PRICE = 1200;

    //  Price per magazine.
    private static final int MINIGUN_AMMO_PRICE = 200;

    public MinigunButton(Game _game, ShopScreen _shopScreen) {
        super(_game, _shopScreen, MinigunButton.X_OFFSET,
                MinigunButton.Y_OFFSET,
                MINIGUN_PRICE,
                MINIGUN_AMMO_PRICE);
        this.minigunButtonView = new MinigunButtonView(this);
    }

    @Override
    public void tick() {
        super.tick();
        this.minigunButtonView.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
        this.minigunButtonView.render(_g2);
    }

    @Override
    public void onMouseClick() {
        //  If we have enough money...
        if (!this.getGame().isShop()) {
            return;
        }
        Gun _weapon = (Gun) this.getInventory().hasWeapon(WeaponType.MINIGUN);
        //  If we don't have the weapon, add it to the user's inventory.
        if (_weapon == null && this.getGame().getPlayer().getMoney() >= this.getPrice()) {
            this.getGame().getPlayer().setMoney(this.getGame().getPlayer().getMoney() - this.getPrice());
            this.getInventory().addWeapon(new Shotgun(this.getGame(),
                    this.getGame().getPlayer(), this.getGame().getPlayer().getHandler()));
        } //  Otherwise, add to the ammunition.
        else if (this.getGame().getPlayer().getMoney() >= this.getPricePerMagazine()) {
            this.getGame().getPlayer().setMoney(this.getGame().getPlayer().getMoney() - this.getPricePerMagazine());
            _weapon.setTotalAmmo(_weapon.getTotalAmmo() + _weapon.getMagazineCapacity());
        }
    }

    @Override
    public void onMouseEnterHover() {
        super.onMouseEnterHover();
    }

    @Override
    public void onMouseExitHover() {
        super.onMouseExitHover();
    }
}
