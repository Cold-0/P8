package com.cold0.realestatemanager.screens.commons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.ComposeUtils.estateFormat
import java.util.*
import kotlin.reflect.KMutableProperty1

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
inline fun <T, reified V> OutlinedFieldFromTo(
	prop: KMutableProperty1<T, V>,
	from: T,
	to: T,
	crossinline onValuesChanged: (T, T) -> (Unit),
) {
	Row() {
		when (V::class) {
			Date::class -> {
				// ------------------------------
				// DATE
				// ------------------------------
				OutlinedDatePickerButton(
					title = "From",
					onValueChange = {
						prop.set(from, it as V)
						onValuesChanged(from, to)
					},
					modifier = Modifier
						.weight(1.0f),
				)

				Spacer(Modifier
					.width(0.dp))

				OutlinedDatePickerButton(
					title = "To",
					onValueChange = {
						prop.set(to, it as V)
						onValuesChanged(from, to)
					},
					modifier = Modifier
						.weight(1.0f),
				)
			}
			else -> {
				// ------------------------------
				// OTHER (INT, STRING)
				// ------------------------------
				OutlinedTextField(
					value = prop.get(from).toString(),
					onValueChange = {
						when (V::class) {
							String::class -> {
								prop.set(from, it as V)
								onValuesChanged(from, to)
							}
							Int::class -> {
								val integrer = it.toIntOrNull()
								if (integrer != null) {
									prop.set(from, integrer as V)
									onValuesChanged(from, to)
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
					value = prop.get(to).toString(),
					onValueChange = {
						when (V::class) {
							String::class -> {
								prop.set(to, it as V)
								onValuesChanged(from, to)
							}
							Int::class -> {
								val integrer = it.toIntOrNull()
								if (integrer != null) {
									prop.set(to, integrer as V)
									onValuesChanged(from, to)
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

	}
}