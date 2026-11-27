package io.github.alextsybin.pacekit.samples

import io.github.alextsybin.pacekit.PaceCalculator
import io.github.alextsybin.pacekit.model.Distance
import io.github.alextsybin.pacekit.model.Pace
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun calculatePaceSample() {

    val distance = Distance.kilometers(10.0)
    val duration = 35.minutes

    val pace = PaceCalculator.calculatePace(distance, duration)

    println(pace.secondsPerKilometer) // 210.0
    println(pace.minutesPerKilometer) // 3.5
}

fun calculateSpeedSample() {

    val distance = Distance.kilometers(1.0)
    val duration = 200.seconds

    val speed = PaceCalculator.calculateSpeed(distance, duration)

    println(speed.metersPerSecond)   // 5.0
    println(speed.kilometersPerHour) // 18.0
    println(speed.milesPerHour)      // Approximately 11.18
}

fun calculateFinishTimeSample() {

    val distance = Distance.kilometers(10.0)
    val pace = Pace.minutesPerKilometer(3.5)

    val duration = PaceCalculator.calculateFinishTime(
        distance = distance,
        pace = pace
    )

    println(duration)                // 35m
    println(duration.inWholeSeconds) // 2100
    println(duration.inWholeMinutes) // 35
}

fun calculateFinishTimeWithExtraTimeSample() {

    val distance = Distance.kilometers(10.0)
    val pace = Pace.secondsPerKilometer(210.0)
    val extraTime = 2.minutes

    val duration = PaceCalculator.calculateFinishTime(
        distance = distance,
        pace = pace,
        extraTime = extraTime
    )

    println(duration.toDouble(DurationUnit.MINUTES)) // 37.0
}