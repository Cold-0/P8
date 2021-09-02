package com.cold0.realestatemanager.screens.photoviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.theme.RealEstateManagerTheme

@ExperimentalCoilApi
class PhotoViewerActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RealEstateManagerTheme {
				Surface(color = Color.Black.copy(alpha = 0.7f), modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        finish()
                    }) {
					ZoomableImageComposable(intent.getStringExtra("img"))
				}
			}
		}
	}
}