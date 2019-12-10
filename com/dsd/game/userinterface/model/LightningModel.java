package com.dsd.game.userinterface.model;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Pair;

/**
 * This class represents a simple lightning effect; it uses an array of line
 * segments to draw the shape.
 *
 * @author Joshua
 *
 * @updated 12/10/19
 */
public class LightningModel extends Interactor {

    //  Miscellaneous game and camera references.
    private final Game game;
    private final StandardCamera camera;
    
    //  Line array of lightning segments, which is populated at run time.
    private Line2D[] lightningLines;
    
    //  Values determining the variability and shape of each line connecting
    //  to the bolt.
    private final int MIN_X_VARIABILITY = 5;
    private final int MAX_X_VARIABILITY = 30;
    private final int Y_VARIABILITY = 3;

    public LightningModel(Game _game) {
        this.game = _game;
        this.camera = this.game.getCamera();
    }

    @Override
    public void tick() {
        this.updateLightningPos();
    }

    @Override
    public void render(Graphics2D _g2) {
        _g2.setColor(StandardDraw.BLUE_CRAYOLA);
        if (this.lightningLines != null) {
            for (Line2D line : lightningLines) {
                if (line != null) {
                    _g2.draw(line);
                }
            }
        }
    }

    @Override
    public void onMouseClick() {
        //  No mouse logic.

    }

    @Override
    public void onMouseEnterHover() {
        //  No mouse logic.

    }

    @Override
    public void onMouseExitHover() {
        //  No mouse logic.

    }

    /**
     * Updates the lightning array object that holds the coordinates for each of
     * the line-segment points.
     */
    private void updateLightningPos() {
        List<Pair<Integer, Integer>> lightningPoints = this.generatePoints();
        this.lightningLines = new Line2D[lightningPoints.size()];
        
        for (int i = 0; i < this.lightningLines.length - 1; i++) {
            Pair<Integer, Integer> coordOne = lightningPoints.get(i);
            Pair<Integer, Integer> coordTwo = lightningPoints.get(i + 1);
            this.lightningLines[i] = new Line2D.Double(coordOne.getFirst(), coordOne.getSecond(),
                    coordTwo.getFirst(), coordTwo.getSecond());
        }
    }

    /**
     * Generates the points that are used to draw the lightning bolt.
     *
     * @return
     */
    private List<Pair<Integer, Integer>> generatePoints() {
        List<Pair<Integer, Integer>> boltPositions = new ArrayList<>();
        int prevXPos = (int) (this.camera.getX() - Screen.gameHalfWidth);
        int prevYPos = (int) StdOps.rand(this.camera.getY() - this.Y_VARIABILITY, this.camera.getY() + this.Y_VARIABILITY);
        
        //  Add initial pair.
        boltPositions.add(new Pair(prevXPos, prevYPos));
        
        //  While the bolt still hasn't reached the edge of the screen.
        while (prevXPos < this.camera.getX() + Screen.gameHalfWidth) {
            int deltaX = StdOps.rand(this.MIN_X_VARIABILITY, this.MAX_X_VARIABILITY);
            int deltaY = StdOps.rand(-this.Y_VARIABILITY, this.Y_VARIABILITY);
            
            //  Apply random positioning offsets to the previous x/y pair.
            prevXPos += deltaX;
            prevYPos += deltaY;
            boltPositions.add(new Pair(prevXPos, prevYPos));
        }
        return boltPositions;
    }
}
