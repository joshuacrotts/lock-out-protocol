package com.dsd.game;

import com.dsd.game.api.CityLocator;
import com.dsd.game.api.WeatherConnector;
import com.dsd.game.controller.AudioBoxController;
import com.dsd.game.controller.CollisionHandlerController;
import com.dsd.game.controller.RainController;
import com.dsd.game.objects.Monster;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.MenuScreen;
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
 * @TODO: Lots of refactoring to separate private methods Create level
 * controller class which determines when the user transitions from one level to
 * the next.
 *
 * @author Joshua
 */
public class Game extends StandardGame {

    //
    //  Miscellaneous reference variables
    //
    private final StandardCamera sc;
    private final StandardCollisionHandler sch;
    private final StandardLevel level;

    //
    //  UI Element controller
    //
    private final MenuScreen menu;

    //
    //  Rain controller which contacts the API for the logic of
    //  determining whether it should rain or not.
    //
    private final RainController rainController;

    //
    //  Game state variable (paused, running, menu, etc.)
    //
    private GameState gameState = GameState.MENU;

    //
    //  Main player reference so other monsters can track them
    //
    private final Player player;

    public Game () {
        super(1280, 720, "Lock Out Protocol");

        //  Initialize the sound controller
        AudioBoxController.initialize(16);

        //  Creates the UI handler
        this.menu = new MenuScreen(this);

        //  Create a new collision handler
        this.sch = new CollisionHandlerController(null);

        //  Instantiates player & adds it to the handler
        this.player = new Player(200, 200, this, null, this.sch);
        this.sch.addEntity(player);

        //  Instantiate the camera
        this.sc = new StandardCamera(this, player, 1, this.getGameWidth(), this.getGameHeight());
        this.sch.addEntity(new Monster(900, 900, this, this.sc, this.sch));

        //  Prevents the camera from scrolling too far to any of the sides
        this.initCamera();

        //  Sets the camera for the player and the handler
        this.player.setCamera(this.sc);
        this.sch.setCamera(this.sc);

        // Instantiates the rain controller
        this.rainController = new RainController(this, this.sc, WeatherConnector.getWeather(CityLocator.getCity()));

        // Instantiates the levels @TODO: (should move this to a method and to
        // some type of controller to determine HOW levels transition)
        this.level = new ForestLevel(player);

        this.startGame();

    }

    @Override
    public void tick () {
        //
        //  Depending on the game state, update different things.
        //
        if (this.gameState == GameState.MENU) {
            this.menu.tick();
        }

        else if (this.gameState == GameState.RUNNING) {
            //  Update the level background first
            this.level.tick();
            //  Then the objects within the handler
            StandardHandler.Handler(this.sch);
            // Then the rain if applicable
            this.rainController.tick();
            //  And lastly the camera
            StandardHandler.Object(this.sc);
        }
    }

    @Override
    public void render () {
        //
        //  Depending on the game state, render different things.
        //
        if (this.gameState == GameState.MENU) {
            this.menu.render(StandardDraw.Renderer);
        }

        else if (this.gameState == GameState.RUNNING) {
            //  First things first: render the camera
            StandardDraw.Object(this.sc);
            //  Then render the level
            this.level.render(StandardDraw.Renderer);
            // Then render the rain if applicable
            this.rainController.render(StandardDraw.Renderer);
            //  Then render the handler objects
            StandardDraw.Handler(this.sch);
        }
    }

    /**
     * Sets the camera's field of view so as to prevent the camera from
     * scrolling too far to any of the sides
     */
    private void initCamera () {
        int cameraMinX = 640;//These numbers are just guess&check..
        int cameraMaxX = 1080;
        int cameraMinY = 350;
        int cameraMaxY = 720;

        this.sc.restrict(cameraMaxX, cameraMaxY, cameraMinX, cameraMinY);
    }

//========================== GETTERS =============================/
    public Player getPlayer () {
        return this.player;
    }

    public GameState getGameState () {
        return this.gameState;
    }

//========================== SETTERS =============================/
    public void setGameState (GameState _gs) {
        this.gameState = _gs;
    }

    public static void main (String[] args) {
        Game game = new Game();
    }

}
