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
    public static BufferedImage[] loadFrames (String _directory, int _frameCount) {
        File folder = new File(_directory);
        File[] listOfFiles = folder.listFiles();
        BufferedImage[] frames = new BufferedImage[_frameCount];
        for (int i = 0 ; i < frames.length ; i++) {
            frames[i] = StdOps.loadImage(listOfFiles[i].getPath());
        }
        return frames;
    }

    /**
     * Clamps a number to a specific range.
     *
     * @param _num
     * @param _min
     * @param _max
     * @return
     */
    public static float clampFloat (float _num, float _min, float _max) {
        if (_num < _min) {
            return _min;
        }
        else if (_num > _max) {
            return _max;
        }
        return _num;
    }

    /**
     * Calculates a value between 0 and 1, given the precondition that value is
     * between min and max. 0 means value = max, and 1 means value = min.
     *
     * SRC:
     * https://stackoverflow.com/questions/45841201/normalize-a-given-random-value-to-the-range-of-0-1-in-java
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static float normalize (float value, float min, float max) {
        return 1 - ((value - min) / (max - min));
    }
}
