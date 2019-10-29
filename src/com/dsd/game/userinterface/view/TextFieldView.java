package com.dsd.game.userinterface.view;

import com.dsd.game.userinterface.model.TextFieldModel;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class represents the view of a text-field model. We have a border color,
 * an active color and inactive color. If the user clicks the box, it will
 * become active.
 *
 * @author Joshua
 */
public class TextFieldView implements Renderable, Updatable {

    //  Backing model for this TextView
    private final TextFieldModel model;

    //  Rectangle view object.
    private Rectangle rectangleView;

    //  Miscellaneous colors.
    private static final Color BORDER_COLOR = Color.BLUE;
    private static final Color ACTIVE_COLOR = Color.WHITE;
    private static final Color UNACTIVE_COLOR = Color.GRAY;
    private static final Color TEXT_COLOR = Color.BLACK;

    //  Font size.
    private static final int FONT_SIZE = 30;
    private static final double FONT_SIZE_FACTOR = 1.15;
    private static final Font font = StdOps.initFont("src/resources/fonts/chargen.ttf", FONT_SIZE);

    //  Text position offsets.
    private static final int TEXT_X_OFFSET = 10;
    private static final int HIDDEN_TEXT_Y_OFFSET = 5;

    public TextFieldView (TextFieldModel _model) {
        this.model = _model;
        this.rectangleView = new Rectangle(this.model.getX(), this.model.getY(), this.model.getWidth(), this.model.getHeight());
    }

    @Override
    public void tick () {
        this.rectangleView = new Rectangle(this.model.getX(), this.model.getY(), this.model.getWidth(), this.model.getHeight());
    }

    @Override
    public void render (Graphics2D _g2) {
        this.drawTextBox(_g2);
        this.drawBorder(_g2);
        this.drawString(_g2);
    }

    /**
     * Draws the border around the textbox.
     *
     * @param _g2
     */
    private void drawBorder (Graphics2D _g2) {
        _g2.setColor(TextFieldView.BORDER_COLOR);
        _g2.draw(this.rectangleView);
    }

    /**
     * Draws the actual textbox.
     *
     * @param _g2
     */
    private void drawTextBox (Graphics2D _g2) {
        if (this.model.isActive()) {
            _g2.setColor(TextFieldView.ACTIVE_COLOR);
        }
        else {
            _g2.setColor(TextFieldView.UNACTIVE_COLOR);
        }

        _g2.fill(this.rectangleView);
    }

    /**
     * Draws the string that is stored in the stringbuilder. If it is "masked"
     * in the model, then we will only draw asterisks.
     *
     * @param _g2
     */
    private void drawString (Graphics2D _g2) {
        _g2.setColor(TextFieldView.TEXT_COLOR);
        _g2.setFont(TextFieldView.font);

        if (!this.model.isHidden()) {
            _g2.drawString(this.model.getString(), this.model.getX() + TEXT_X_OFFSET, this.model.getY() + (int) (FONT_SIZE * FONT_SIZE_FACTOR));
        }
        else {
            _g2.drawString(this.model.getMaskedString(), this.model.getX() + TEXT_X_OFFSET, this.model.getY() + HIDDEN_TEXT_Y_OFFSET + (int) (FONT_SIZE * FONT_SIZE_FACTOR));
        }
    }
}
