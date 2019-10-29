package com.dsd.game.database;

import com.dsd.game.Game;
import com.dsd.game.LanguageEnum;
import com.dsd.game.ResolutionEnum;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the interactor between the settings file itself, and the
 * translator database java file.
 *
 * @author Joshua
 */
public class SettingsDatabase implements Database {

    private final Game game;
    private BufferedReader reader;
    private BufferedWriter writer;

    public SettingsDatabase (Game _game) {
        this.game = _game;
    }

    @Override
    public boolean save () {
        try {
            this.writer = new BufferedWriter(new FileWriter("settings.cfg"));
            this.writer.append("res=" + ResolutionEnum.getResolutionIndex()).append("\n");
            this.writer.append("lang=" + LanguageEnum.getLanguageIndex());
            this.writer.close();
        }
        catch (IOException ex) {
            Logger.getLogger(SettingsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    @Override
    public boolean load () {
        try {
            this.reader = new BufferedReader(new FileReader("settings.cfg"));

            //  Parse the resolution line.
            String line = this.reader.readLine();
            ResolutionEnum.setResolutionIndex(Integer.parseInt(line.substring(line.indexOf("=") + 1)));
            this.game.setGameWidth(ResolutionEnum.getDimension().width);
            this.game.setGameHeight(ResolutionEnum.getDimension().height);

            //  Parse the language line.
            line = this.reader.readLine();
            LanguageEnum.setLanguageIndex(Integer.parseInt(line.substring(line.indexOf("=") + 1)));
            this.reader.close();
        }
        catch (IOException ex) {
            Logger.getLogger(SettingsDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

}
