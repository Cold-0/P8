package com.cold0.realestatemanager.screens.editestate

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldLazy(
	value: String,
	modifier: Modifier = Modifier,
	leadingIcon: ImageVector? = null,
	trailingIcon: ImageVector? = null,
	title: String? = null,
	placeholder: String? = null,
	keyboardType: KeyboardType = KeyboardType.Text,
	onValueChange: (String) -> (Unit),
) {
	var text by remember { mutableStateOf(value) }

	val leadingIconLambda: @Composable (() -> Unit)? =
		if (leadingIcon != null) {
			{ Image(leadingIcon, leadingIcon.name) }
		} else null
	val trailingIconLambda: @Composable (() -> Unit)? =
		if (trailingIcon != null) {
			{ Image(trailingIcon, trailingIcon.name) }
		} else null
	val labelLambda: @Composable (() -> Unit)? =
		if (title != null) {
			{ Text(title) }
		} else null
	val placeholderLambda: @Composable (() -> Unit)? =
		if (placeholder != null) {
			{ Text(placeholder) }
		} else null

	OutlinedTextField(
		value = value,
		leadingIcon = leadingIconLambda,
		trailingIcon = trailingIconLambda,
		label = labelLambda,
		placeholder = placeholderLambda,
		keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
		onValueChange = {
			text = it
			onValueChange(text)
		},
		modifier = modifier
			.padding(8.dp)
			.fillMaxWidth())
}