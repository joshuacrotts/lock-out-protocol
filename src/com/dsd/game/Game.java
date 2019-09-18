package com.dsd.game;

import com.revivedstandards.main.StandardGame;

/**
 * This is the main class.
 *
 * @author Joshua
 */
public class Game extends StandardGame {

    public Game () {
        super(1280, 720, "CSC-340 Game");

        this.StartGame();
    }

    @Override
    public void tick () {

    }

    @Override
    public void render () {

    }

    public static void main (String[] args) {
        Game game = new Game();
    }

}
