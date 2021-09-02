package com.cold0.realestatemanager.screens.editestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.cold0.realestatemanager.screens.commons.TopBarReturn
import com.cold0.realestatemanager.theme.RealEstateManagerTheme

class EditEstateActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RealEstateManagerTheme {
				TopBarReturn(this, "Edit Estate") {
					Text("Hello")
				}
			}
		}
	}
}