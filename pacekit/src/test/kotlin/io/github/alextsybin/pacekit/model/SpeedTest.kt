package io.github.alextsybin.pacekit.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SpeedTest {

    companion object {

        private const val TOLERANCE = 1e-9
    }

    @Test
    fun `meters per second are correctly converted to kilometers per hour`() {

        val metersPerSecond = 5.0

        val speed = Speed.metersPerSecond(metersPerSecond)

        assertEquals(18.0, speed.kilometersPerHour, TOLERANCE)
    }

    @Test
    fun `kilometers per hour are correctly converted to meters per second`() {

        val kilometersPerHour = 18.0

        val speed = Speed.kilometersPerHour(kilometersPerHour)

        assertEquals(5.0, speed.metersPerSecond, TOLERANCE)
    }

    @Test
    fun `miles per hour are correctly converted to meters per second`() {

        val milesPerHour = 10.0

        val speed = Speed.milesPerHour(milesPerHour)

        assertEquals(4.4704, speed.metersPerSecond, TOLERANCE)
    }

    @Test
    fun `meters per second are correctly converted to miles per hour`() {

        val metersPerSecond = 4.4704

        val speed = Speed.metersPerSecond(metersPerSecond)

        assertEquals(10.0, speed.milesPerHour, TOLERANCE)
    }

    @Test
    fun `zero speed is accepted`() {

        val speed = Speed.metersPerSecond(0.0)

        assertEquals(0.0, speed.kilometersPerHour)
    }

    @Test
    fun `negative speed is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Speed.metersPerSecond(-100.0)
        }
    }

    @Test
    fun `NaN speed is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Speed.metersPerSecond(Double.NaN)
        }
    }

    @Test
    fun `positive infinite speed is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Speed.metersPerSecond(Double.POSITIVE_INFINITY)
        }
    }

    @Test
    fun `negative infinite speed is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Speed.metersPerSecond(Double.NEGATIVE_INFINITY)
        }
    }
}