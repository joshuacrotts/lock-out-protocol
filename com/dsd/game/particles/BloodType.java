package com.dsd.game.particles;

/**
 * This class represents the several different types of blood particles that are
 * spawned by the BloodParticleHandler. We created this class to vastly improve
 * readability and flexibility of the Enemy class by delegating the control of
 * the spawning SOLELY to the handler.
 *
 * @group [Data Structure Deadheads]
 *
 * @author Joshua, Rinty, Ronald
 *
 * @updated 12/3/19
 */
public enum BloodType {
    SLOWING,
    SHRINKING,
    SCATTERED,
    STANDARD,
}
