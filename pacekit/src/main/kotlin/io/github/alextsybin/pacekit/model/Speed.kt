package io.github.alextsybin.pacekit.model

import io.github.alextsybin.pacekit.internal.MPH_TO_MPS
import io.github.alextsybin.pacekit.internal.MPS_TO_KPH

@JvmInline
value class Speed private constructor(
    val metersPerSecond: Double
) {

    init {
        require(metersPerSecond.isFinite())
        require(metersPerSecond >= 0)
    }

    val kilometersPerHour: Double
        get() = metersPerSecond * MPS_TO_KPH

    val milesPerHour: Double
        get() = metersPerSecond / MPH_TO_MPS

    companion object {

        fun metersPerSecond(value: Double): Speed = Speed(value)

        fun kilometersPerHour(value: Double): Speed = Speed(value / MPS_TO_KPH)

        fun milesPerHour(value: Double): Speed = Speed(value * MPH_TO_MPS)
    }
}