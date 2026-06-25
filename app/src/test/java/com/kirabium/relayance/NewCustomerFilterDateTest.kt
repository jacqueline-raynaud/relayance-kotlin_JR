package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Calendar
import java.util.Date


class NewCustomerFilterDateTest {

    // function to create date without hours and minutes
    private fun createDateForTest(year: Int, month: Int, day: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.clear() // zero in hours and minutes
        calendar.set(year, month, day)
        return calendar.time
    }

    val dDayTest = createDateForTest(2026, Calendar.JANUARY, 1)

    // data class for list of dates test
    data class LimitDatesTest(
        val nameTest: String,
        val customer: Customer,
        val expectedResult : Boolean
    )

    // list of dates test
    private val datesTest = listOf(
        LimitDatesTest(
            "Does a creation date earlier than the filter date result in a false result",
            customer= Customer(1, "Alice", "alice@test.com",createDateForTest(2025, Calendar.SEPTEMBER, 1)),
            expectedResult = false
        ),
        LimitDatesTest(
            "Does a date later than the filter result in a true value",
            customer=Customer (2,"Pierre", "pierre@test.com",createDateForTest(2025, Calendar.DECEMBER, 1)),
            expectedResult = true
        ),
        LimitDatesTest(
            "Does a date equal the filter result in a true value",
            customer=Customer (3,"Jeanne", "jeanne@test.com",createDateForTest(2025, Calendar.OCTOBER, 1)),
            expectedResult = true
        )
    )

    @Test
    fun `isNewCustomer function reacts correctly to all imposed variations`() {
        datesTest.forEach { datesTest ->

            // Act
            val result = datesTest.customer.isNewCustomer(dDayTest)

            // Assert
            assertThat(result)
                .describedAs(datesTest.nameTest)
                .isEqualTo(datesTest.expectedResult)
        }

    }
}
