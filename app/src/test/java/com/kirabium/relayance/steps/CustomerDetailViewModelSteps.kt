package com.kirabium.relayance.steps

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.usecases.GetCustomerByIdUseCase
import com.kirabium.relayance.ui.customerdetail.CustomerDetailUiState
import com.kirabium.relayance.ui.customerdetail.CustomerDetailViewModel
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat

import java.util.Date
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalCoroutinesApi::class)
class CustomerDetailViewModelSteps {

    private lateinit var repository: FakeCustomerRepository
    private lateinit var viewModel: CustomerDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = FakeCustomerRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Given("a customer with ID {int} named {string} exists in the repository")
    fun a_customer_with_id_named_exists_in_the_repository(id: Int, name: String) {
        val customer = Customer(id = id, name = name, email = "test@test.com", createdAt = Date())
        runBlocking {
            repository.addCustomer(customer)
        }
    }

    @When("I load the customer with ID {int}")
    fun i_load_the_customer_with_id(id: Int) {
        val useCase = GetCustomerByIdUseCase(repository)
        viewModel = CustomerDetailViewModel(useCase)

        viewModel.loadCustomer(id)
    }

    @Then("the detail state is Success and displays the name {string}")
    fun the_detail_state_is_success_and_displays_the_name(expectedName: String) {
        val state = viewModel.uiState.value

        assertThat(state).isInstanceOf(CustomerDetailUiState.Success::class.java)

        val successState = state as CustomerDetailUiState.Success
        assertThat(successState.customer.name).isEqualTo(expectedName)
    }

    @Then("the detail state is Error with the message {string}")
    fun the_detail_state_is_error_with_the_message(expectedMessage: String) {
        val state = viewModel.uiState.value

        assertThat(state).isInstanceOf(CustomerDetailUiState.Error::class.java)

        val errorState = state as CustomerDetailUiState.Error
        assertThat(errorState.exception.message).isEqualTo(expectedMessage)
    }
}