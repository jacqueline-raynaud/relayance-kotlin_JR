package com.kirabium.relayance.usecase

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.usecases.GetAllCustomersUseCase
import com.kirabium.relayance.steps.FakeCustomerRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class GetAllCustomersUseCaseTest {
    private val repository = FakeCustomerRepository()
    private val useCase = GetAllCustomersUseCase(repository)

    @Test
    fun `invoke returns all customers from repository`() = runBlocking {
        // Arrange
        val c1 = Customer(1, "Alice", "alice@test.com", Date())
        val c2 = Customer(2, "Bob", "bob@test.com", Date())
        repository.customers.addAll(listOf(c1, c2))

        // Act : appeler le use case
        val result = useCase()

        // Assert : la liste retournée doit être celle du repository
        assertEquals(listOf(c1, c2), result)
    }
}