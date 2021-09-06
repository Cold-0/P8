package com.cold0.realestatemanager.screens.home.estatelist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.screens.commons.OutlinedDropDown
import kotlin.reflect.KMutableProperty1

@Suppress("SelfAssignment")
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun EtateListFilter(top: Dp) {
	Column(Modifier
		.padding(top = top, start = 8.dp, end = 8.dp, bottom = 8.dp)
		.fillMaxHeight()
		.fillMaxWidth()
		.verticalScroll(rememberScrollState())
	) {

		var estateFrom by remember { mutableStateOf(Estate(), policy = neverEqualPolicy()) }
		var estateTo by remember { mutableStateOf(Estate(), policy = neverEqualPolicy()) }

		OutlinedDropDown(title = "Type", currentSelected = estateFrom.type, onValueSelected = {
			estateFrom.type = it
			estateFrom = estateFrom
		})

		OutlinedDropDown(title = "Status", currentSelected = estateFrom.status, onValueSelected = {
			estateFrom.status = it
			estateFrom = estateFrom
		})

		OutlinedFieldFromTo(
			title = "Surface",
			prop = Estate::surface,
			estateFrom = estateFrom,
			estateTo = estateTo,
			onValueFromChanged = { estateFrom = it },
			onValueToChanged = { estateTo = it }
		)

		OutlinedFieldFromTo(
			title = "Rooms",
			prop = Estate::rooms,
			estateFrom = estateFrom,
			estateTo = estateTo,
			onValueFromChanged = { estateFrom = it },
			onValueToChanged = { estateTo = it }
		)
	}
}

@Composable
inline fun <T, reified V> OutlinedFieldFromTo(
	title: String,
	prop: KMutableProperty1<T, V>,
	estateFrom: T,
	estateTo: T,
	crossinline onValueFromChanged: (T) -> (Unit),
	crossinline onValueToChanged: (T) -> (Unit),
) {
	Text(title, fontWeight = FontWeight.Bold, color = Color(0xffa0a0a0), modifier = Modifier.padding(8.dp))

	Row() {
		OutlinedTextField(
			value = prop.get(estateFrom).toString(),
			onValueChange = {
				when (V::class) {
					String::class -> {
						prop.set(estateFrom, it as V)
						onValueFromChanged(estateFrom)
					}
					Int::class -> {
						val integrer = it.toIntOrNull()
						if (integrer != null) {
							prop.set(estateFrom, integrer as V)
							onValueFromChanged(estateFrom)
						}
					}
					else -> {
					}
				}
			},
			modifier = Modifier
				.weight(1.0f),
			label = {
				Text("From")
			}
		)

		Spacer(Modifier
			.width(8.dp))

		OutlinedTextField(
			value = prop.get(estateTo).toString(),
			onValueChange = {
				when (V::class) {
					String::class -> {
						prop.set(estateTo, it as V)
						onValueToChanged(estateTo)
					}
					Int::class -> {
						val integrer = it.toIntOrNull()
						if (integrer != null) {
							prop.set(estateTo, integrer as V)
							onValueToChanged(estateTo)
						}
					}
					else -> {
					}
				}
			},
			modifier = Modifier
				.weight(1.0f),
			label = {
				Text("To")
			}
		)
	}
}