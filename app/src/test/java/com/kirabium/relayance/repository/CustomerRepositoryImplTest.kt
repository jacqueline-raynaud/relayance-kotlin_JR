package com.kirabium.relayance.repository

import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.data.repository.CustomerRepositoryImpl
import com.kirabium.relayance.domain.model.Customer
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.Date

class CustomerRepositoryImplTest : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf
    val repository = CustomerRepositoryImpl()

    // Scénario 1 : récupérer tous les clients
    Given("a repository") {
        When("I request all customers") {
            val result = repository.getAllCustomers()
            Then("it should return a list matching the DummyData size") {
                result.size shouldBe DummyData.customers.size
            }
        }
    }

    // Scénario 2 : récupérer un client par un id valide
    Given("a repository and a valid customer id") {
        val targetCustomer = DummyData.customers.first()
        When("I request the customer by that id") {
            val result = repository.getCustomerById(targetCustomer.id)
            Then("it should return the correct customer") {
                result shouldNotBe null
                result?.id shouldBe targetCustomer.id
                result?.name shouldBe targetCustomer.name
            }
        }
    }

    // Scénario 3 : récupérer un client par un id inexistant
    Given("a repository and an id that does not exist") {
        val impossibleId = DummyData.customers.maxOf { it.id } + 1
        When("I request the customer by that invalid id") {
            val result = repository.getCustomerById(impossibleId)
            Then("it should return null") {
                result shouldBe null
            }
        }
    }

    // Scénario 4 : ajouter un nouveau client
    Given("a repository and a new customer to add") {
        val initialSize = repository.getAllCustomers().size
        val newId = DummyData.customers.maxOf { it.id } + 1
        val newCustomer = Customer(newId, "New Customer", "new@customer.com", Date())
        When("I add the new customer") {
            repository.addCustomer(newCustomer)
            Then("it should be added at the end of the list") {
                val allCustomers = repository.getAllCustomers()
                allCustomers.size shouldBe initialSize + 1
                allCustomers.last() shouldBe newCustomer
            }
        }
    }
})
