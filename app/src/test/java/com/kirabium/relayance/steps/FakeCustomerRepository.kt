package com.kirabium.relayance.steps

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository

class FakeCustomerRepository : CustomerRepository {
    val customers = mutableListOf<Customer>()
    override suspend fun getAllCustomers(): List<Customer> = customers.toList()
    override suspend fun getCustomerById(id: Int): Customer? = customers.find { it.id == id }
    override suspend fun addCustomer(customer: Customer) { customers.add(customer) }
}