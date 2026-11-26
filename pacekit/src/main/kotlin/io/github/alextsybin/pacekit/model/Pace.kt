package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.KILOMETERS_PER_MILE
import io.github.alextsybin.pacekit.internal.SECONDS_PER_MINUTE

@JvmInline
value class Pace private constructor(
    val secondsPerKilometer: Double
) {

    init {
        require(secondsPerKilometer.isFinite())
        require(secondsPerKilometer > 0)
    }

    val minutesPerKilometer: Double
        get() = secondsPerKilometer / SECONDS_PER_MINUTE

    val secondsPerMile: Double
        get() = secondsPerKilometer * KILOMETERS_PER_MILE

    companion object {

        fun secondsPerKilometer(value: Double): Pace = Pace(value)

        fun minutesPerKilometer(value: Double): Pace = Pace(value * SECONDS_PER_MINUTE)

        fun secondsPerMile(value: Double): Pace = Pace(value / KILOMETERS_PER_MILE)
    }
}