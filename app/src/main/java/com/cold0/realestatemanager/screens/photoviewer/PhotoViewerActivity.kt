package com.cold0.realestatemanager.screens.photoviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.model.Photo
import com.cold0.realestatemanager.theme.RealEstateManagerTheme

@ExperimentalCoilApi
class PhotoViewerActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val photo = intent.extras?.getParcelable<Photo>("img")
		setContent {
			RealEstateManagerTheme {
				var numberOfClick by remember { mutableStateOf(0) }
				Surface(color = Color.Black.copy(alpha = 0.7f), modifier = Modifier
					.fillMaxSize()
					.clickable {
						numberOfClick += 1
						if (numberOfClick >= 2)
							finish()
					}) {
					if (photo != null) {
						Box {
							ZoomableImage(photo)
							if (numberOfClick == 0)
								Surface(shape = RoundedCornerShape(8.dp), color = Color.Black.copy(alpha = 0.8f), modifier = Modifier
									.align(Alignment.BottomCenter)
									.padding(16.dp)) {
									Text(photo.description, color = Color.White, modifier = Modifier
										.padding(8.dp)
										.fillMaxWidth()
									)
								}
						}
					} else
						finish()
				}
			}
		}
	}
}