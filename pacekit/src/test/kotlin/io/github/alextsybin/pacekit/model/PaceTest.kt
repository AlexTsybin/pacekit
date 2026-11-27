package io.github.alextsybin.pacekit.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class PaceTest {

    companion object {

        private const val TOLERANCE = 1e-9
    }

    @Test
    fun `seconds per kilometer are correctly converted to minutes per kilometer`() {

        val secondsPerKilometer = 210.0

        val pace = Pace.secondsPerKilometer(secondsPerKilometer)

        assertEquals(3.5, pace.minutesPerKilometer, TOLERANCE)
    }

    @Test
    fun `minutes per kilometer are correctly converted to seconds per kilometer`() {

        val minutesPerKilometer = 3.5

        val pace = Pace.minutesPerKilometer(minutesPerKilometer)

        assertEquals(210.0, pace.secondsPerKilometer, TOLERANCE)
    }

    @Test
    fun `seconds per mile are correctly converted to seconds per kilometer`() {

        val secondsPerMile = 337.96224

        val pace = Pace.secondsPerMile(secondsPerMile)

        assertEquals(210.0, pace.secondsPerKilometer, TOLERANCE)
    }

    @Test
    fun `seconds per kilometer are correctly converted to seconds per mile`() {

        val secondsPerKilometer = 210.0

        val pace = Pace.secondsPerKilometer(secondsPerKilometer)

        assertEquals(337.96224, pace.secondsPerMile, TOLERANCE)
    }

    @Test
    fun `zero pace is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Pace.secondsPerKilometer(0.0)
        }
    }

    @Test
    fun `negative pace is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Pace.secondsPerKilometer(-100.0)
        }
    }

    @Test
    fun `NaN pace is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Pace.secondsPerKilometer(Double.NaN)
        }
    }

    @Test
    fun `positive infinite pace is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Pace.secondsPerKilometer(Double.POSITIVE_INFINITY)
        }
    }

    @Test
    fun `negative infinite pace is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Pace.secondsPerKilometer(Double.NEGATIVE_INFINITY)
        }
    }

    @Test
    fun `pace conversion overflow is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Pace.minutesPerKilometer(Double.MAX_VALUE)
        }
    }
}