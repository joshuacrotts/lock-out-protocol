package com.dsd.game.userinterface.model.labels;

import com.dsd.game.Game;
import com.dsd.game.controller.LanguageController;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Label that shows what wave we're about to transition to.
 *
 * @author Joshua
 */
public class WaveLabel extends StandardLabel {

    private final StandardCamera camera;
    private final int VARIABILITY = 1;

    public WaveLabel (Game _game, int _waveNumber) {
        super((int) _game.getCamera().getX(), (int) _game.getCamera().getY(),
                LanguageController.translate("Wave ") + _waveNumber, "src/resources/fonts/chargen.ttf", 32f);
        this.camera = _game.getCamera();
    }

    @Override
    public void tick () {
        int xPos = (int) StdOps.rand(this.camera.getX() - this.VARIABILITY, this.camera.getX() + this.VARIABILITY);
        int yPos = (int) StdOps.rand(this.camera.getY() - this.VARIABILITY, this.camera.getY() + this.VARIABILITY);
        this.setX(xPos);
        this.setY(yPos);
    }

    @Override
    public void render (Graphics2D _g2) {
        Color oldColor = _g2.getColor();
        _g2.setColor(Color.WHITE);
        super.render(_g2);
        _g2.setColor(oldColor);
    }

    public void setWaveNumber (int _waveNumber) {
        this.setText(LanguageController.translate("Wave ") + _waveNumber);
    }
}
