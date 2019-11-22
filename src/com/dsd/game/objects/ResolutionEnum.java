package com.dsd.game.objects;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * This enum defines the different resolutions the user can choose when playing
 * the game.
 *
 * @author Joshua
 */
public enum ResolutionEnum {

    RES_1280_720(new Dimension(1280, 720)),
    RES_1366_768(new Dimension(1366, 768)),
    RES_1440_900(new Dimension(1440, 900)),
    RES_1600_900(new Dimension(1600, 900)),
    RES_1920_1080(new Dimension(1920, 1080)),
    RES_2560_1440(new Dimension(2560, 1440)),
    RES_3840_2160(new Dimension(3840, 2160));

    private final Dimension resolution;
    private static int resolutionIndex = 0;

    //  Array of possible resolutions; more can be added later.
    private static final ResolutionEnum[] RESOLUTION_LIST = {
        RES_1280_720,
        RES_1366_768,
        RES_1440_900,
        RES_1600_900,
        RES_1920_1080,
        RES_2560_1440
    };

    /**
     * Increases the index pointer for the RESOLUTION_LIST array.
     */
    public static void increaseResolution() {
        if (resolutionIndex < RESOLUTION_LIST.length - 1 && !isAtResolutionLimit()) {
            resolutionIndex++;
        }
    }

    /**
     * Decreases the index pointer for the RESOLUTION_LIST array.
     */
    public static void decreaseResolution() {
        if (resolutionIndex > 0) {
            resolutionIndex--;
        }
    }

    /**
     * Compares the resolution of the monitor to the element that the resIndex
     * pointer is on. If the resolution of the screen is SMALLER than the
     * resIndex pointer, then we don't need to advance any further because the
     * monitor can't support it.
     *
     * @return true if not supported, false otherwise.
     */
    private static boolean isAtResolutionLimit() {
        Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
        /**
         * Grabs the individual dimensions of the screen AND the one pointed at
         * by resIndex + 1 (we need to advance it by one because we'll be on the
         * current one, which is not wanted.
         */
        int screenResWidth = screenRes.width;
        int screenResHeight = screenRes.height;
        int gameResWidth = getDimension(resolutionIndex + 1).width;
        int gameResHeight = getDimension(resolutionIndex + 1).height;
        return gameResWidth > screenResWidth || gameResHeight > screenResHeight;
    }

    private ResolutionEnum(Dimension _resolution) {
        resolution = _resolution;
    }

//================================ GETTERS ==================================
    /**
     * Returns a string representation of the dimension; it takes the width,
     * concatenates an x, then adds the height.
     *
     * @return
     */
    private String getDimensionString() {
        return (int) resolution.getWidth() + "x"
                + (int) resolution.getHeight();
    }

    public static int getResolutionIndex() {
        return resolutionIndex;
    }

    /**
     * Returns a string representation of the resolution.
     *
     * @return
     */
    public static String getResolution() {
        return RESOLUTION_LIST[resolutionIndex].getDimensionString();
    }

    /**
     * Returns the actual dimension object associated with each ResolutionEnum.
     *
     * @return
     */
    public static Dimension getDimension() {
        return RESOLUTION_LIST[resolutionIndex].resolution;
    }

    /**
     * Returns the dimension object associated with the object at _index.
     *
     * @param _index
     * @return
     */
    private static Dimension getDimension(int _index) {
        return RESOLUTION_LIST[_index].resolution;
    }

//======================== SETTERS ===============================
    public static void setResolutionIndex(int _n) {
        resolutionIndex = _n;
    }

}
