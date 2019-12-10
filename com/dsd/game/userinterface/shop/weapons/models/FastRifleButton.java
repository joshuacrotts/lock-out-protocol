package com.dsd.game.userinterface.shop.weapons.models;

import com.dsd.game.core.Game;
import com.dsd.game.objects.weapons.FastRifle;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.objects.weapons.enums.WeaponType;
import com.dsd.game.userinterface.ShopScreen;
import com.dsd.game.userinterface.shop.weapons.views.FastRifleButtonView;
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
public class FastRifleButton extends ShopButton {

    //  Associated view with the shop button.
    private final FastRifleButtonView fastRifleView;

    //  Offset positions.
    private static final int X_OFFSET = 400;
    private static final int Y_OFFSET = -24;

    //  Price to purchase the gun.
    private static final int FAST_RIFLE_PRICE = 500;

    //  Price per magazine.
    private static final int FAST_RIFLE_AMMO_PRICE = 75;

    public FastRifleButton(Game _game, ShopScreen _shopScreen) {
        super(_game, _shopScreen, FastRifleButton.X_OFFSET,
                FastRifleButton.Y_OFFSET,
                FAST_RIFLE_PRICE,
                FAST_RIFLE_AMMO_PRICE);
        this.fastRifleView = new FastRifleButtonView(this);
    }

    @Override
    public void tick() {
        this.setX((int) this.getGame().getCamera().getX() - FastRifleButton.X_OFFSET);
        this.setY((int) this.getGame().getCamera().getY() - FastRifleButton.Y_OFFSET);
        this.fastRifleView.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
        this.fastRifleView.render(_g2);
    }

    @Override
    public void onMouseClick() {
        if (!this.getGame().isShop()) {
            return;
        }
        Gun _weapon = (Gun) this.getInventory().hasWeapon(WeaponType.FAST_RIFLE);
        //  If we don't have the weapon, add it to the user's inventory.
        if (_weapon == null && this.getGame().getPlayer().getMoney() >= this.getPrice()) {
            this.getGame().getPlayer().setMoney(this.getGame().getPlayer().getMoney() - this.getPrice());
            this.getInventory().addWeapon(new FastRifle(this.getGame(),
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
