package com.kirabium.relayance.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kirabium.relayance.R
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding
import com.kirabium.relayance.ui.customeradd.AddCustomerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding
    private val viewModel: AddCustomerViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupToolbar()
        saveCustomer()
        observeState()
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupBinding() {
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun saveCustomer() {
        binding.saveFab.setOnClickListener {
            viewModel.onNameChange(binding.nameEditText.text?.toString().orEmpty())
            viewModel.onEmailChange(binding.emailEditText.text?.toString().orEmpty())
            viewModel.save()
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    // email error
                    binding.emailTextInputLayout.error =
                        if (state.emailError) getString(R.string.invalid_email) else null

                    // loading state - disable FAB while loading
                    binding.saveFab.isEnabled = !state.isloading

                    //Toast and go to MainActivity
                    if (state.isSaved) {
                        Toast.makeText(
                            this@AddCustomerActivity,
                            R.string.customer_added,
                            Toast.LENGTH_LONG
                        ).show()
                        finish()
                    }
                }
            }
        }
    }
}