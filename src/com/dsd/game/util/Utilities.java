package com.dsd.game.util;

import com.revivedstandards.util.StdOps;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * This class is a utilities class for certain operations that are not specified
 * by the Standards library's StdOps class.
 *
 * @author Joshua
 */
public abstract class Utilities extends StdOps {

    /**
     * Loads all files from a particular directory. All files need to be of the
     * same type (png, jpeg, etc.) and have distinct, ordinal names.
     *
     * @param _directory
     * @param _frameCount
     * @return
     */
    public static BufferedImage[] loadFrames(String _directory, int _frameCount) {
        File folder = new File(_directory);
        File[] listOfFiles = folder.listFiles();
        BufferedImage[] frames = new BufferedImage[_frameCount];

        for (int i = 0; i < frames.length; i++) {
            frames[i] = StdOps.loadImage(listOfFiles[i].getPath());
        }
        return frames;
    }
}
