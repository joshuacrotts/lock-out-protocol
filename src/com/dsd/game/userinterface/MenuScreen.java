package com.dsd.game.userinterface;

import com.dsd.game.core.Game;
import com.dsd.game.commands.TabTextFieldCommand;
import com.dsd.game.userinterface.model.EmailTextFieldModel;
import com.dsd.game.userinterface.model.PasswordTextFieldModel;
import com.dsd.game.userinterface.model.buttons.AccountButton;
import com.dsd.game.userinterface.model.buttons.BackButton;
import com.dsd.game.userinterface.model.buttons.EasyButton;
import com.dsd.game.userinterface.model.buttons.ExitButton;
import com.dsd.game.userinterface.model.buttons.FemalePlayerButton;
import com.dsd.game.userinterface.model.buttons.HardButton;
import com.dsd.game.userinterface.model.buttons.HelpOrOptionsButton;
import com.dsd.game.userinterface.model.buttons.LanguageChangeButton;
import com.dsd.game.userinterface.model.buttons.LoadButton;
import com.dsd.game.userinterface.model.buttons.LoginButton;
import com.dsd.game.userinterface.model.buttons.MakeAccountButton;
import com.dsd.game.userinterface.model.buttons.MalePlayerButton;
import com.dsd.game.userinterface.model.buttons.MediumButton;
import com.dsd.game.userinterface.model.buttons.PlayButton;
import com.dsd.game.userinterface.model.buttons.ResolutionMenuButton;
import com.dsd.game.userinterface.model.buttons.VolumeControlButton;
import com.dsd.game.userinterface.model.labels.TitleLabel;
import com.dsd.game.userinterface.view.AudioView;
import com.dsd.game.userinterface.view.LanguageChangeView;
import com.dsd.game.userinterface.view.MenuView;
import com.dsd.game.userinterface.view.ResolutionView;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudio;
import com.revivedstandards.model.StandardAudioType;
import java.awt.Graphics2D;
import java.util.Stack;

/**
 * MenuScreen controller. Decides what gets rendered when on the screen for when
 * the game is in the Menu state.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/17/19
 */
public class MenuScreen extends Screen {

    //  Determines the state of the menu, and how many menus deep we're in
    //  (i.e. if the user wants to press the back button, they'll pop a
    //  state off the stack and the menu will be set to that.
    private MenuState menuState;
    private final Stack<MenuState> menuStateStack;

    //  Each view listed here is a segment of the MenuScreen as a whole.
    //  MenuScreen acts as a controller for which view will be rendered
    //  next.
    private final MenuView menuView;
    private ResolutionView changeResView;
    private LanguageChangeView changeLanguageView;
    private AudioView changeAudioView;

    public MenuScreen (Game _game) {
        super(_game);
        this.menuView = new MenuView(_game, this);
        this.menuState = MenuState.MAIN;
        this.menuStateStack = new Stack<>();
        this.menuStateStack.push(this.menuState);
        this.createUIElements();
        this.createUIScreens();
        TabTextFieldCommand tabCommand = new TabTextFieldCommand(this.getGame());
        this.playMenuMusic();
    }

    @Override
    public void tick () {
        if (this.getGame().isInGameState()) {
            return;
        }
        this.menuView.tick();
        super.tick();
        if (this.isOnResolution()) {
            this.changeResView.tick();
        }
        else if (this.isOnLanguages()) {
            this.changeLanguageView.tick();
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.getGame().isInGameState()) {
            return;
        }
        this.menuView.render(_g2);
        super.render(_g2);
        if (this.isOnResolution()) {
            this.changeResView.render(_g2);
        }
        else if (this.isOnLanguages()) {
            this.changeLanguageView.render(_g2);
        }
    }

    /**
     * Plays the music associated with the menu.
     */
    public void playMenuMusic () {
        StandardAudioController.play("src/resources/audio/music/menu.wav", StandardAudioType.MUSIC);
    }

    /**
     * Stops the music associated with the menu.
     */
    public void stopMenuMusic () {
        StandardAudioController.stop("src/resources/audio/music/menu.wav", StandardAudioType.MUSIC);
    }

    /**
     * Returns the top-most state on the stack. This is the [menu] state that is
     * set when the user presses the back button.
     *
     * @return
     */
    public MenuState popMenuStack () {
        return this.menuStateStack.pop();
    }

    /**
     * Pushes a state onto the stack. When the user goes deeper into a menu, the
     * state they were just on is pushed to the stack. In the event they want to
     * return to it, they press the back button, and it is popped off the top of
     * the stack.
     *
     * @param _state
     */
    public void pushMenuStack (MenuState _state) {
        this.menuStateStack.push(_state);
    }

    /**
     * Loads the background image for the menu.
     */
    public void loadMenuBackground () {
        this.menuView.loadBackgroundImage();
    }

    /**
     * Initializes the position of all the buttons for the user-interface when
     * the user is in the menu state.
     */
    private void createUIElements () {
        this.initializeMainMenuButtons();
        this.initializeDifficultyButtons();
        this.initializeAccountButtons();
        this.initializeOptionsButtons();
    }

    /**
     * Instantiates the buttons that are on the MainMenu screen.
     */
    private void initializeMainMenuButtons () {
        //  Instantiates the play button.
        super.addInteractor(new PlayButton(this.getGame(), this));
        //  Instantiates the exit button.
        super.addInteractor(new ExitButton(this.getGame(), this));
        //  Instantiates load button.
        super.addInteractor(new LoadButton(this.getGame(), this));
        //  Instantiates the help/options button.
        super.addInteractor(new HelpOrOptionsButton(this.getGame(), this));
        //  Instantiates the title label.
        super.addInteractor(new TitleLabel(this.getGame()));
        //  Instantiates the account label (to access the submenu).
        super.addInteractor(new AccountButton(this.getGame(), this));
        //  Instantiates the MalePlayer button.
        super.addInteractor(new MalePlayerButton(this.getGame(), this));
        //  Instantiates the FemalePlayer button.
        super.addInteractor(new FemalePlayerButton(this.getGame(), this));
    }

    /**
     * Initializes the three difficulty buttons.
     */
    private void initializeDifficultyButtons () {
        super.addInteractor(new EasyButton(this.getGame(), this));
        super.addInteractor(new MediumButton(this.getGame(), this));
        super.addInteractor(new HardButton(this.getGame(), this));
        super.addInteractor(new BackButton(this.getGame(), this));
    }

    /**
     * Initializes the sub-options menu buttons.
     */
    private void initializeOptionsButtons () {
        super.addInteractor(new ResolutionMenuButton(this.getGame(), this));
        super.addInteractor(new VolumeControlButton(this.getGame(), this));
        super.addInteractor(new LanguageChangeButton(this.getGame(), this));
    }

    /**
     * Initializes the buttons located on the Account status submenu.
     */
    private void initializeAccountButtons () {
        EmailTextFieldModel emailModel = new EmailTextFieldModel(Screen.gameHalfWidth, Screen.gameFourthHeight, this.getGame(), this);
        PasswordTextFieldModel pswdModel = new PasswordTextFieldModel(Screen.gameHalfWidth, Screen.gameHalfHeight, this.getGame(), this);
        super.addInteractor(emailModel);
        super.addInteractor(pswdModel);
        super.addInteractor(new LoginButton(this.getGame(), this, emailModel, pswdModel));
        super.addInteractor(new MakeAccountButton(this.getGame(), this, emailModel, pswdModel));
    }

    /**
     * Some models will require extra views (example: the change resolution
     * button requires a view for the different resolutions). This will
     * instantiate them.
     */
    private void createUIScreens () {
        this.changeResView = new ResolutionView(this.getGame(), this);
        this.changeLanguageView = new LanguageChangeView(this.getGame(), this);
        this.changeAudioView = new AudioView(this.getGame(), this);
    }

//====================== GETTERS ===============================//
    public boolean isOnMainMenu () {
        return this.menuState == MenuState.MAIN || this.menuStateStack.isEmpty();
    }

    public boolean isOnDifficulty () {
        return this.menuState == MenuState.DIFFICULTY;
    }

    public boolean isOnAccountScreen () {
        return this.menuState == MenuState.LOGIN;
    }

    public boolean isOnOptions () {
        return this.menuState == MenuState.OPTIONS;
    }

    public boolean isOnResolution () {
        return this.menuState == MenuState.RESOLUTION;
    }

    public boolean isOnVolume () {
        return this.menuState == MenuState.VOLUME;
    }

    public boolean isOnPlayerGender () {
        return this.menuState == MenuState.PLAYER_GENDER;
    }

    public boolean isOnLanguages () {
        return this.menuState == MenuState.LANGUAGES;
    }

    public MenuState getMenuState () {
        return this.menuState;
    }

//====================== SETTERS ===============================//
    public void setMenuState (MenuState _menuState) {
        this.menuState = _menuState;
    }
}
