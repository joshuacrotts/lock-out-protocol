package com.dsd.game.userinterface.view;

import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This is the view that will be shown when the user wants to change their
 * player gender.
 *
 * @author rinty
 */
public class PlayerView implements Renderable, Updatable {

    private static BufferedImage icon;
    private static final int IMAGE_WIDTH = 15;
    private static final int IMAGE_HEIGHT = 12;
    private final String sex;

    public PlayerView(String _sex) {
        this.icon = StdOps.loadImage("src/resources/img/player/player_icon/" + _sex + "_icon.png");
        this.sex = _sex;
    }

    @Override
    public void render(Graphics2D _g2) {
        _g2.drawImage(icon, null, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    @Override
    public void tick() {
    }
}
