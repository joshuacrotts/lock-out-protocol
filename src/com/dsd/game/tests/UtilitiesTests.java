package com.dsd.game.tests;

import com.dsd.game.util.Utilities;

/**
 * This class will unit test several methods in the Utilities class.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/17/19
 */
public abstract class UtilitiesTests {

    public static void main (String[] args) {
        //  Edge case (all 0 integers)
        System.out.println(assertEquals(Utilities.clamp(0, 0, 0), 0));
        //  Edge case (all 0 floats)
        System.out.println(assertEquals(Utilities.clampFloat(0.0f, 0.0f, 0.0f), 0.0f));
        //  Edge case (floating point values that are all the same).
        System.out.println(assertEquals(Utilities.clampFloat(2.2f, 2.2f, 2.2f), 2.2f));
        //  Edge case (clamping negative number to positive range).
        System.out.println(assertEquals(Utilities.clamp(-5, 0, 5), 0));
        //  Edge case (all negative numbers)
        System.out.println(assertEquals(Utilities.clamp(-50, -40, -30), -40));
        //  Edge case (clamping positive number to negative range
        System.out.println(assertEquals(Utilities.clamp(65, -5, -4), -4));
        //  Normal cases.
        System.out.println(assertEquals(Utilities.clamp(5, 3, 7), 5));
        System.out.println(assertEquals(Utilities.clamp(0, 2, 10), 2));
        System.out.println(assertEquals(Utilities.clampFloat(0.0f, 0.1f, 0.2f), 0.1f));
        System.out.println(assertEquals(Utilities.clampFloat(3.254f, 3.26f, 3.27f), 3.26f));
        System.out.println(assertEquals(Utilities.clampFloat(-.01f, 5.0f, 12.0f), 5.0f));
    }

    /**
     * Returns true if the two values are equal to each other. False otherwise.
     *
     * @param _resultA
     * @param _resultB
     * @return
     */
    public static boolean assertEquals (int _resultA, int _resultB) {
        return _resultA == _resultB;
    }

    /**
     * Compares two floating point numbers and determines if they are
     * "reasonably" equivalent. This "reasonably" definition comes from the
     * epsilonThreshold defined as 0.0000000001f. If the difference between
     * resultA and resultB is less than this value, it is reasonable evidence to
     * say that they are equal.
     *
     * This also returns true if the two values are equal according to Java's
     * equality checking with FPs. This check comes first since it may evaluate
     * to true.
     *
     * @param _resultA
     * @param _resultB
     * @return
     */
    public static boolean assertEquals (float _resultA, float _resultB) {
        //  Using a threshold epsilon value isn't the greatest idea,
        //  but since we aren't using extremely accurate FP numbers,
        //  it'll be okay.
        float epsilonThreshold = 0.0000000001f;
        return (_resultA == _resultB)
                || ((Math.abs(_resultA - _resultB) < epsilonThreshold));
    }
}
