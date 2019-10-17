package com.dsd.game.userinterface.model.labels;

import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.util.StdOps;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Creates a string of text drawn at the user-specified location.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class StandardLabel extends Interactor implements MouseEventInterface {

    private String text;
    private final int originX;
    private final int originY;
    private final int shakeFactor = 1;
    private final Font font;

    public StandardLabel (int _x, int _y, String _text, Font _fontPath) {
        super(_x, _y);
        this.originX = _x;
        this.originY = _y;
        this.text = _text;
        this.font = _fontPath;
    }

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
        Font oldFont = _g2.getFont();
        _g2.setFont(this.font);
        _g2.drawString(this.text, (int) this.getX(), (int) this.getY());
        _g2.setFont(oldFont);
    }

    @Override
    public void onMouseClick () {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMouseEnterHover () {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMouseExitHover () {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//=============================== GETTERS ====================================//
    public Font getFont () {
        return this.font;
    }

    public String getText () {
        return this.text;
    }

    public void setText (String _text) {
        this.text = _text;
    }
}
