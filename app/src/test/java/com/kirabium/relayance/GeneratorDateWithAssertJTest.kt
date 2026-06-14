package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData.generateDate
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.Before
import java.util.Calendar
import java.util.Date


class GeneratorDateWithAssertJTest {

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
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2025)
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.DECEMBER)
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(10)
    }

    @Test
    fun `removing a lot of months doesn't crashes`() {

        // Arrange
        val monthsBack = 120000

        // Act
        val result: Date = generateDate(monthsBack)

        // Assert
        assertThatCode { (result) }.doesNotThrowAnyException()
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
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2026)
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.JANUARY)
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(10)

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
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2026)
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.FEBRUARY)
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(28)
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
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(2026)
        assertThat(calendar.get(Calendar.MONTH)).isEqualTo(Calendar.FEBRUARY)
        assertThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(10)
    }
}
