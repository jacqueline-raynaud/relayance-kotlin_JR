package com.kirabium.relayance.steps

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository

class FailableCustomerRepository : CustomerRepository {
    private val customers = mutableListOf<Customer>()
    var shouldFail=false

    fun addInitialCustomers(list:List<Customer>) {
        customers.addAll(list)
    }

    override suspend fun getAllCustomers(): List<Customer> {
        if (shouldFail) { throw Exception("Simulated failure")        }
        return customers.toList()
    }
    override suspend fun getCustomerById(id: Int): Customer? = customers.find { it.id == id }
    override suspend fun addCustomer(customer: Customer) { customers.add(customer) }
}
