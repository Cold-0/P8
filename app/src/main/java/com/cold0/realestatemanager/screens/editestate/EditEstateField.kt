package com.cold0.realestatemanager.screens.editestate

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EditEstateField(
	icon: ImageVector,
	title: String,
	value: String,
	bottomSpacing: Dp = 8.dp,
	keyboardType: KeyboardType = KeyboardType.Text,
	onValueChanged: (String) -> (Unit),
) {
	var text by remember { mutableStateOf(value) }
//	Row {
//		Image(icon, icon.name)
//		Text(text = title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(paddingValues = PaddingValues(start = 8.dp)))
//	}
	Row {
		OutlinedTextField(leadingIcon = { Image(icon, icon.name) },
			label = { Text(title) },
			value = value,
			keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
			onValueChange = {
				text = it
				onValueChanged(text)
			},
			readOnly = true,
			modifier = Modifier
				.padding(8.dp)
				.fillMaxWidth())
	}
	Spacer(Modifier.height(bottomSpacing))
}