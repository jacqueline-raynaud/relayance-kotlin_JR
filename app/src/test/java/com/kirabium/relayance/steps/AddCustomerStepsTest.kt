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
    fun an_empty_customer_addition_form() {
        assertThat(viewModel.uiState.value.name).isEmpty()
        assertThat(viewModel.uiState.value.email).isEmpty()
    }

    @When("I enter the name {string} and the email {string}")
    fun i_enter_the_name_and_email(nom: String, email: String) {
        viewModel.onNameChange(nom)
        viewModel.onEmailChange(email)
    }

    @Then("I confirm the addition")
    fun i_confirm_the_addition() {
        viewModel.save()
    }

    @Then("The client {string} is present in the list of clients")
    fun the_client_is_present_in_the_list_of_clients(nom: String) {
        assertThat(repository.customers.map { it.name }).contains(nom)
    }

    @Then("No email errors are displayed")
    fun no_email_errors_displayed() {
        assertThat(viewModel.uiState.value.emailError).isFalse()
    }

    @Then("An email error is displayed")
    fun an_email_error_displayed() {
        assertThat(viewModel.uiState.value.emailError).isTrue()
    }

    @Then("The customer list remains unchanged")
    fun the_customer_list_remains_unchanged() {
        assertThat(repository.customers).isEmpty()
    }

    @Then("A name error is displayed")
    fun a_name_error_displayed() {
        assertThat(viewModel.uiState.value.nameError).isTrue()
    }
}
