package io.github.alextsybin.pacekit

import io.github.alextsybin.pacekit.model.Distance
import io.github.alextsybin.pacekit.model.Pace
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class PaceCalculatorTest {

    companion object {
        private const val TOLERANCE = 1e-9
    }

    @Test
    fun `pace is correctly calculated`() {

        val distance = Distance.kilometers(10.0)
        val duration = 35.minutes

        val pace = PaceCalculator.calculatePace(
            distance = distance,
            duration = duration
        )

        assertEquals(
            expected = 210.0,
            actual = pace.secondsPerKilometer,
            absoluteTolerance = TOLERANCE
        )
    }

    @Test
    fun `pace with fraction is correctly calculated`() {

        val distance = Distance.meters(400.0)
        val duration = 68.5.seconds

        val pace = PaceCalculator.calculatePace(
            distance = distance,
            duration = duration
        )

        assertEquals(
            expected = 171.25,
            actual = pace.secondsPerKilometer,
            absoluteTolerance = TOLERANCE
        )
    }

    @Test
    fun `zero distance is rejected`() {

        val distance = Distance.meters(0.0)
        val duration = 35.minutes

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculatePace(
                distance = distance,
                duration = duration
            )
        }
    }

    @Test
    fun `pace calculation rejects zero duration`() {

        val distance = Distance.kilometers(10.0)
        val duration = 0.seconds

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculatePace(
                distance = distance,
                duration = duration
            )
        }
    }

    @Test
    fun `pace calculation rejects negative duration`() {

        val distance = Distance.kilometers(10.0)
        val duration = (-1).seconds

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculatePace(
                distance = distance,
                duration = duration
            )
        }
    }

    @Test
    fun `pace calculation rejects positive infinite duration`() {

        val distance = Distance.kilometers(10.0)
        val duration = Duration.INFINITE

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculatePace(
                distance = distance,
                duration = duration
            )
        }
    }

    @Test
    fun `speed is correctly calculated`() {

        val distance = Distance.kilometers(1.0)
        val duration = 200.seconds

        val speed = PaceCalculator.calculateSpeed(
            distance = distance,
            duration = duration
        )

        assertEquals(
            expected = 5.0,
            actual = speed.metersPerSecond,
            absoluteTolerance = TOLERANCE
        )
    }

    @Test
    fun `zero distance gives zero speed`() {

        val distance = Distance.meters(0.0)
        val duration = 200.seconds

        val speed = PaceCalculator.calculateSpeed(
            distance = distance,
            duration = duration
        )

        assertEquals(
            expected = 0.0,
            actual = speed.metersPerSecond
        )
    }

    @Test
    fun `zero duration to speed is rejected`() {

        val distance = Distance.kilometers(1.0)
        val duration = 0.seconds

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculateSpeed(
                distance = distance,
                duration = duration
            )
        }
    }

    @Test
    fun `negative duration to speed is rejected`() {

        val distance = Distance.kilometers(1.0)
        val duration = (-1).seconds

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculateSpeed(
                distance = distance,
                duration = duration
            )
        }
    }

    @Test
    fun `positive infinite duration to speed is rejected`() {

        val distance = Distance.kilometers(1.0)
        val duration = Duration.INFINITE

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculateSpeed(
                distance = distance,
                duration = duration
            )
        }
    }

    @Test
    fun `negative infinite duration to speed is rejected`() {

        val distance = Distance.kilometers(1.0)
        val duration = -Duration.INFINITE

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculateSpeed(
                distance = distance,
                duration = duration
            )
        }
    }

    @Test
    fun `duration is correctly calculated`() {

        val distance = Distance.kilometers(10.0)
        val pace = Pace.secondsPerKilometer(210.0)

        val duration = PaceCalculator.calculateFinishTime(
            distance = distance,
            pace = pace
        )

        assertEquals(
            expected = 35.minutes,
            actual = duration
        )
    }

    @Test
    fun `marathon finish time is correctly calculated`() {

        val distance = Distance.kilometers(42.195)
        val pace = Pace.secondsPerKilometer(240.0)

        val duration = PaceCalculator.calculateFinishTime(
            distance = distance,
            pace = pace
        )

        assertEquals(
            expected = 10126.8.seconds,
            actual = duration,
        )
    }

    @Test
    fun `zero distance gives zero finish time`() {

        val distance = Distance.meters(0.0)
        val pace = Pace.secondsPerKilometer(210.0)

        val duration = PaceCalculator.calculateFinishTime(
            distance = distance,
            pace = pace
        )

        assertEquals(
            expected = Duration.ZERO,
            actual = duration
        )
    }

    @Test
    fun `fractional finish time is correctly calculated`() {

        val distance = Distance.meters(400.0)
        val pace = Pace.secondsPerKilometer(171.25)

        val duration = PaceCalculator.calculateFinishTime(
            distance = distance,
            pace = pace
        )

        assertEquals(
            expected = 68.5.seconds,
            actual = duration,
        )
    }

    @Test
    fun `positive extra time is correctly calculated`() {

        val distance = Distance.kilometers(10.0)
        val pace = Pace.secondsPerKilometer(210.0)
        val extraTime = 2.minutes

        val duration = PaceCalculator.calculateFinishTime(
            distance = distance,
            pace = pace,
            extraTime = extraTime
        )

        assertEquals(
            expected = 37.minutes,
            actual = duration
        )
    }

    @Test
    fun `zero extra time is correctly calculated`() {

        val distance = Distance.kilometers(10.0)
        val pace = Pace.secondsPerKilometer(210.0)
        val extraTime = 0.seconds

        val duration = PaceCalculator.calculateFinishTime(
            distance = distance,
            pace = pace,
            extraTime = extraTime
        )

        assertEquals(
            expected = 35.minutes,
            actual = duration
        )
    }

    @Test
    fun `negative extra time is rejected`() {

        val distance = Distance.kilometers(10.0)
        val pace = Pace.secondsPerKilometer(210.0)
        val extraTime = (-1).minutes

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculateFinishTime(
                distance = distance,
                pace = pace,
                extraTime = extraTime
            )
        }
    }

    @Test
    fun `positive infinite extra time is rejected`() {

        val distance = Distance.kilometers(10.0)
        val pace = Pace.secondsPerKilometer(210.0)
        val extraTime = Duration.INFINITE

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculateFinishTime(
                distance = distance,
                pace = pace,
                extraTime = extraTime
            )
        }
    }

    @Test
    fun `negative infinite extra time is rejected`() {

        val distance = Distance.kilometers(10.0)
        val pace = Pace.secondsPerKilometer(210.0)
        val extraTime = -Duration.INFINITE

        assertFailsWith<IllegalArgumentException> {
            PaceCalculator.calculateFinishTime(
                distance = distance,
                pace = pace,
                extraTime = extraTime
            )
        }
    }
}