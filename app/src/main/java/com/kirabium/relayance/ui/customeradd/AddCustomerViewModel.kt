package com.kirabium.relayance.ui.customeradd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.domain.usecases.AddCustomerResult
import com.kirabium.relayance.domain.usecases.AddCustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCustomerViewModel @Inject constructor(
    private val addCustomer: AddCustomerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddCustomerUiState())
    val uiState: StateFlow<AddCustomerUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value) }

    fun onEmailChange(value: String) =
        _uiState.update { it.copy(email = value, emailError = false) }

    fun save() {
        viewModelScope.launch {
            _uiState.update { it.copy(isloading = true) }
            val current = _uiState.value
            when (addCustomer(current.name, current.email)) {
                AddCustomerResult.Success ->
                    _uiState.update { it.copy(isSaved = true, emailError = false) }
                AddCustomerResult.InvalidEmail ->
                    _uiState.update { it.copy(isloading=false, emailError = true) }
            }
        }
    }
}