package com.dsd.game;

import com.dsd.game.api.CityLocator;
import com.dsd.game.api.WeatherConnector;
import com.dsd.game.controller.AudioBoxController;
import com.dsd.game.controller.CollisionHandlerController;
import com.dsd.game.controller.RainController;
import com.dsd.game.objects.Monster;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.HUDScreen;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.PauseScreen;
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
    //  UI Element views
    //
    private final MenuScreen menuScreen;
    private final PauseScreen pauseScreen;
    private final HUDScreen hudScreen;

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

    public Game (int _width, int _height, String _title) {
        //
        //  Note: Magic numbers for the player and the monster are just
        //        for demonstration; they will NOT be in the final game.
        //
        super(_width, _height, _title);

        //  Initialize the sound controller
        AudioBoxController.initialize(16);

        //  Create a new collision handler
        this.sch = new CollisionHandlerController(null);

        //  Instantiates player & adds it to the handler
        this.player = new Player(200, 200, this, null, this.sch);
        this.sch.addEntity(player);

        //  Instantiate the camera
        this.sc = new StandardCamera(this, player, 1, this.getGameWidth(), this.getGameHeight());
        this.sch.addEntity(new Monster(900, 900, this, this.sc, this.sch));

        //  Sets the camera for the player and the handler
        this.player.setCamera(this.sc);
        this.sch.setCamera(this.sc);

        // Instantiates the rain controller
        this.rainController = new RainController(this, this.sc, WeatherConnector.getWeather(CityLocator.getCity()));

        // Instantiates the levels @TODO: (should move this to a method and to
        // some type of controller to determine HOW levels transition)
        this.level = new ForestLevel(this.player, this, this.sc);

        //  Creates the UI views
        this.menuScreen = new MenuScreen(this);
        this.pauseScreen = new PauseScreen(this);
        this.hudScreen = new HUDScreen(this, this.player);

        this.startGame();
    }

    @Override
    public void tick () {
        //
        //  Depending on the game state, update different things.
        //
        switch (this.gameState) {
            case MENU:
                this.menuScreen.tick();
                break;
            case PAUSED:
                this.pauseScreen.tick();
                break;
            case RUNNING:
                //  Update the level background first
                this.level.tick();
                //  Then the objects within the handler
                StandardHandler.Handler(this.sch);
                //  Then the rain if applicable
                this.rainController.tick();
                //  Then the heads up display
                this.hudScreen.tick();
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
            this.menuScreen.render(StandardDraw.Renderer);
        }
        else {

            //  First things first: render the camera
            StandardDraw.Object(this.sc);
            //  Then render the level
            this.level.render(StandardDraw.Renderer);
            // Then render the rain if applicable
            this.rainController.render(StandardDraw.Renderer);
            //  Then render the handler objects
            StandardDraw.Handler(this.sch);
            //  Then render the heads up display
            this.hudScreen.render(StandardDraw.Renderer);

            //  If the game is paused, draw the paused text and
            //  transparent background.
            if (this.gameState == GameState.PAUSED) {
                this.pauseScreen.render(StandardDraw.Renderer);
            }

        }
    }

//========================== GETTERS =============================/
    public Player getPlayer () {
        return this.player;
    }

    public GameState getGameState () {
        return this.gameState;
    }

    public StandardCamera getCamera () {
        return this.sc;
    }

//========================== SETTERS =============================/
    public void setGameState (GameState _gs) {
        this.gameState = _gs;
    }

    public static void main (String[] args) {
        Game game = new Game(1280, 720, "Lock Out Protocol");
    }

}
