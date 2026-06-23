package com.kirabium.relayance.usecase

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.usecases.GetAllCustomersUseCase
import com.kirabium.relayance.steps.FakeCustomerRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.util.Date

class GetAllCustomersUseCaseTest : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf
    val repository = FakeCustomerRepository()
    val useCase = GetAllCustomersUseCase(repository)

    //scenario with data
    Given("a repository with two customers") {
        val paul = Customer(1, "pPaul", "paul@test.com", Date())
        val alain = Customer(2, "Alain", "alain@test.com", Date())

        repository.customers.addAll(listOf(paul, alain))

        When("the use case is executed for retrieve the list") {
            val result = useCase()

            Then("it should return the list of customers") {
                result shouldBe listOf(paul, alain)
                result.size shouldBe 2
            }
        }
    }
    // scenario whithout data
    Given("an empty repository") {

        When("the use case is executed for retrieve the list") {
            val result = useCase()

            Then("it should return an empty list") {
                result shouldBe emptyList()
            }
        }
    }
})
