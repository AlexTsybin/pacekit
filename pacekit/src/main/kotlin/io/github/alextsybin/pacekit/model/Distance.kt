package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.METERS_PER_KILOMETER
import io.github.alextsybin.pacekit.internal.METERS_PER_MILE

/**
 * Represents a finite, non-negative distance.
 *
 * The distance is internally stored in meters and can be
 * accessed in meters, kilometers, or miles.
 * Zero is a valid distance.
 *
 * Use [Distance.meters], [Distance.kilometers], or
 * [Distance.miles] to create an instance.
 *
 * @property meters The distance expressed in meters.
 *
 * @see Pace
 * @see Speed
 */
@JvmInline
public value class Distance private constructor(
    public val meters: Double
) {

    init {
        require(meters.isFinite())
        require(meters >= 0)
    }

    /**
     * The distance expressed in kilometers.
     */
    public val kilometers: Double
        get() = meters / METERS_PER_KILOMETER

    /**
     * The distance expressed in international miles.
     */
    public val miles: Double
        get() = meters / METERS_PER_MILE

    /**
     * Factory methods for creating distance values.
     */
    public companion object {

        /**
         * Creates a distance from meters.
         *
         * @param value The distance in meters.
         * @return The corresponding [Distance].
         * @throws IllegalArgumentException If [value] is
         * negative or not finite.
         */
        public fun meters(value: Double): Distance = Distance(value)

        /**
         * Creates a distance from kilometers.
         *
         * @param value The distance in kilometers.
         * @return The corresponding [Distance].
         * @throws IllegalArgumentException If [value] is
         * negative or not finite, or the conversion overflows.
         */
        public fun kilometers(value: Double): Distance = Distance(value * METERS_PER_KILOMETER)

        /**
         * Creates a distance from international miles.
         *
         * @param value The distance in miles.
         * @return The corresponding [Distance].
         * @throws IllegalArgumentException If [value] is
         * negative or not finite, or the conversion overflows.
         */
        public fun miles(value: Double): Distance = Distance(value * METERS_PER_MILE)
    }
}