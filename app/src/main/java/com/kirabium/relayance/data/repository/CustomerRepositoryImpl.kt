package com.kirabium.relayance.data.repository

import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor() : CustomerRepository {
    // si on part sur une liste vide : private val customers = mutableListOf<Customer>()

    private val currentCustomers = DummyData.customers.toMutableList()

    override suspend fun getAllCustomers(): List<Customer> {
        //delay(500) // Petit délai artificiel pour simuler un temps de chargement réseau/BDD
        return currentCustomers.toList()
    }

    override suspend fun getCustomerById(id: Int): Customer? {
        //delay(200)
        return currentCustomers.find { it.id == id }
    }

    override suspend fun addCustomer(customer: Customer) {
        //delay(500)
        currentCustomers.add(customer)
    }
}
