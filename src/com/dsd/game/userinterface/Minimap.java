package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.util.StdOps;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * This class is a minimap in the top right of the screen. Pretty
 * self-explanatory.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class Minimap extends Interactor {

    //  Miscellaneous reference variables
    private final Game game;
    private final StandardCollisionHandler globalHandler;

    //  Border texture that surrounds the image with the objects
    private final BufferedImage border;

    //  Scale that is applied to all objects in the map
    private final int MINIMAP_SCALE = 20;
    private final int MMX_OFFSET = 230;
    private final int MMY_OFFSET = 20;

    //  Object and map size dimensions
    private final int MAP_DIMENSION = 200;
    private final int OBJECT_DIMENTION = 5;

    //  Points for describing the triangle that draws the player
    private final int[] X_POINTS;
    private final int[] Y_POINTS;

    //  Indices in the arrays of x/y points
    private final int POINT_ONE = 0;
    private final int POINT_TWO = 1;
    private final int POINT_THREE = 2;
    private final int POINT_FOUR = 3;

    //  Scale for the player's triangle
    private final int TRIANGLE_X_SCALE = 6;
    private final int TRIANGLE_Y_SCALE = 12;

    public Minimap (Game _game, StandardCollisionHandler _sch) {
        this.game = _game;
        this.globalHandler = _sch;
        this.X_POINTS = new int[]{0, 0, 0, 0};
        this.Y_POINTS = new int[]{0, 0, 0, 0};
        this.border = StdOps.loadImage("src/resources/img/bg/borders/minimap_border2.png");
    }

    @Override
    public void render (Graphics2D _g2) {
        this.drawMapBackground(_g2);
        /**
         * Renders a copy of all entities on the screen, but by a factor of 20x
         * smaller.
         */
        for (int i = 0 ; i < this.globalHandler.size() ; i++) {
            StandardGameObject obj = this.globalHandler.get(i);

            if (obj != null && obj.isAlive()) {
                switch (obj.getId()) {
                    case Player:
                        this.drawPlayer(_g2, (Player) obj);
                        break;
                    case BasicMonster:
                        this.drawObject(_g2, obj, StandardDraw.RED);
                        break;
                    case Monster2:
                        this.drawObject(_g2, obj, StandardDraw.BRUNSWICK_GREEN);
                        break;
                    case Monster3:
                        this.drawObject(_g2, obj, StandardDraw.PURPLE);
                        break;
                    case Monster4:
                        this.drawObject(_g2, obj, StandardDraw.PINK);
                }
            }
        }
        this.drawBorder(_g2);
    }

    /**
     * Draws the transparent background for the minimap.
     *
     * @param _g2
     */
    private void drawMapBackground (Graphics2D _g2) {
        AlphaComposite oldComposite = (AlphaComposite) _g2.getComposite();
        _g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        _g2.drawImage(this.game.getCurrentLevel().getBgImage(),
                (int) this.game.getCamera().getX() + Screen.gameHalfWidth - this.MMX_OFFSET,
                (int) this.game.getCamera().getY() - Screen.gameHalfHeight + this.MMY_OFFSET,
                MAP_DIMENSION, MAP_DIMENSION, null);
        _g2.setComposite(oldComposite);
    }

    /**
     * Draws the border around the transparent map background.
     *
     * @param _g2
     */
    private void drawBorder (Graphics2D _g2) {
        _g2.drawImage(this.border,
                (int) this.game.getCamera().getX() + Screen.gameHalfWidth - this.MMX_OFFSET,
                (int) this.game.getCamera().getY() - Screen.gameHalfHeight + this.MMY_OFFSET,
                null);
    }

    /**
     * Draws the triangle representing the player and what direction they're
     * facing in the minimap.
     *
     * @param _g2
     * @param _player
     */
    private void drawPlayer (Graphics2D _g2, Player _player) {
        int scaledPX = (int) (this.game.getCamera().getX() + Screen.gameHalfWidth - this.MMX_OFFSET + (_player.getX() / this.MINIMAP_SCALE));
        int scaledPY = (int) (this.game.getCamera().getY() - Screen.gameHalfHeight + this.MMY_OFFSET + (_player.getY() / this.MINIMAP_SCALE));
        this.createPoints(scaledPX, scaledPY);
        /**
         * Instantiates the translation/transform object to rotate the triangle
         * to whatever angle the player is rotated to.
         */
        AffineTransform backup = _g2.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.rotate(_player.getAngle(), scaledPX, scaledPY);
        _g2.setColor(StandardDraw.GREEN);
        _g2.transform(transform);
        _g2.fill(new Polygon(this.X_POINTS, this.Y_POINTS, this.X_POINTS.length));
        _g2.setTransform(backup);
    }

    /**
     * Draws a square representing the SGO specified by the color.
     *
     * @param _g2
     * @param obj
     * @param color
     */
    private void drawObject (Graphics2D _g2, StandardGameObject obj, Color color) {
        _g2.setColor(color);
        _g2.fillRect((int) (this.game.getCamera().getX() + Screen.gameHalfWidth - this.MMX_OFFSET + (obj.getX() / this.MINIMAP_SCALE)),
                (int) (this.game.getCamera().getY() - Screen.gameHalfHeight + this.MMY_OFFSET + (obj.getY() / this.MINIMAP_SCALE)),
                OBJECT_DIMENTION, OBJECT_DIMENTION);
    }

    /**
     * Reassigns the re-scaled points to the x and y point arrays so we can draw
     * the triangle.
     *
     * @param _scaledPX
     * @param _scaledPY
     */
    private void createPoints (int _scaledPX, int _scaledPY) {
        //  Creates the points necessary for the triangle
        this.X_POINTS[POINT_ONE] = _scaledPX;
        this.X_POINTS[POINT_TWO] = _scaledPX - TRIANGLE_X_SCALE;
        this.X_POINTS[POINT_THREE] = _scaledPX;
        this.X_POINTS[POINT_FOUR] = _scaledPX + TRIANGLE_X_SCALE;
        this.Y_POINTS[POINT_ONE] = _scaledPY;
        this.Y_POINTS[POINT_TWO] = _scaledPY + TRIANGLE_Y_SCALE;
        this.Y_POINTS[POINT_THREE] = _scaledPY + TRIANGLE_X_SCALE;
        this.Y_POINTS[POINT_FOUR] = _scaledPY + TRIANGLE_Y_SCALE;
    }

    @Override
    public void tick () {
    }

    @Override
    public void onMouseClick () {
    }

    @Override
    public void onMouseEnterHover () {
    }

    @Override
    public void onMouseExitHover () {
    }

}
