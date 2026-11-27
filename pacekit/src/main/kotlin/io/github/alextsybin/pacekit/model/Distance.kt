package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.METERS_PER_KILOMETER
import io.github.alextsybin.pacekit.internal.METERS_PER_MILE

@JvmInline
public value class Distance private constructor(
    public val meters: Double
) {

    init {
        require(meters.isFinite())
        require(meters >= 0)
    }

    public val kilometers: Double
        get() = meters / METERS_PER_KILOMETER

    public val miles: Double
        get() = meters / METERS_PER_MILE

    public companion object {

        public fun meters(value: Double): Distance = Distance(value)

        public fun kilometers(value: Double): Distance = Distance(value * METERS_PER_KILOMETER)

        public fun miles(value: Double): Distance = Distance(value * METERS_PER_MILE)
    }
}