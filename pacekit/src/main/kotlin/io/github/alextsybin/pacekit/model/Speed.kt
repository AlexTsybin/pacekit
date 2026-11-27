package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.MPH_TO_MPS
import io.github.alextsybin.pacekit.internal.MPS_TO_KPH

@JvmInline
public value class Speed private constructor(
    public val metersPerSecond: Double
) {

    init {
        require(metersPerSecond.isFinite())
        require(metersPerSecond >= 0)
    }

    public val kilometersPerHour: Double
        get() = metersPerSecond * MPS_TO_KPH

    public val milesPerHour: Double
        get() = metersPerSecond / MPH_TO_MPS

    public companion object {

        public fun metersPerSecond(value: Double): Speed = Speed(value)

        public fun kilometersPerHour(value: Double): Speed = Speed(value / MPS_TO_KPH)

        public fun milesPerHour(value: Double): Speed = Speed(value * MPH_TO_MPS)
    }
}