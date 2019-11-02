package com.dsd.game.util;

import com.revivedstandards.util.StdOps;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TreeMap;

/**
 * This class is a utilities class for certain operations that are not specified
 * by the Standards library's StdOps class.
 *
 * @author Joshua
 */
public abstract class Utilities extends StdOps {

    private static final TreeMap<Integer, String> map = new TreeMap<>();

    /**
     * Loads all files from a particular directory. All files need to be of the
     * same type (png, jpeg, etc.) and have distinct, ordinal names.
     *
     * @param _directory
     * @param _frameCount
     * @return
     */
    public static final BufferedImage[] loadFrames (String _directory, int _frameCount) {
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
    public static final float clampFloat (float _num, float _min, float _max) {
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
    public static final float normalize (float value, float min, float max) {
        return 1 - ((value - min) / (max - min));
    }

    /**
     * Converts a number from the primitive java int type to its roman numeral
     * counterpart using a recursive treemap implementation.
     *
     * @param number
     * @return
     */
    public static final String toRoman (int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }

    static {
        Utilities.map.put(1000, "M");
        Utilities.map.put(900, "CM");
        Utilities.map.put(500, "D");
        Utilities.map.put(400, "CD");
        Utilities.map.put(100, "C");
        Utilities.map.put(90, "XC");
        Utilities.map.put(50, "L");
        Utilities.map.put(40, "XL");
        Utilities.map.put(10, "X");
        Utilities.map.put(9, "IX");
        Utilities.map.put(5, "V");
        Utilities.map.put(4, "IV");
        Utilities.map.put(1, "I");
    }
}
