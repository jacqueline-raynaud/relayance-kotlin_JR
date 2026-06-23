package com.kirabium.relayance.domain.usecases

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import com.kirabium.relayance.domain.validator.EmailValidator
import java.util.Date
import javax.inject.Inject


sealed interface AddCustomerResult {
    data object Success : AddCustomerResult
    data object InvalidEmail : AddCustomerResult
    data object EmptyName : AddCustomerResult
}

class AddCustomerUseCase @Inject constructor(
    private val repository: CustomerRepository,
    private val emailValidator: EmailValidator
) {
    suspend operator fun invoke(name: String, email: String): AddCustomerResult {
        if (name.isBlank()) return AddCustomerResult.EmptyName
        if (!emailValidator.isValid(email)) return AddCustomerResult.InvalidEmail


        val nextId = (repository.getAllCustomers().maxOfOrNull { it.id } ?: 0) + 1
        repository.addCustomer(Customer(nextId, name, email, Date()))

        return AddCustomerResult.Success
    }
}