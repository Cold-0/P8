package com.cold0.realestatemanager.screens.commons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.reflect.KMutableProperty1

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