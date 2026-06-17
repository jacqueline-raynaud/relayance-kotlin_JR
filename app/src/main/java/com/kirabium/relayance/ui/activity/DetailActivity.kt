package com.kirabium.relayance.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kirabium.relayance.R
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.databinding.ActivityDetailBinding
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.ui.composable.DetailScreen
import com.kirabium.relayance.ui.customerdetail.CustomerDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: CustomerDetailViewModel by viewModels()

    companion object {
        const val EXTRA_CUSTOMER_ID = "customer_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1)
        if (customerId != -1) {
            viewModel.loadCustomer(customerId)
        }
        setContent {
            DetailScreen(
                viewModel = viewModel,
                onBackClick = {
                    finish()
                })
        }
    }
}




