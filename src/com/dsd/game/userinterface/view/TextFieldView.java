package com.dsd.game.userinterface.view;

import com.dsd.game.userinterface.model.TextFieldModel;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import static org.apache.commons.math3.stat.inference.TestUtils.g;

/**
 * This class represents the view of a text-field model. We have a border color,
 * an active color and inactive color. If the user clicks the box, it will
 * become active.
 *
 * @author Joshua
 */
public class TextFieldView implements Renderable, Updatable {

    private final TextFieldModel model;
    private Rectangle rectangleView;
    private static final Color BORDER_COLOR = Color.BLUE;
    private static final Color ACTIVE_COLOR = Color.WHITE;
    private static final Color UNACTIVE_COLOR = Color.GRAY;
    private static final Color TEXT_COLOR = Color.BLACK;

    public TextFieldView(TextFieldModel _model) {
        this.model = _model;
        this.rectangleView = new Rectangle(this.model.getX(), this.model.getY(), this.model.getWidth(), this.model.getHeight());
    }

    @Override
    public void tick() {
        this.rectangleView = new Rectangle(this.model.getX(), this.model.getY(), this.model.getWidth(), this.model.getHeight());
    }

    @Override
    public void render(Graphics2D _g2) {
        this.drawTextBox(_g2);
        this.drawBorder(_g2);
        this.drawString(_g2);
    }

    /**
     * Draws the border around the textbox.
     *
     * @param _g2
     */
    private void drawBorder(Graphics2D _g2) {
        _g2.setColor(BORDER_COLOR);
        _g2.draw(this.rectangleView);
    }

    /**
     * Draws the actual textbox.
     *
     * @param _g2
     */
    private void drawTextBox(Graphics2D _g2) {
        if (this.model.isActive()) {
            _g2.setColor(ACTIVE_COLOR);
        } else {
            _g2.setColor(UNACTIVE_COLOR);
        }
        _g2.fill(this.rectangleView);
    }

    /**
     * Draws the string that is stored in the stringbuilder. If it is "masked"
     * in the model, then we will only draw asterisks.
     *
     * @param _g2
     */
    private void drawString(Graphics2D _g2) {
        _g2.setColor(TEXT_COLOR);
        _g2.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        if (!this.model.isHidden()) {
            _g2.drawString(this.model.getString(), this.model.getX(), this.model.getY() + 24);
        } else {
            _g2.drawString(this.model.getMaskedString(), this.model.getX(), this.model.getY() + 24);
        }
    }
}
