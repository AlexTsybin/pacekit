package io.github.alextsybin.pacekit

import io.github.alextsybin.pacekit.model.Distance
import io.github.alextsybin.pacekit.model.Pace
import io.github.alextsybin.pacekit.model.Speed
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

/**
 * Provides stateless calculations for pace, speed,
 * and estimated finish time.
 *
 * All calculations use type-safe representations of
 * distance, pace, speed, and duration.
 *
 * Finish time calculations assume a constant pace.
 *
 * @see Distance
 * @see Pace
 * @see Speed
 * @see Duration
 */
public object PaceCalculator {

    /**
     * Calculates the average pace over a given distance.
     *
     * Fractional seconds are preserved within the
     * available numerical precision.
     *
     * @param distance The distance traveled.
     * Must be strictly positive.
     * @param duration The elapsed time.
     * Must be finite and strictly positive.
     * @return The average [Pace].
     * @throws IllegalArgumentException If the distance
     * is zero, the duration is not finite or positive,
     * or the calculated pace is invalid.
     *
     * @sample io.github.alextsybin.pacekit.samples.calculatePaceSample
     */
    public fun calculatePace(
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

    /**
     * Calculates the average speed over a given distance.
     *
     * A zero distance returns zero speed, provided that
     * the duration is finite and strictly positive.
     *
     * @param distance The distance traveled.
     * @param duration The elapsed time.
     * Must be finite and strictly positive.
     * @return The average [Speed].
     * @throws IllegalArgumentException If the duration
     * is not finite or positive, or the calculated
     * speed is not finite.
     *
     * @sample io.github.alextsybin.pacekit.samples.calculateSpeedSample
     */
    public fun calculateSpeed(
        distance: Distance,
        duration: Duration
    ): Speed {

        require(duration.isFinite() && duration > Duration.ZERO)

        val seconds = duration.toDouble(DurationUnit.SECONDS)

        return Speed.metersPerSecond(
            distance.meters / seconds
        )
    }

    /**
     * Calculates the estimated finish time for a given
     * distance at a constant pace.
     *
     * A zero distance returns [Duration.ZERO].
     *
     * Extremely large input values may produce
     * [Duration.INFINITE].
     *
     * @param distance The target distance.
     * @param pace The constant pace maintained
     * throughout the distance.
     * @return The estimated finish time.
     *
     * @sample io.github.alextsybin.pacekit.samples.calculateFinishTimeSample
     */
    public fun calculateFinishTime(
        distance: Distance,
        pace: Pace,
    ): Duration {

        return (distance.kilometers * pace.secondsPerKilometer).seconds
    }

    /**
     * Calculates the estimated finish time, including
     * additional time for stops, breaks, or delays.
     *
     * A zero [extraTime] produces the same result as
     * calculating the finish time without additional time.
     *
     * A zero distance returns the additional time.
     *
     * Extremely large input values may produce
     * [Duration.INFINITE].
     *
     * @param distance The target distance.
     * @param pace The constant moving pace.
     * @param extraTime Additional non-moving time.
     * Must be finite and non-negative.
     * @return The estimated total finish time,
     * including additional time.
     * @throws IllegalArgumentException If [extraTime]
     * is negative or not finite.
     *
     * @sample io.github.alextsybin.pacekit.samples.calculateFinishTimeWithExtraTimeSample
     */
    public fun calculateFinishTime(
        distance: Distance,
        pace: Pace,
        extraTime: Duration,
    ): Duration {

        require(extraTime.isFinite())
        require(extraTime >= Duration.ZERO)

        return calculateFinishTime(distance, pace) + extraTime
    }
}