package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.MPH_TO_MPS
import io.github.alextsybin.pacekit.internal.MPS_TO_KPH

/**
 * Represents a finite, non-negative speed.
 *
 * A speed can be expressed in meters per second,
 * kilometers per hour, or miles per hour.
 *
 * Zero represents the absence of movement.
 *
 * @property metersPerSecond The speed expressed
 * in meters per second.
 *
 * @see Distance
 * @see Pace
 */
@JvmInline
public value class Speed private constructor(
    public val metersPerSecond: Double
) {

    init {
        require(metersPerSecond.isFinite())
        require(metersPerSecond >= 0)
    }

    /**
     * The speed expressed in kilometers per hour.
     */
    public val kilometersPerHour: Double
        get() = metersPerSecond * MPS_TO_KPH

    /**
     * The speed expressed in miles per hour.
     */
    public val milesPerHour: Double
        get() = metersPerSecond / MPH_TO_MPS

    /**
     * Factory methods for creating speed values.
     */
    public companion object {

        /**
         * Creates a speed from meters per second.
         *
         * @param value The speed in meters per second.
         * @return The corresponding [Speed].
         * @throws IllegalArgumentException If [value] is
         * negative or not finite.
         */
        public fun metersPerSecond(value: Double): Speed = Speed(value)

        /**
         * Creates a speed from kilometers per hour.
         *
         * @param value The speed in kilometers per hour.
         * @return The corresponding [Speed].
         * @throws IllegalArgumentException If the converted
         * speed is negative or not finite.
         */
        public fun kilometersPerHour(value: Double): Speed = Speed(value / MPS_TO_KPH)

        /**
         * Creates a speed from miles per hour.
         *
         * @param value The speed in miles per hour.
         * @return The corresponding [Speed].
         * @throws IllegalArgumentException If the converted
         * speed is negative or not finite.
         */
        public fun milesPerHour(value: Double): Speed = Speed(value * MPH_TO_MPS)
    }
}