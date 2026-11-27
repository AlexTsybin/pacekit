package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.KILOMETERS_PER_MILE
import io.github.alextsybin.pacekit.internal.SECONDS_PER_MINUTE

@JvmInline
public value class Pace private constructor(
    public val secondsPerKilometer: Double
) {

    init {
        require(secondsPerKilometer.isFinite())
        require(secondsPerKilometer > 0)
    }

    public val minutesPerKilometer: Double
        get() = secondsPerKilometer / SECONDS_PER_MINUTE

    public val secondsPerMile: Double
        get() = secondsPerKilometer * KILOMETERS_PER_MILE

    public companion object {

        public fun secondsPerKilometer(value: Double): Pace = Pace(value)

        public fun minutesPerKilometer(value: Double): Pace = Pace(value * SECONDS_PER_MINUTE)

        public fun secondsPerMile(value: Double): Pace = Pace(value / KILOMETERS_PER_MILE)
    }
}