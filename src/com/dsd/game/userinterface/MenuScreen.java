package com.dsd.game.userinterface;

import com.dsd.game.userinterface.model.PlayButton;
import com.dsd.game.Game;
import com.dsd.game.userinterface.model.AccountButton;
import com.dsd.game.userinterface.model.BackButton;
import com.dsd.game.userinterface.model.EasyButton;
import com.dsd.game.userinterface.model.EmailTextFieldModel;
import com.dsd.game.userinterface.model.ExitButton;
import com.dsd.game.userinterface.model.HardButton;
import com.dsd.game.userinterface.model.HelpOrOptionsButton;
import com.dsd.game.userinterface.model.LoginButton;
import com.dsd.game.userinterface.model.MakeAccountButton;
import com.dsd.game.userinterface.model.MediumButton;
import com.dsd.game.userinterface.model.PasswordTextFieldModel;
import com.dsd.game.userinterface.model.StandardLabel;
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

    public MenuScreen (Game _game) {
        super(_game);
        this.menuState = MenuState.MAIN;
        this.menuStateStack = new Stack<>();
        this.menuStateStack.push(this.menuState);
        this.createUIElements();
    }

    @Override
    public void tick () {
        super.tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        super.render(_g2);
    }

    /**
     * Initializes the position of all the buttons for the user-interface when
     * the user is in the menu state.
     */
    private void createUIElements () {
        this.initializeMainMenuButtons();
        this.initializeDifficultyButtons();
        this.initializeAccountButtons();
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
        super.addInteractor(new StandardLabel(Screen.gameHalfWidth - 130, 40, this.getGame().getWindow().getTitle(), "src/resources/fonts/chargen.ttf", 32f));
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

//====================== SETTERS ===============================//
    public void setMenuState (MenuState _menuState) {
        this.menuState = _menuState;
    }
}
