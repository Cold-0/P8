package com.cold0.realestatemanager.screens.home.estatedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EstateDetailInfoLabel(icon: ImageVector, title: String, value: String, leftSpacing: Dp = 64.dp, bottomSpacing: Dp = 8.dp) {
	Row {
		Image(icon, icon.name)
		Text(text = title, modifier = Modifier.padding(paddingValues = PaddingValues(start = 8.dp)))
	}
	Row {
		Spacer(Modifier.width(leftSpacing))
		Text(text = value, textAlign = TextAlign.Center)
	}
	Spacer(Modifier.height(bottomSpacing))
}