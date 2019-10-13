/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.model;

import com.dsd.game.userinterface.model.labels.StandardLabel;
import com.dsd.game.userinterface.StandardInteractorHandler;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * This class represents a string that appears above monsters when you shoot
 * them.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class DamageText extends StandardLabel {

    //  Interactor handler that this damage text belongs to
    private final StandardInteractorHandler sih;
    //  Font for the text
    private static final Font font = StdOps.initFont("src/resources/fonts/chargen.ttf", 12f);
    /**
     * Values regarding the color of the damage text, how fast it fades, and how
     * quickly it changes colors.
     */
    private static final int RED_INC_VALUE = 20;
    private static final int ORIGINAL_RED_VALUE = 90;
    private static int redColorValue = ORIGINAL_RED_VALUE;
    private final int FADE_TIMER = 5;
    private Color fadeColor;

    public DamageText (int _x, int _y, String _text, StandardInteractorHandler _sih) {
        super(_x, _y, _text, font);
        this.sih = _sih;
        this.fadeColor = new Color(this.generateRedColor(), 0, 0, 0xff);
        this.setVelY(-1);
    }

    @Override
    public void tick () {
        if (this.fadeColor.getAlpha() <= 0) {
            this.sih.getInteractors().remove(this);
        }
        else {
            this.updateColor();
            this.setY(this.getY() + this.getVelY());
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        StandardDraw.text(this.getText(), this.getX(), this.getY(), font, font.getSize(), this.fadeColor);
    }

    /**
     * Updates the color of the text being drawn above the monster when hurt.
     */
    private void updateColor () {
        this.fadeColor = new Color(this.fadeColor.getRed(),
                this.fadeColor.getGreen(),
                this.fadeColor.getBlue(),
                this.fadeColor.getAlpha() - FADE_TIMER);
    }

    /**
     * Continuously generates a red value for the text, getting increasingly
     * brighter with every new text.
     *
     * @return redValue if leq 255, original red value otherwise.
     */
    private int generateRedColor () {
        DamageText.redColorValue += RED_INC_VALUE;
        return DamageText.redColorValue > 255 ? (DamageText.redColorValue = ORIGINAL_RED_VALUE) : DamageText.redColorValue;
    }
}
