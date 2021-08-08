package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
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
        binding.textMain.text = "Le premier bien immobilier enregistré vaut "
    }

    private fun configureTextViewQuantity() {
        val quantity = Utils.convertDollarToEuro(100)
        binding.quantityMain.textSize = 20f
        binding.quantityMain.text = String.format("%d", quantity)
    }
}