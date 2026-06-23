package com.kirabium.relayance.usecase

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.usecases.GetCustomerByIdUseCase
import com.kirabium.relayance.steps.FakeCustomerRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.util.Date

class GetCustomerByIdUseCaseTest : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf
    val repository = FakeCustomerRepository()
    val useCase = GetCustomerByIdUseCase(repository)


    Given("a repository containing two customers") {
        val alice = Customer(1, "Alice", "alice@test.com", Date())
        val bob = Customer(2, "Bob", "bob@test.com", Date())

        repository.customers.addAll(listOf(alice, bob))


        When("I request to retrieve the customer with ID 2") {
            val result = useCase(2)

            Then("it should return Bob") {
                result shouldBe bob
            }
        }
    }

    Given("an empty repository") {

        When("I request to retrieve the customer 1") {
            val result = useCase(1)

            Then("it should return null") {
                result shouldBe null
            }
        }
    }
})
