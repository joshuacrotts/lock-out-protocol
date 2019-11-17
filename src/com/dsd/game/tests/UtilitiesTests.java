package com.dsd.game.tests;

import com.dsd.game.util.Utilities;

/**
 * This class will unit test several methods in the Utilities class.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/14/19
 */
public abstract class UtilitiesTests {

    public static void main (String[] args) {
        System.out.println(assertEquals(Utilities.clamp(5, 3, 7), 5));
        //  Edge case
        System.out.println(assertEquals(Utilities.clamp(0, 0, 0), 0));
        //  Edge case
        System.out.println(assertEquals(Utilities.clampFloat(2.2f, 2.2f, 2.2f), 2.2f));
        //  Edge case (clamping negative number to positive range).
        System.out.println(assertEquals(Utilities.clamp(-5, 0, 5), 0));
        //  Edge case (all negative numbers)
        System.out.println(assertEquals(Utilities.clamp(-50, -40, -30), -40));
        //  Edge case (clamping positive number to negative range
        System.out.println(assertEquals(Utilities.clamp(65, -5, -4), -4));
        System.out.println(assertEquals(Utilities.clampFloat(0.0f, 0.1f, 0.2f), 0.1f));
    }

    public static boolean assertEquals (int _resultA, int _resultB) {
        return _resultA == _resultB;
    }

    public static boolean assertEquals (float _resultA, float _resultB) {
        //  Using a threshold epsilon value isn't the greatest idea,
        //  but since we aren't using extremely accurate FP numbers,
        //  it'll be okay.
        float epsilonThreshold = 0.00000001f;
        return ((Math.abs(_resultA - _resultB) < epsilonThreshold)
                || (_resultA == _resultB));

    }

}
