package com.kirabium.relayance.domain.usecases

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import javax.inject.Inject

class GetCustomerByIdUseCase @Inject constructor(
    private val customerRepository: CustomerRepository
) {
    suspend operator fun invoke(id: Int): Customer? {
        return customerRepository.getCustomerById(id)
    }
}