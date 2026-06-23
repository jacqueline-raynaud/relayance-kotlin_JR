package com.kirabium.relayance.steps

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.usecases.GetAllCustomersUseCase
import com.kirabium.relayance.ui.customerlist.CustomerListUiState
import com.kirabium.relayance.ui.customerlist.CustomerListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class CustomerListViewModelSteps {

    private lateinit var repository: FailableCustomerRepository
    private lateinit var viewModel: CustomerListViewModel

    // --- CONFIGURATION COROUTINES ---
    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = FailableCustomerRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Given("a repository containing {int} customers")
    fun a_repository_containing_customers(customerCount: Int) {
        val list = (1..customerCount).map { Customer(it, "Client $it", "c$it@test.com", Date()) }
        repository.addInitialCustomers(list)
    }

    @Given("a repository that fails to return customers")
    fun a_repository_that_fails_to_return_customers() {
        repository.shouldFail = true
    }

    @When("I load the customer list")
    fun i_load_the_customer_list() {
        viewModel = CustomerListViewModel(GetAllCustomersUseCase(repository))
    }

    @Then("the list state is Success with {int} customers")
    fun the_list_state_is_success_with_customers(customerCount: Int) {
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(CustomerListUiState.Success::class.java)
        val successState = state as CustomerListUiState.Success
        assertThat(successState.customers).hasSize(customerCount)
    }

    @Then("the list state is Error")
    fun the_list_state_is_error() {
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(CustomerListUiState.Error::class.java)
    }

}