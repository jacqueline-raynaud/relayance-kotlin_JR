package com.kirabium.relayance.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.databinding.ActivityMainBinding
import com.kirabium.relayance.ui.adapter.CustomerAdapter
import com.kirabium.relayance.ui.customerlist.CustomerListUiState
import com.kirabium.relayance.ui.customerlist.CustomerListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customerAdapter: CustomerAdapter
    private val viewModel: CustomerListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupCustomerRecyclerView()
        setupFab()
        observeViewModel()
    }

    private fun setupFab() {
        binding.addCustomerFab.setOnClickListener {
            val intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupCustomerRecyclerView() {
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(this)
        customerAdapter = CustomerAdapter(DummyData.customers) { customer ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_CUSTOMER_ID, customer.id)
            }
            startActivity(intent)
        }
        binding.customerRecyclerView.adapter = customerAdapter
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is CustomerListUiState.Loading -> {
                        // Exemple si tu as une ProgressBar et un TextView d'erreur dans ton XML
                        // binding.progressBar.visibility = View.VISIBLE
                        binding.customerRecyclerView.visibility = View.GONE
                        // binding.errorTextView.visibility = View.GONE
                    }

                    is CustomerListUiState.Success -> {
                        // binding.progressBar.visibility = View.GONE
                        binding.customerRecyclerView.visibility = View.VISIBLE
                        // binding.errorTextView.visibility = View.GONE

                        // On passe la liste fraîchement récupérée à l'adaptateur
                        customerAdapter.updateData(state.customers)
                    }

                    is CustomerListUiState.Error -> {
                        // binding.progressBar.visibility = View.GONE
                        binding.customerRecyclerView.visibility = View.GONE
                        // binding.errorTextView.visibility = View.VISIBLE
                        // binding.errorTextView.text = state.exception.message
                    }
                }
            }
        }
    }
}