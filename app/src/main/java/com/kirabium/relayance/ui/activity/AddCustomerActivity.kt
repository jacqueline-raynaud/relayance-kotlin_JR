package com.kirabium.relayance.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding

class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupToolbar()
        saveCustomer()
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
            //TODO save customer
            // return to main activity 2 lignes à commenter pour test bout en bout
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        }
}