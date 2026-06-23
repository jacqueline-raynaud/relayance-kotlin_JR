package com.kirabium.relayance.ui.customeradd

data class AddCustomerUiState (
        val name: String = "",
        val email: String = "",
        val emailError: Boolean = false,
        val isSaved: Boolean = false,
        val isloading: Boolean = false,
        val nameError: Boolean = false
)
