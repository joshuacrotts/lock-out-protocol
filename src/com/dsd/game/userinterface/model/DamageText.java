/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.model;

import com.dsd.game.userinterface.StandardInteractorHandler;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * This class represents a string that appears above monsters when you shoot them.
 *
 * @author Joshua
 */
public class DamageText extends StandardLabel {

    private final StandardInteractorHandler sih;
    private static final Font font = StdOps.initFont("src/res/fonts/chargen.ttf", 12f);
    private Color fadeColor;
    private final int fadeTimer = 5;

    public DamageText (int _x, int _y, String _text, StandardInteractorHandler _sih) {
        super(_x, _y, _text, font);
        this.sih = _sih;
        this.fadeColor = new Color(0xff, 0xff, 0xff, 0xff);
        this.setVelY(-1);
    }

    @Override
    public void tick () {
        if (this.fadeColor.getAlpha() <= 0) {
            this.sih.getInteractors().remove(this);
        }
        else {
            this.fadeColor = new Color(this.fadeColor.getRed(),
                    this.fadeColor.getGreen(),
                    this.fadeColor.getBlue(),
                    this.fadeColor.getAlpha() - fadeTimer);

            this.setY(this.getY() + this.getVelY());
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        StandardDraw.text(this.getText(), this.getX(), this.getY(), font, font.getSize(), this.fadeColor);
    }

}
