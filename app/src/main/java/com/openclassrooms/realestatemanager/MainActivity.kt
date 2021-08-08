package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureTextViewMain()
        configureTextViewQuantity()
    }

    private fun configureTextViewMain() {
        binding.textMain.textSize = 15f
        binding.textMain.text = getString(R.string.first_registered_real_estate)
    }

    private fun configureTextViewQuantity() {
        binding.quantityMain.textSize = 20f
        binding.quantityMain.text = String.format("%d", Utils.convertDollarToEuro(100))
    }
}