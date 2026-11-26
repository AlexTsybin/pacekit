package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.METERS_PER_KILOMETER
import io.github.alextsybin.pacekit.internal.METERS_PER_MILE

@JvmInline
value class Distance private constructor(
    val meters: Double
) {

    init {
        require(meters.isFinite())
        require(meters >= 0)
    }

    val kilometers: Double
        get() = meters / METERS_PER_KILOMETER

    val miles: Double
        get() = meters / METERS_PER_MILE

    companion object {

        fun meters(value: Double): Distance = Distance(value)

        fun kilometers(value: Double): Distance = Distance(value * METERS_PER_KILOMETER)

        fun miles(value: Double): Distance = Distance(value * METERS_PER_MILE)
    }
}