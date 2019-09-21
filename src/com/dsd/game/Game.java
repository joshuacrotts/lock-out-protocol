package com.dsd.game;

import com.dsd.game.api.CityLocator;
import com.dsd.game.api.WeatherConnector;
import com.dsd.game.controller.RainController;
import com.dsd.game.objects.Player;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardLevel;

/**
 * This is the main class.
 *
 * @author Joshua
 */
public class Game extends StandardGame {

    private final StandardCamera sc;
    private final StandardCollisionHandler sch;
    private final StandardLevel level;

    private final RainController rainController;

    public Game () {
        super(1280, 720, "CSC-340 Game");

        //  Initialize the sound controller
        StandardAudioController.init(8);

        //  Create a new collision handler
        this.sch = new StandardCollisionHandler(null);

        //  Instantiates player & adds it to the handler
        Player player = new Player(this, null, this.sch, 200, 200);
        this.sch.addEntity(player);

        //  Instantiate the camera
        this.sc = new StandardCamera(this, player, 1, this.getGameWidth(), this.getGameHeight());

        //  Prevents the camera from scrolling too far to any of the sides
        int cameraMinX = 640;//These numbers are just guess&check..
        int cameraMaxX = 1080;
        int cameraMinY = 350;
        int cameraMaxY = 720;

        this.sc.restrict(cameraMaxX, cameraMaxY, cameraMinX, cameraMinY);

        //  Sets the camera for the player and the handler
        player.setCamera(this.sc);
        this.sch.setCamera(this.sc);

        // Instantiates the rain controller
        this.rainController = new RainController(this, this.sc, WeatherConnector.getWeather(CityLocator.getCity()));

        this.level = new ForestLevel(player);

        this.StartGame();
    }

    @Override
    public void tick () {
        //  Update the level background first
        this.level.tick();
        //  Then the objects within the handler
        StandardHandler.Handler(this.sch);
        // Then the rain if applicable
        this.rainController.tick();
        //  And lastly the camera
        StandardHandler.Object(this.sc);
    }

    @Override
    public void render () {
        //  First things first: render the camera
        StandardDraw.Object(this.sc);

        //  Then render the level
        this.level.render(StandardDraw.Renderer);

        // Then render the rain if applicable
        this.rainController.render(StandardDraw.Renderer);

        //  Then render the handler objects
        StandardDraw.Handler(this.sch);
    }

    public static void main (String[] args) {
        Game game = new Game();
    }

}
