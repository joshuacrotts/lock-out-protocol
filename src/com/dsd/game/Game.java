package com.dsd.game;

import com.dsd.game.controller.AudioBoxController;
import com.dsd.game.controller.CollisionHandlerController;
import com.dsd.game.controller.CursorController;
import com.dsd.game.controller.DebugController;
import com.dsd.game.controller.DifficultyController;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.controller.LevelController;
import com.dsd.game.controller.RainController;
import com.dsd.game.controller.TimerController;
import com.dsd.game.database.TranslatorDatabase;
import com.dsd.game.levels.MetalLevel;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.HUDScreen;
import com.dsd.game.userinterface.HelpScreen;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.PauseScreen;
import com.dsd.game.userinterface.PreambleScreen;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.ShopScreen;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardDraw;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.model.StandardLevel;
import javax.swing.JOptionPane;

/**
 * This is the main class.
 *
 * @TODO: Lots of refactoring to separate private methods Create level
 * controller class which determines when the user transitions from one level to
 * the next. Serialization is also being accounted for because only a few pieces
 * of data will ever need to be saved (the Player's position, stats, inventory,
 * and level info).
 *
 * @author Joshua, Ronald, Rinty
 */
public class Game extends StandardGame {

    //  Miscellaneous reference variables
    private final StandardCollisionHandler sch;
    private final StandardCamera sc;
    //  Database references
    private final TranslatorDatabase translatorDatabase;
    //  UI Element views
    private final MenuScreen menuScreen;
    private final PauseScreen pauseScreen;
    private final PreambleScreen preambleScreen;
    private final HUDScreen hudScreen;
    private final ShopScreen shopScreen;
    private final HelpScreen helpScreen;

    /**
     * Rain controller which contacts the API for the logic of determining
     * whether it should rain or not.
     */
    private final RainController rainController;
    //  Debug controller
    private final DebugController debugController;
    //  Difficulty controller
    private final DifficultyController difficultyController;
    //  Level controller
    private final LevelController levelController;
    //  Cursor controller
    private final CursorController cursorController;
    //  Game state variable (paused, running, menu, etc.)
    private GameState gameState = GameState.MENU;
    //  Main player reference so other monsters can track them
    private Player player;

    public Game (int _width, int _height, String _title) {

        /**
         * Note: Magic numbers for the player and the monster are just for
         * demonstration; they will NOT be in the final game.
         */
        super(_width, _height, _title);

        /**
         * Initialize the database translator
         */
        this.translatorDatabase = new TranslatorDatabase(this);
        this.translatorDatabase.loadFromSettings();

        //  Initialize the sound controller
        AudioBoxController.initialize(40);

        //  Create a new collision handler
        this.sch = new CollisionHandlerController(this);
        //  Instantiates player & adds it to the handler
        this.player = new Player(200, 200, this, this.sch);
        //  Instantiate the camera
        this.sc = new StandardCamera(this, player, 1, this.getGameWidth(), this.getGameHeight());
        //  Sets the camera for the player and the handler
        this.player.setCamera(this.sc);
        this.sch.setCamera(this.sc);
        // Instantiates the rain, debug, and level controllers.
        this.rainController = new RainController(this);
        this.debugController = new DebugController(this, this.sch);
        this.difficultyController = new DifficultyController(this);
        this.levelController = new LevelController(this);
        this.cursorController = new CursorController(this);
        this.instantiateLevels();

        /**
         * Instantiates the levels @TODO: (should move this to a method and to
         * some type of controller to determine HOW levels transition) Creates
         * the UI views
         */
        this.menuScreen = new MenuScreen(this);
        this.pauseScreen = new PauseScreen(this);
        this.preambleScreen = new PreambleScreen(this);
        this.shopScreen = new ShopScreen(this);
        this.helpScreen = new HelpScreen(this);
        this.hudScreen = new HUDScreen(this, this.player, this.sch);
        this.getWindow().setWindowVisible(true);
        this.startGame();
    }

    @Override
    public void tick () {

        //  Depending on the game state, update different things.
        switch (this.gameState) {

            case MENU:
                this.menuScreen.tick();
                break;
            case PAUSED:
                this.pauseScreen.tick();
                break;
            case HELP:
                this.helpScreen.tick();
                break;
            case SHOP:
                this.shopScreen.tick();
                break;
            case PREAMBLE:
                //  Update the preamble text/effect
                this.preambleScreen.tick();
            case RUNNING:
                //  Update the level background first
                this.levelController.tickLevel();
                //  Update the handler with the casings
                StandardHandler.Handler(this.player.getCasingHandler());
                //  Then the objects within the handler
                StandardHandler.Handler(this.sch);
                //  Then the rain if applicable
                this.rainController.tick();
                //  Then the heads up display
                this.hudScreen.tick();
                //  And lastly the camera
                StandardHandler.Object(this.sc);
                break;
        }

        this.cursorController.tick();
    }

    @Override
    public void render () {

        //  Depending on the game state, render different things.
        if (this.gameState == GameState.MENU) {

            this.menuScreen.render(StandardDraw.Renderer);
        }
        else {

            //  First things first: render the camera
            StandardDraw.Object(this.sc);
            //  Then render the current [active] level
            this.levelController.renderLevel(StandardDraw.Renderer);
            //  Then render the handler with the casings.
            StandardDraw.Handler(this.player.getCasingHandler());
            //  Then render the handler objects
            StandardDraw.Handler(this.sch);
            //  Then render the rain if applicable
            this.rainController.render(StandardDraw.Renderer);
            //  Then render the heads up display
            this.hudScreen.render(StandardDraw.Renderer);
            //  Then render the preamble, pause or shop effect if necessary
            switch (this.gameState) {

                case PREAMBLE:
                    this.preambleScreen.render(StandardDraw.Renderer);
                    break;
                case SHOP:
                    this.shopScreen.render(StandardDraw.Renderer);
                    break;
                case PAUSED:
                    this.pauseScreen.render(StandardDraw.Renderer);
                    break;
                case HELP:
                    this.helpScreen.render(StandardDraw.Renderer);
                    break;
            }
            //  If we are in debug mode, we can draw the text.
            if (DebugController.DEBUG_MODE) {

                this.debugController.render(StandardDraw.Renderer);
            }

        }

    }

    /**
     * Once the game turns to the PLAY state, this method is called. It will
     * instantiate the Spawner controllers, level controllers, etc.
     */
    public void uponPlay () {

        DifficultyController.setDifficultyFactor();
        DifficultyController.setLevelTransitionTimer();
        this.levelController.getCurrentLevel().loadLevelData();
        this.levelController.startWaveTimer();
        this.menuScreen.stopMenuMusic();
    }

    /**
     * Plays the wave change sfx.
     */
    public void playWaveChangeSFX () {

        StandardAudioController.play("src/resources/audio/sfx/round_change.wav", StandardAudioType.SFX);
    }

    /**
     * Changes the resolution of the game window. The user cannot set the
     * resolution higher than their monitor's current resolution. Once the
     * resolution of the JFrame window changes, the Screen variables will
     * update, and all components relying on these variables will update
     * themselves accordingly.
     *
     * @param _width
     * @param _height
     */
    public void changeResolution (int _width, int _height) {

        this.setGameWidth(_width);
        this.setGameHeight(_height);
        Screen.setGameDimensions();
        this.reinstantiateCamera();
        this.menuScreen.loadMenuBackground();
    }

    /**
     * Resets the game to its original state. Clears all entities from the
     * handler, removes all levels from the level controller, resets the player
     * state and its variables (health, money, etc), then proceeds to
     * reinstantiate the levels (so the timers of spawnercontrollers are
     * likewise reset), cancel all timers, and finally resets the difficulty
     * factors from the previous game's progression.
     */
    public void resetGame () {

        this.sch.clearEntities();
        this.levelController.clearLevels();
        this.player.resetPlayer();
        this.player.getInventory().resetInventory();
        this.instantiateLevels();

        TimerController.stopTimers();
        DifficultyController.resetDifficultyFactors();
    }

    /**
     * Calls the translator DB class to save the game's current state.
     *
     * @return
     */
    public boolean saveToDatabase () {

        if (!this.translatorDatabase.saveToDatabase()) {

            JOptionPane.showMessageDialog(null, LanguageController.translate("Unable to save data."));
            return false;
        }

        return true;
    }

    /**
     * Calls the translator DB class to load a previously-saved file.ß
     *
     * @return
     */
    public boolean loadFromDatabase () {

        if (!this.translatorDatabase.loadFromDatabase()) {

            JOptionPane.showMessageDialog(null, LanguageController.translate("Unable to load data, did you log in?."));
            return false;
        }

        return true;
    }

    /**
     * Calls the translator DB class to save the language and resolution
     * preferences to a settings file.
     */
    public void saveToSettings () {

        if (!this.translatorDatabase.saveToSettings()) {

            JOptionPane.showMessageDialog(null, LanguageController.translate("Unable to save to the settings file."));
        }

    }

    /**
     * Calls the translator DB class to load the language and resolution
     * preferences from a settings file.
     */
    public void loadFromSettings () {

        if (!this.translatorDatabase.saveToSettings()) {

            JOptionPane.showMessageDialog(null, LanguageController.translate("Unable to load from the settings file."));
        }

    }

    /**
     * Loads the level data when the game starts so the timers can be
     * instantiated.
     */
    private void instantiateLevels () {

        this.levelController.addLevel(new MetalLevel(this.player, this, this.sch));
    }

    /**
     * Resets the camera's viewport to account for a resized window.
     */
    private void reinstantiateCamera () {

        this.sc.setVpw(this.getGameWidth() >> 1);
        this.sc.setVph(this.getGameHeight() >> 1);
    }

//========================== GETTERS =============================
    public Player getPlayer () {

        return this.player;
    }

    public GameState getGameState () {

        return this.gameState;
    }

    public StandardCamera getCamera () {

        return this.sc;
    }

    public StandardCollisionHandler getHandler () {

        return this.sch;
    }

    public MenuScreen getMenuScreen () {

        return this.menuScreen;
    }

    public HUDScreen getHUDScreen () {

        return this.hudScreen;
    }

    public LevelController getLevelController () {

        return this.levelController;
    }

    public DifficultyController getDifficultyController () {

        return this.difficultyController;
    }

    public StandardLevel getCurrentLevel () {

        return this.levelController.getCurrentLevel();
    }

    public int getCurrentLevelID () {

        return this.levelController.getCurrentLevelID();
    }

    public int getLogicalCurrentLevelID () {

        return this.levelController.getLogicalCurrentLevelID();
    }

    public int getWaveNumber () {

        return this.levelController.getWaveNumber();
    }

    public boolean isPaused () {

        return this.gameState == GameState.PAUSED;
    }

    public boolean isPreamble () {

        return this.gameState == GameState.PREAMBLE;
    }

    public boolean isRunning () {

        return this.gameState == GameState.RUNNING;
    }

    public boolean isInGameState () {

        return this.isRunning() | this.isPreamble();
    }

    public boolean isShop () {

        return this.gameState == GameState.SHOP;
    }

    public boolean isMenu () {

        return this.gameState == GameState.MENU;
    }

    public boolean isHelp () {

        return this.gameState == GameState.HELP;
    }

//========================== SETTERS =============================
    public void setGameState (GameState _gs) {

        this.gameState = _gs;
    }

    public void setPlayer (Player _player) {

        this.player = _player;
        this.player.setCamera(sc);
        this.player.setHandler(this.sch);
    }

    /**
     * Sets the game to the preamble state and reset the alpha transparency of
     * it.
     */
    public void setPreambleState () {

        this.gameState = GameState.PREAMBLE;
        this.playWaveChangeSFX();
        this.preambleScreen.resetPreambleScreen();
    }

}
