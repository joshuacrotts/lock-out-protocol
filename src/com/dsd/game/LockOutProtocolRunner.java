package com.dsd.game;

import com.dsd.game.database.PersistentDatabase;

/**
 * This is the main class for actually creating a Game object and running the
 * game.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class LockOutProtocolRunner {

    public static void main (String[] args) {
        //Game game = new Game(1280, 720, "Lock Out Protocol");
        PersistentDatabase db = new PersistentDatabase("_file.txt");
        db.connect();
    }
}
