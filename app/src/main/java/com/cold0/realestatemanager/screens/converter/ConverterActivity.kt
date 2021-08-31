package com.cold0.realestatemanager.screens.converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.Utils
import com.cold0.realestatemanager.theme.RealEstateManagerTheme

class ConverterActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			RealEstateManagerTheme {


				Column() {
					TopAppBar(
						elevation = 4.dp,
						title = { Text("Converter Tools") },
						navigationIcon = {
							IconButton(onClick = { finish() }) {
								Icon(Icons.Filled.ArrowBack, contentDescription = getString(R.string.content_description_return_to_home_screen), tint = Color.White)
							}
						}
					)
					Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
						Text("Dollar / Euro", style = MaterialTheme.typography.h3, textAlign = TextAlign.Center)

						val configuration = LocalConfiguration.current

						if (configuration.screenWidthDp > 450)
							Row(modifier = Modifier.padding(8.dp)) {
								Converter()
							}
						else
							Column(modifier = Modifier.padding(8.dp)) {
								Converter()
							}
					}
				}
			}
		}
	}
}

@Composable
fun Converter() {
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