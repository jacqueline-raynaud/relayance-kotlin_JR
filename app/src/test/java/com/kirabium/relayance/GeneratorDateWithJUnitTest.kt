package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData.generateDate
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import java.util.Calendar
import java.util.Date


class GeneratorDateWithJUnitTest {

    private lateinit var myDate: Date

    @Before
    fun setup() {
        val dateTest = Calendar.getInstance()
        dateTest.set(2026, Calendar.JANUARY, 10)
        myDate = dateTest.time
    }

    @Test
    fun `removing a month in January moves the year to the previous year`() {

        // Arrange
        //already initialized

        // Act
        val result: Date = generateDate(1, myDate = myDate)

        // Assert
        val calendar = Calendar.getInstance()
        calendar.time = result
        assertEquals(2025, calendar.get(Calendar.YEAR))
        assertEquals(Calendar.DECEMBER, calendar.get(Calendar.MONTH))
        assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun `removing a lot of months doesn't crashes`() {

        // Arrange
        val monthsBack = 120000

        // Act
        val result: Date = generateDate(monthsBack)

        // Assert
        val calendar = Calendar.getInstance()
        calendar.time = result
        // je ne sais pas tester si pas crashe puisque c'est pas null en attendant
        assertNotNull(result)
    }

    @Test
    fun `substracting zero months gives the same date `() {
        // Arrange
        // already initialized

        // Act
        val result: Date = generateDate(0, myDate = myDate)

        // Assert
        val calendar = Calendar.getInstance()
        calendar.time = result
        assertEquals(2026, calendar.get(Calendar.YEAR))
        assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH))
        assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun `remove 1 month from March 31st, display February 28th`() {
        // Arrange
        val dateTest = Calendar.getInstance()
        dateTest.set(2026, Calendar.MARCH, 31)
        val myDate: Date = dateTest.time

        // Act
        val result: Date = generateDate(1, myDate = myDate)

        // Assert
        val calendar = Calendar.getInstance()
        calendar.time = result
        assertEquals(2026, calendar.get(Calendar.YEAR))
        assertEquals(Calendar.FEBRUARY, calendar.get(Calendar.MONTH))
        assertEquals(28, calendar.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun `remove negative month add month to current date`() {
        // Arrange
        val monthsBack = -1

        // Act
        val result: Date = generateDate(monthsBack, myDate = myDate)

        // Assert
        val calendar = Calendar.getInstance()
        calendar.time = result
        assertEquals(2026, calendar.get(Calendar.YEAR))
        assertEquals(Calendar.FEBRUARY, calendar.get(Calendar.MONTH))
        assertEquals(10, calendar.get(Calendar.DAY_OF_MONTH))
    }
}
