package com.cold0.realestatemanager.screens.converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cold0.realestatemanager.theme.RealEstateManagerTheme

class ConverterActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RealEstateManagerTheme {

			}
		}
	}
}