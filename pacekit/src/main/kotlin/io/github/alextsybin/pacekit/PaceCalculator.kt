package io.github.alextsybin.pacekit

import io.github.alextsybin.pacekit.model.Distance
import io.github.alextsybin.pacekit.model.Pace
import io.github.alextsybin.pacekit.model.Speed
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

object PaceCalculator {

    fun calculatePace(
        distance: Distance,
        duration: Duration
    ): Pace {

        require(distance.meters > 0)
        require(duration.isFinite() && duration > Duration.ZERO)

        val seconds = duration.toDouble(DurationUnit.SECONDS)

        return Pace.secondsPerKilometer(
            seconds / distance.kilometers
        )
    }

    fun calculateSpeed(
        distance: Distance,
        duration: Duration
    ): Speed {

        require(duration.isFinite() && duration > Duration.ZERO)

        val seconds = duration.toDouble(DurationUnit.SECONDS)

        return Speed.metersPerSecond(
            distance.meters / seconds
        )
    }

    fun calculateFinishTime(
        distance: Distance,
        pace: Pace
    ): Duration {

        return (distance.kilometers * pace.secondsPerKilometer).seconds
    }
}