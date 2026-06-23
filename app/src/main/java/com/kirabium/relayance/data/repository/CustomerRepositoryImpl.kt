package com.kirabium.relayance.data.repository

import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor() : CustomerRepository {

    companion object{
    private val currentCustomers = DummyData.customers.toMutableList()

        fun resetForTest(){
            currentCustomers.clear()
            currentCustomers.addAll(DummyData.customers)
        }
    }

    override suspend fun getAllCustomers(): List<Customer> {
        return currentCustomers.toList()
    }

    override suspend fun getCustomerById(id: Int): Customer? {
        return currentCustomers.find { it.id == id }
    }

    override suspend fun addCustomer(customer: Customer) {
        currentCustomers.add(customer)
    }
}
