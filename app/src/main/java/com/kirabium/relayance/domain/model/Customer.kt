package com.kirabium.relayance.domain.model

import java.util.Calendar
import java.util.Date

data class Customer(
    val id: Int,
    val name: String,
    val email: String,
    val createdAt: Date) {

    fun isNewCustomer(currentDate: Date = Date()): Boolean {
        val limitDate = Calendar.getInstance()
        limitDate.time = currentDate
        limitDate.add(Calendar.MONTH, -3)

        val createdAtCalendar = Calendar.getInstance().apply {
            time = createdAt
        }
        return !createdAtCalendar.before(limitDate)
    }
}
