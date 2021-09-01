package com.cold0.realestatemanager.screens.converter

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.cold0.realestatemanager.Utils

@Composable
fun ConverterComposable() {
	var euro by remember { mutableStateOf("") }
	var dollar by remember { mutableStateOf("") }

	TextField(
		value = euro,
		onValueChange = {
			euro = it
			val result = it.toIntOrNull()
			dollar = if (result != null)
				Utils.convertEuroToDollar(result).toString()
			else
				"Incorrect Euro Value"
		},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Number
		),
		label = { Text("Euro") },
		modifier = Modifier
			.onFocusChanged {
				if (it.isFocused)
					euro = ""
			}
			.padding(8.dp)
	)
	TextField(
		value = dollar,
		onValueChange = {
			dollar = it
			val result = it.toIntOrNull()
			euro = if (result != null)
				Utils.convertDollarToEuro(result).toString()
			else
				"Incorrect Dollar Value"
		},
		keyboardOptions = KeyboardOptions(
			keyboardType = KeyboardType.Number
		),
		label = { Text("Dollar") },
		modifier = Modifier
			.onFocusChanged {
				if (it.isFocused)
					dollar = ""
			}
			.padding(8.dp)
	)
}