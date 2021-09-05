package com.cold0.realestatemanager.screens.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.MapsHomeWork
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun <E : Enum<E>> DropDownMenu(title: String, currentSelected: Enum<E>, list: List<Enum<E>>, onValueSelected: (Enum<E>) -> (Unit)) {
	var expanded by remember { mutableStateOf(false) }
	var selectedText by remember { mutableStateOf(currentSelected.toString()) }
	var textfieldSize by remember { mutableStateOf(Size.Zero) }

	val icon = if (expanded)
		Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
	else
		Icons.Filled.ArrowDropDown

	Column(Modifier.padding(horizontal = 0.dp, vertical = 8.dp)) {
		Row {
			Icon(Icons.Default.MapsHomeWork, "")
			Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
		}
		Box(Modifier
			.fillMaxWidth()
			.padding(2.dp)
			.onGloballyPositioned { coordinates ->
				textfieldSize = coordinates.size.toSize()
			}
			.clickable {
				expanded = !expanded
			}) {

			Surface(color = Color.Transparent, modifier = Modifier
				.fillMaxSize()
				.align(Alignment.Center)
				.border(BorderStroke(1.dp, Color.DarkGray), RoundedCornerShape(20))) {
				Text(text = selectedText, modifier = Modifier.padding(16.dp))
			}
			Icon(icon, "contentDescription",
				Modifier
					.align(Alignment.CenterEnd)
					.padding(end = 8.dp))
		}
		DropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false },
			modifier = Modifier
				.width(with(LocalDensity.current) { textfieldSize.width.toDp() })
		) {
			list.forEach { enumValue ->
				DropdownMenuItem(onClick = {
					selectedText = enumValue.toString()
					onValueSelected(enumValue)
					expanded = false
				}) {
					Text(text = enumValue.toString())
				}
			}
		}
	}
}