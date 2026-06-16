package com.kirabium.relayance.domain.repository

import com.kirabium.relayance.domain.model.Customer

interface CustomerRepository {
    suspend fun getAllCustomers(): List<Customer>
    suspend fun getCustomerById(id: Int): Customer?
    suspend fun addCustomer(customer: Customer)
}