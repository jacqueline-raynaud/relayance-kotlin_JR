package com.kirabium.relayance.ui.customerdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.domain.usecases.GetCustomerByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerDetailViewModel @Inject constructor(
    private val getCustomerByIdUseCase: GetCustomerByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CustomerDetailUiState>(CustomerDetailUiState.Loading)
    val uiState: StateFlow<CustomerDetailUiState> = _uiState.asStateFlow()

    fun loadCustomer(id: Int) {
        viewModelScope.launch {
            _uiState.value = CustomerDetailUiState.Loading
            try {
                val customer = getCustomerByIdUseCase(id)

                if (customer != null) {
                    _uiState.value = CustomerDetailUiState.Success(customer)
                } else {
                    _uiState.value = CustomerDetailUiState.Error(Exception("Client introuvable"))
                }
            } catch (e: Exception) {
                _uiState.value = CustomerDetailUiState.Error(e)
            }
        }
    }
}