package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.LanguageEnum;
import com.revivedstandards.commands.Command;
import java.awt.event.KeyEvent;

/**
 * Command representing when the user presses the left arrow to increase the
 * game's screen resolution.
 *
 * @author Joshua, Ronald, Rinty
 */
public class DecreaseLanguageCommand extends Command {

    //  Miscellaneous reference variables.
    private final Game game;

    public DecreaseLanguageCommand (Game _game) {

        this.game = _game;
        this.bind(this.game.getKeyboard(), KeyEvent.VK_LEFT);
    }

    @Override
    public void pressed (float _dt) {

        if (!this.game.isMenu() || !this.game.getMenuScreen().isOnLanguages()) {

            return;
        }

        LanguageEnum.decreaseLanguage();
    }

}
