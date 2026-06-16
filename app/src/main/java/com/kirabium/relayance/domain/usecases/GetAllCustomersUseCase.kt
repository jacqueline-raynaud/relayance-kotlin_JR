package com.kirabium.relayance.domain.usecases

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import javax.inject.Inject

class GetAllCustomersUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(): List<Customer> { // ou Flow<List<Customer>> si on veut un flux de données
        val customers=customerRepository.getAllCustomers()
        return customers
    }
}
