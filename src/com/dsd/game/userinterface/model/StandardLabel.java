package com.dsd.game.userinterface.model;

import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Creates a string of text drawn at the user-specified location.
 *
 * @author Joshua
 */
public class StandardLabel extends Interactor {

    private String text;

    private final int originX;
    private final int originY;
    private final int shakeFactor = 1;

    private Font font;

    public StandardLabel (int _x, int _y, String _text, String _fontPath, float _fontSize) {
        super(_x, _y);

        this.originX = _x;
        this.originY = _y;
        this.text = _text;

        this.font = StdOps.initFont(_fontPath, _fontSize);
    }

    @Override
    public void tick () {
        this.setX(StdOps.rand(this.originX - shakeFactor, this.originX + this.shakeFactor));
        this.setY(StdOps.rand(this.originY - shakeFactor, this.originY + this.shakeFactor));
    }

    @Override
    public void render (Graphics2D _g2) {
        StandardDraw.text(this.text, this.getX(), this.getY(), this.font, this.font.getSize(), Color.WHITE);
    }
}
