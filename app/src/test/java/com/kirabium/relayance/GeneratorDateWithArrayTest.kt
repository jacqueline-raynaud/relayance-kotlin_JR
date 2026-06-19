package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData.generateDate
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.Test
import java.util.Calendar
import java.util.Date


class GeneratorDateWithArrayTest {

    // function to create date without hours and minutes
    private fun createDateForTest(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.clear() // zero in hours and minutes
        calendar.set(year, month, day)
        return calendar.time
    }

    // data class for list of dates test
    data class DatesTest(
        val nameTest: String,
        val monthsToRemove: Int,
        val dateActual: Date,
        val dateExpected: Date
    )

    // list of dates test
    private val datesTest = listOf(
        DatesTest(
            "remove 1 month from March 31st, display February 28th",
            1,
            createDateForTest(2026, Calendar.MARCH, 31),
            createDateForTest(2026, Calendar.FEBRUARY, 28)
        ),
        DatesTest(
            "remove 1 month from January 1st, display December 1st",
            1,
            createDateForTest(2026, Calendar.JANUARY, 1),
            createDateForTest(2025, Calendar.DECEMBER, 1)
        ),
        DatesTest(
            "remove zero month from January 1st, display January 1st",
            monthsToRemove = 0,
            dateActual = createDateForTest(2026, Calendar.JANUARY, 1),
            dateExpected = createDateForTest(2026, Calendar.JANUARY, 1)
        ),
        DatesTest(
            "remove négative month add month to current date",
            monthsToRemove = -1,
            dateActual = createDateForTest(2026, Calendar.JANUARY, 1),
            dateExpected = createDateForTest(2026, Calendar.FEBRUARY, 1)
        )
    )

    @Test
    fun `the generateDate function reacts correctly to all imposed variations`() {
        datesTest.forEach { datesTest ->
            // Act
            val result = generateDate(datesTest.monthsToRemove, datesTest.dateActual)
            // Assert
            assertThat(result)
                .describedAs(datesTest.nameTest)
                .isEqualTo(datesTest.dateExpected)
        }

    }

    @Test
    fun `removing a lot of months doesn't crash`() {
        assertThatCode {
            generateDate(120000, myDate = createDateForTest(2026, Calendar.JANUARY, 1))
        }.doesNotThrowAnyException()
    }
}
