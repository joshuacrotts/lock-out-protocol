package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.commands.ShopCommand;
import com.dsd.game.userinterface.shop.ShopTitleLabel;
import com.dsd.game.userinterface.shop.enums.ShopState;
import com.dsd.game.userinterface.shop.weapons.models.FastRifleButton;
import com.dsd.game.userinterface.shop.weapons.models.GrenadeLauncherButton;
import com.dsd.game.userinterface.shop.weapons.models.MinigunButton;
import com.dsd.game.userinterface.shop.weapons.models.PistolButton;
import com.dsd.game.userinterface.shop.weapons.models.RifleButton;
import com.dsd.game.userinterface.shop.weapons.models.ShotgunButton;
import com.dsd.game.userinterface.view.ShopView;
import java.awt.Graphics2D;

/**
 * Pause Screen displays a string "Paused" on the screen when the user presses
 * the escape key.
 *
 * @author Joshua, Ronald, Rinty
 */
public class ShopScreen extends Screen {

    //  General state of the shop (i.e. whether we're looking at ammo or
    //  other powerups, the shop command to access it, etc.
    private ShopState shopState;
    private final ShopCommand shopCommand;

    public ShopScreen (Game _game) {
        
        super(_game);
        this.shopCommand = new ShopCommand(_game);
        this.shopState = ShopState.WEAPONS;
        this.createUIElements();
    }

    @Override
    public void tick () {
        
        if (!this.getGame().isShop()) {
            
            return;
        }
        
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        
        if (!this.getGame().isShop()) {
            
            return;
        }
        
        super.render(_g2);
    }

    /**
     * Adds the interactors to the screen handler.
     */
    private void createUIElements () {
        
        super.addInteractor(new ShopView(this.getGame()));
        super.addInteractor(new ShopTitleLabel(this.getGame(), this));
        super.addInteractor(new PistolButton(this.getGame(), this));
        super.addInteractor(new RifleButton(this.getGame(), this));
        super.addInteractor(new GrenadeLauncherButton(this.getGame(), this));
        super.addInteractor(new FastRifleButton(this.getGame(), this));
        super.addInteractor(new ShotgunButton(this.getGame(), this));
        super.addInteractor(new MinigunButton(this.getGame(), this));
    }

//============================ GETTERS ====================================
    public boolean isOnWeapons () {
        
        return this.shopState == ShopState.WEAPONS;
    }

    public boolean isOnItems () {
        
        return this.shopState == ShopState.ITEMS;
    }

}
