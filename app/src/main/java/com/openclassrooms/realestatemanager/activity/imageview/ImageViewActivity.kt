package com.openclassrooms.realestatemanager.activity.imageview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import com.openclassrooms.realestatemanager.ui.theme.RealEstateManagerTheme

class ImageViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealEstateManagerTheme {
                Surface(color = Color.Black.copy(alpha = 0.7f), modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        finish()
                    }) {
                    Image(rememberImagePainter(intent.getStringExtra("img")), contentDescription = "", Modifier.fillMaxSize())
                }
            }
        }
    }
}