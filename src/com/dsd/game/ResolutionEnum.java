package com.dsd.game;

import java.awt.Dimension;

/**
 * This enum defines the different resolutions the user can choose when playing
 * the game.
 *
 * @author Joshua
 */
public enum ResolutionEnum {
    RES_1280_720(new Dimension(1280, 720)),
    RES_1366_768(new Dimension(1366, 768)),
    RES_1600_900(new Dimension(1600, 900)),
    RES_1680_1050(new Dimension(1680, 1050)),
    RES_1440_900(new Dimension(1440, 900)),
    RES_1920_1080(new Dimension(1920, 1080)),
    RES_2560_1080(new Dimension(2560, 1080));

    private final Dimension resolution;
    private static int resolutionIndex = 0;

    //  Array of possible resolutions; more can be added later.
    private static final ResolutionEnum[] RESOLUTION_LIST = {RES_1280_720,
        RES_1366_768,
        RES_1440_900,
        RES_1600_900,
        RES_1680_1050,
        RES_1920_1080,
        RES_2560_1080};

    /**
     * Returns a string representation of the resolution.
     *
     * @return
     */
    public static String getResolution () {
        return RESOLUTION_LIST[resolutionIndex].getDimensionString();
    }

    /**
     * Returns the actual dimension object associated with each ResolutionEnum.
     *
     * @return
     */
    public static Dimension getDimension () {
        return RESOLUTION_LIST[resolutionIndex].resolution;
    }

    /**
     * Increases the index pointer for the RESOLUTION_LIST array.
     */
    public static void increaseResolution () {
        if (resolutionIndex < RESOLUTION_LIST.length - 1) {
            resolutionIndex++;
        }
    }

    /**
     * Decreases the index pointer for the RESOLUTION_LIST array.
     */
    public static void decreaseResolution () {
        if (resolutionIndex > 0) {
            resolutionIndex--;
        }
    }

    private ResolutionEnum (Dimension _resolution) {
        this.resolution = _resolution;
    }

    /**
     * Returns a string representation of the dimension; it takes the width,
     * concatenates an x, then adds the height.
     *
     * @return
     */
    private String getDimensionString () {
        return (int) this.resolution.getWidth() + " x "
             + (int) this.resolution.getHeight();
    }
}
