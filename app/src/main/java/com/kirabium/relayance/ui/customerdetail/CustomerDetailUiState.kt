package com.kirabium.relayance.ui.customerdetail

import com.kirabium.relayance.domain.model.Customer

sealed class CustomerDetailUiState {
    data object Loading : CustomerDetailUiState()
    data class Success(val customer: Customer) : CustomerDetailUiState()
    data class Error(val exception: Throwable) : CustomerDetailUiState()
}