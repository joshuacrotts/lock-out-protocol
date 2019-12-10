package com.dsd.game.util;

import com.revivedstandards.util.StdOps;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.TreeMap;

/**
 * This class is a utilities class for certain operations that are not specified
 * by the Standards library's StdOps class.
 *
 * [Group Name: Data Structure Deadheads]
 * 
 * @author Joshua Last Updated: 12/10/2019
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
    public static final BufferedImage[] loadFrames(String _directory, int _frameCount) {
        File folder = new File(_directory);
        File[] listOfFiles = folder.listFiles();
        BufferedImage[] frames = new BufferedImage[_frameCount];
        for (int i = 0; i < frames.length; i++) {
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
    public static final float clampFloat(float _num, float _min, float _max) {
        if (_min > _max) {
            throw new IllegalArgumentException("Your min clamp value cannot be greater than your max.");
        } else if (_num < _min) {
            return _min;
        } else if (_num > _max) {
            return _max;
        }
        return _num;
    }

    /**
     * Calculates a _value between 0 and 1, given the precondition that _value is
 between _min and _max. 0 means _value = _max, and 1 means _value = _min.

 SRC:
 https://stackoverflow.com/questions/45841201/normalize-a-given-random-_value-to-the-range-of-0-1-in-java
     *
     * @param _value
     * @param _min
     * @param _max
     * @return
     */
    public static final float normalize(float _value, float _min, float _max) {
        return 1.0f - ((_value - _min) / (_max - _min));
    }

    /**
     * Converts a _number from the primitive java int type to its roman numeral
 counterpart using a recursive treemap implementation.
     *
     * @param _number
     * @return
     */
    public static final String toRoman(int _number) {
        int l = map.floorKey(_number);
        if (_number == l) {
            return map.get(_number);
        }
        return map.get(l) + toRoman(_number - l);
    }

    public static float normalize(float _n, float _rMin, float _rMax, float _scaleMin, float _scaleMax) {
        return (((_n - _rMin) / (_rMax - _rMin)) * (_scaleMax - _scaleMin)) + _scaleMin;
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
