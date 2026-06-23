package com.kirabium.relayance.usecase

import com.kirabium.relayance.domain.usecases.AddCustomerResult
import com.kirabium.relayance.domain.usecases.AddCustomerUseCase
import com.kirabium.relayance.domain.validator.EmailValidator
import com.kirabium.relayance.steps.FakeCustomerRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class AddCustomerUseCaseTest : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val repository = FakeCustomerRepository()
    val validator = EmailValidator()
    val useCase = AddCustomerUseCase(repository, validator)

    Given("the Add Customer Use Case") {

        // scenario 1 : success
        When("I request to add a customer with valid information") {
            val result = useCase("Alice", "alice@test.com")

            Then("it should return Success and save the customer") {
                result shouldBe AddCustomerResult.Success
                repository.customers.size shouldBe 1
                repository.customers[0].name shouldBe "Alice"
            }
        }

        // scenario 2 : error name
        When("I request to add a customer with an empty name") {
            val result = useCase("   ", "bob@test.com")

            Then("it should return EmptyName and not save anything") {
                result shouldBe AddCustomerResult.EmptyName
                repository.customers.size shouldBe 0
            }
        }

        // scenario 3 : error email
        When("I request to add a customer with an invalid email") {
            val result = useCase("paul", "paulsansarobase.com")

            Then("it should return InvalidEmail and not save anything") {
                result shouldBe AddCustomerResult.InvalidEmail
                repository.customers.size shouldBe 0
            }
        }
    }
})
