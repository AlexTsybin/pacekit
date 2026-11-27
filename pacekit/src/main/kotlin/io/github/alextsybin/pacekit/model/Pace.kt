package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.KILOMETERS_PER_MILE
import io.github.alextsybin.pacekit.internal.SECONDS_PER_MINUTE

/**
 * Represents the time required to cover a unit of distance.
 *
 * A pace must be finite and strictly positive.
 * A lower pace value represents faster movement.
 *
 * Fractional values are expressed as decimal numbers.
 * For example, 3.5 minutes per kilometer corresponds
 * to 3 minutes and 30 seconds per kilometer.
 *
 * @property secondsPerKilometer The pace expressed
 * in seconds per kilometer.
 *
 * @see Distance
 * @see Speed
 */
@JvmInline
public value class Pace private constructor(
    public val secondsPerKilometer: Double
) {

    init {
        require(secondsPerKilometer.isFinite())
        require(secondsPerKilometer > 0)
    }

    /**
     * The pace expressed in decimal minutes per kilometer.
     */
    public val minutesPerKilometer: Double
        get() = secondsPerKilometer / SECONDS_PER_MINUTE

    /**
     * The pace expressed in seconds per international mile.
     */
    public val secondsPerMile: Double
        get() = secondsPerKilometer * KILOMETERS_PER_MILE

    /**
     * Factory methods for creating pace values.
     */
    public companion object {

        /**
         * Creates a pace from seconds per kilometer.
         *
         * @param value The pace in seconds per kilometer.
         * @return The corresponding [Pace].
         * @throws IllegalArgumentException If [value] is
         * not finite or is less than or equal to zero.
         */
        public fun secondsPerKilometer(value: Double): Pace = Pace(value)

        /**
         * Creates a pace from decimal minutes per kilometer.
         *
         * For example, a value of 4.5 represents
         * 4 minutes and 30 seconds per kilometer.
         *
         * @param value The pace in minutes per kilometer.
         * @return The corresponding [Pace].
         * @throws IllegalArgumentException If the converted
         * pace is not finite or is less than or equal to zero.
         */
        public fun minutesPerKilometer(value: Double): Pace = Pace(value * SECONDS_PER_MINUTE)

        /**
         * Creates a pace from seconds per international mile.
         *
         * @param value The pace in seconds per mile.
         * @return The corresponding [Pace].
         * @throws IllegalArgumentException If the converted
         * pace is not finite or is less than or equal to zero.
         */
        public fun secondsPerMile(value: Double): Pace = Pace(value / KILOMETERS_PER_MILE)
    }
}