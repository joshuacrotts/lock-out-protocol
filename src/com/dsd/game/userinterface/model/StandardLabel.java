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

    public StandardLabel (int x, int y, String text, String fontPath, float fontSize) {
        super(x, y);

        this.originX = x;
        this.originY = y;
        this.text = text;

        this.initFont(fontPath, fontSize);
    }

    @Override
    public void tick () {
        this.setX(StdOps.rand(this.originX - shakeFactor, this.originX + shakeFactor));
        this.setY(StdOps.rand(this.originY - shakeFactor, this.originY + shakeFactor));
    }

    @Override
    public void render (Graphics2D _g2) {
        StandardDraw.text(this.text, this.getX(), this.getY(), this.font, this.font.getSize(), Color.WHITE);
    }

    /**
     * 
     */
    private void initFont (String fontPath, float fontSize) {
        this.font = StdOps.initFont(fontPath, fontSize);
    }

}
