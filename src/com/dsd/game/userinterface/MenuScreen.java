package com.dsd.game.userinterface;

import com.dsd.game.userinterface.model.buttons.PlayButton;
import com.dsd.game.Game;
import com.dsd.game.userinterface.model.buttons.AccountButton;
import com.dsd.game.userinterface.model.buttons.BackButton;
import com.dsd.game.userinterface.model.buttons.EasyButton;
import com.dsd.game.userinterface.model.EmailTextFieldModel;
import com.dsd.game.userinterface.model.buttons.ExitButton;
import com.dsd.game.userinterface.model.buttons.HardButton;
import com.dsd.game.userinterface.model.buttons.HelpOrOptionsButton;
import com.dsd.game.userinterface.model.buttons.LoginButton;
import com.dsd.game.userinterface.model.buttons.MakeAccountButton;
import com.dsd.game.userinterface.model.buttons.MediumButton;
import com.dsd.game.userinterface.model.PasswordTextFieldModel;
import com.dsd.game.userinterface.model.buttons.ResolutionMenuButton;
import com.dsd.game.userinterface.model.labels.TitleLabel;
import com.dsd.game.userinterface.view.MenuView;
import com.dsd.game.userinterface.view.ResolutionView;
import com.revivedstandards.controller.StandardAudioController;
import java.awt.Graphics2D;
import java.util.Stack;

/**
 * MenuScreen controller. Decides what gets rendered when on the screen for when
 * the game is in the Menu state.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class MenuScreen extends Screen {

    private MenuState menuState;
    private final Stack<MenuState> menuStateStack;

    private MenuView menuView;
    private ResolutionView changeResView;

    public MenuScreen (Game _game) {
        super(_game);
        this.menuView = new MenuView(_game, this);
        this.menuState = MenuState.MAIN;
        this.menuStateStack = new Stack<>();
        this.menuStateStack.push(this.menuState);
        this.createUIElements();
        this.createUIScreens();
    }

    @Override
    public void tick () {
        this.menuView.tick();

        super.tick();

        this.changeResView.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        this.menuView.render(_g2);

        super.render(_g2);

        this.changeResView.render(_g2);
    }

    public void stopMenuMusic () {
        StandardAudioController.stop("src/resources/audio/music/menu.wav");
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
        //  Instantiates the play button
        super.addInteractor(new PlayButton(this.getGame(), this));
        //  Instantiates the exit button
        super.addInteractor(new ExitButton(this.getGame(), this));
        //  Instantiates the help/options button
        super.addInteractor(new HelpOrOptionsButton(this.getGame(), this));
        //  Instantiates the title label
        super.addInteractor(new TitleLabel(this.getGame()));
        //  Instantiates the account label (to access the submenu).
        super.addInteractor(new AccountButton(this.getGame(), this));
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

    private void initializeOptionsButtons () {
        super.addInteractor(new ResolutionMenuButton(this.getGame(), this));
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
    }

    public MenuState popMenuStack () {
        return this.menuStateStack.pop();
    }

    public void pushMenuStack (MenuState _state) {
        this.menuStateStack.push(_state);
    }

//====================== GETTERS ===============================//
    public boolean isOnMainMenu () {
        return this.menuState == MenuState.MAIN;
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

//====================== SETTERS ===============================//
    public void setMenuState (MenuState _menuState) {
        this.menuState = _menuState;
    }
}
