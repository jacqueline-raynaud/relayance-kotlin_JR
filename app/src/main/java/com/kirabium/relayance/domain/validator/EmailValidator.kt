package com.kirabium.relayance.domain.validator

import javax.inject.Inject

class EmailValidator @Inject constructor() {
    // pas android.util.Patterns (indispo en test JVM)
    private val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    fun isValid(email: String): Boolean = regex.matches(email)
}