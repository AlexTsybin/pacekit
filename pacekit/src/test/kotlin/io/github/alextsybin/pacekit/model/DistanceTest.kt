package io.github.alextsybin.pacekit.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DistanceTest {

    companion object {

        private const val TOLERANCE = 1e-9
    }

    @Test
    fun `kilometers are correctly converted to meters`() {

        // Arrange
        val kilometers = 10.0

        // Act
        val distance = Distance.kilometers(kilometers)

        // Assert
        assertEquals(10000.0, distance.meters)
    }

    @Test
    fun `negative distance is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Distance.meters(-100.0)
        }
    }

    @Test
    fun `meters are correctly converted to kilometers`() {

        val meters = 1500.0

        val distance = Distance.meters(meters)

        assertEquals(1.5, distance.kilometers, TOLERANCE)
    }

    @Test
    fun `miles are correctly converted to meters`() {

        val miles = 1.0

        val distance = Distance.miles(miles)

        assertEquals(1609.344, distance.meters, TOLERANCE)
    }

    @Test
    fun `meters are correctly converted to miles`() {

        val meters = 1609.344

        val distance = Distance.meters(meters)

        assertEquals(
            expected = 1.0,
            actual = distance.miles,
            absoluteTolerance = TOLERANCE
        )
    }

    @Test
    fun `zero distance is accepted`() {

        val distance = Distance.meters(0.0)

        assertEquals(0.0, distance.kilometers)
    }

    @Test
    fun `NaN distance is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Distance.meters(Double.NaN)
        }
    }

    @Test
    fun `positive infinite distance is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Distance.meters(Double.POSITIVE_INFINITY)
        }
    }

    @Test
    fun `negative infinite distance is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Distance.meters(Double.NEGATIVE_INFINITY)
        }
    }

    @Test
    fun `distance conversion overflow is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Distance.kilometers(Double.MAX_VALUE)
        }
    }

    @Test
    fun `distance in miles conversion overflow is rejected`() {

        assertFailsWith<IllegalArgumentException> {
            Distance.miles(Double.MAX_VALUE)
        }
    }
}