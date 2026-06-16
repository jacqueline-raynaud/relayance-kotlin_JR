package com.kirabium.relayance.ui.customerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.domain.usecases.GetAllCustomersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerListViewModel @Inject constructor(
    private val getAllCustomersUseCase: GetAllCustomersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<CustomerListUiState>(CustomerListUiState.Loading)
    val uiState: StateFlow<CustomerListUiState> = _uiState.asStateFlow()

    init {
        loadCustomers()
    }

    fun loadCustomers() {
        viewModelScope.launch {
            _uiState.value = CustomerListUiState.Loading

            try {
                val customers = getAllCustomersUseCase()
                _uiState.value = CustomerListUiState.Success(customers)
            } catch (e: Exception) {
                _uiState.value = CustomerListUiState.Error(e)
            }
        }
    }
}