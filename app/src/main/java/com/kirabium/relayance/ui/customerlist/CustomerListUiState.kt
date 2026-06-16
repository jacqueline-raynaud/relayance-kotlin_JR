package com.kirabium.relayance.ui.customerlist

import com.kirabium.relayance.domain.model.Customer

sealed class CustomerListUiState {
    data object Loading : CustomerListUiState()
    data class Success(val customers: List<Customer>) : CustomerListUiState()
    data class Error(val exception: Throwable) : CustomerListUiState()
}