package com.kirabium.relayance.steps

import com.kirabium.relayance.domain.usecases.AddCustomerUseCase
import com.kirabium.relayance.domain.validator.EmailValidator
import com.kirabium.relayance.ui.customeradd.AddCustomerViewModel
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat


class AddCustomerStepsTest {
    private lateinit var repository: FakeCustomerRepository
    private lateinit var viewModel: AddCustomerViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = FakeCustomerRepository()
        viewModel = AddCustomerViewModel(
            AddCustomerUseCase(repository, EmailValidator())
            )
    }

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Given("an empty customer addition form")
    fun formulaire_vide() { // etat initial
    }

    @When("I enter the name {string} and the email {string}")
    fun je_saisie(nom: String, email: String) {
        viewModel.onNameChange(nom)
        viewModel.onEmailChange(email)
    }

    @Then("I confirm the addition")
    fun je_valide() {
        viewModel.save()
    }

    @Then("The client {string} is present in the list of clients")
    fun client_est_present(nom: String) {
        assertThat(repository.customers.map { it.name }).contains(nom)
    }

    @Then("No email errors are displayed")
    fun aucune_erreur() {
        assertThat(viewModel.uiState.value.emailError).isFalse()
    }

    @Then("An email error is displayed")
    fun une_erreur_affichee() {
        assertThat(viewModel.uiState.value.emailError).isTrue()
    }

    @Then("The customer list remains unchanged")
    fun liste_clients_inchangee() {
        assertThat(repository.customers).isEmpty()
    }
}
