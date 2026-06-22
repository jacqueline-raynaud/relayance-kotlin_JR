package com.kirabium.relayance.usecase

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.usecases.GetCustomerByIdUseCase
import com.kirabium.relayance.steps.FakeCustomerRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.Date

class GetCustomerByIdUseCaseTest {

        private val repository = FakeCustomerRepository()
        private val useCase = GetCustomerByIdUseCase(repository)

        @Test
        fun `invoke returns customer when present`() = runBlocking {
            // Arrange
            val c1 = Customer(1, "Alice", "alice@test.com", Date())
            val c2 = Customer(2, "Bob", "bob@test.com", Date())
            repository.customers.addAll(listOf(c1, c2))

            // Act
            val result = useCase(2)

            // Assert
            assertEquals(c2, result)
        }

        @Test
        fun `invoke returns null when customer not present`() = runBlocking {
            // Arrange
            repository.customers.clear()

            // Act
            val result = useCase(99)

            // Assert
            assertNull(result)
        }
}