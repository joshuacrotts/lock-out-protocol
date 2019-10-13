/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.view;

import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joshua
 */
public class MenuView implements Renderable, Updatable {

    private final Game game;
    private final MenuScreen menuScreen;
    private static final BufferedImage background;

    public MenuView (Game _game, MenuScreen _menuScreen) {
        this.game = _game;
        this.menuScreen = _menuScreen;

    }

    @Override
    public void tick () {

    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.drawImage(background, 0, 0, this.game.getGameWidth(), this.game.getGameHeight(), null);
    }

    static {
        background = StdOps.loadImage("src/resources/img/bg/menu.png");
    }
}
