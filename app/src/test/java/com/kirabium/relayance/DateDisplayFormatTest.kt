package com.kirabium.relayance

import com.kirabium.relayance.extension.toHumanDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Calendar
import java.util.Date

class DateDisplayFormatTest {

    class DateExtTest {

        private fun createDate(year: Int, month: Int, day: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar.set(year, month, day)
            return calendar.time
        }

        @Test
        fun `toHumanDate correctly formats a date with two-digit days and months`() {
            // Arrange
            val myDate = createDate(2026, Calendar.DECEMBER, 25)

            // Act
            // Comme c'est une fonction d'extension, on l'appelle directement SUR l'objet Date
            val result = myDate.toHumanDate()

            // Assert
            assertThat(result).isEqualTo("25/12/2026")
        }

        @Test
        fun `toHumanDate correctly formats a date with one-digit days and months`() {
            // Arrange
            val myDate = createDate(2026, Calendar.JANUARY, 5)

            // Act
            val result = myDate.toHumanDate()

            // Assert
            assertThat(result).isEqualTo("05/01/2026")
        }
    }
}