package com.cold0.realestatemanager.screens.home.estatedetail

import androidx.compose.foundation.Image
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun EstateDetailInfoLabel(icon: ImageVector, title: String, value: String) {
	OutlinedTextField(label = { Text(title) }, leadingIcon = { Image(icon, icon.name) }, value = value, onValueChange = {}, readOnly = true)
}