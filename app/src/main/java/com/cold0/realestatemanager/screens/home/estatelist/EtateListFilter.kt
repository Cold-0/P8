package com.cold0.realestatemanager.screens.home.estatelist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
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
import com.cold0.realestatemanager.screens.commons.OutlinedFieldFromTo
import java.util.*
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

		var estateFrom by remember { mutableStateOf(Estate(agent = "", surface = 50, rooms = 0, bedrooms = 0, bathrooms = 1), policy = neverEqualPolicy()) }
		var estateTo by remember { mutableStateOf(Estate(agent = "", surface = 250, rooms = 7, bedrooms = 2, bathrooms = 5), policy = neverEqualPolicy()) }

		var mapOfIntProps by remember {
			@Suppress("UNCHECKED_CAST")
			mutableStateOf(mutableMapOf<KMutableProperty1<Estate, Int>, Boolean>(
				Estate::surface to false,
				Estate::rooms to false,
				Estate::bathrooms to false,
				Estate::bedrooms to false,
				Estate::surface to false,
			))
		}

		var mapOfStringProps by remember {
			@Suppress("UNCHECKED_CAST")
			mutableStateOf(mutableMapOf(
				Estate::agent to false
			))
		}


		var mapOfDateProps by remember {
			@Suppress("UNCHECKED_CAST")
			mutableStateOf(mutableMapOf(
				Estate::timestamp as KMutableProperty1<Estate, Date> to false,
				Estate::dateSold as KMutableProperty1<Estate, Date> to false,
			))
		}

		OutlinedDropDown(title = "Type", currentSelected = estateFrom.type, onValueSelected = {
			estateFrom.type = it
			estateFrom = estateFrom
		})

		OutlinedDropDown(title = "Status", currentSelected = estateFrom.status, onValueSelected = {
			estateFrom.status = it
			estateFrom = estateFrom
		})


		// -------------------
		// DATEFIELD
		// -------------------
		mapOfDateProps.keys.forEach { field ->
			var checkbox by remember { mutableStateOf(false) }
			Row(Modifier.padding(top = 8.dp)) {
				Checkbox(checked = checkbox, onCheckedChange = { checkbox = it; mapOfDateProps[field] = checkbox; mapOfDateProps = mapOfDateProps })
				Text(field.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
					fontWeight = FontWeight.Bold,
					color = Color(0xffa0a0a0),
					modifier = Modifier.padding(start = 8.dp))
			}
			OutlinedFieldFromTo(prop = field, from = estateFrom, to = estateTo, onValuesChanged = { from, to -> estateFrom = from; estateTo = to })
		}

		// -------------------
		// INTFIELD
		// -------------------
		mapOfIntProps.keys.forEach { field ->
			var checkbox by remember { mutableStateOf(false) }
			Row(Modifier.padding(top = 8.dp)) {
				Checkbox(checked = checkbox, onCheckedChange = { checkbox = it; mapOfIntProps[field] = checkbox; mapOfIntProps = mapOfIntProps })
				Text(field.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
					fontWeight = FontWeight.Bold,
					color = Color(0xffa0a0a0),
					modifier = Modifier.padding(start = 8.dp))
			}
			OutlinedFieldFromTo(prop = field, from = estateFrom, to = estateTo, onValuesChanged = { from, to -> estateFrom = from; estateTo = to })
		}

		// -------------------
		// STRING FIELDS
		// -------------------
		mapOfStringProps.keys.forEach { field ->
			var checkbox by remember { mutableStateOf(false) }
			Row(Modifier.padding(top = 8.dp)) {
				Checkbox(checked = checkbox, onCheckedChange = { checkbox = it; mapOfStringProps[field] = checkbox; mapOfStringProps = mapOfStringProps })
				Text(field.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
					fontWeight = FontWeight.Bold,
					color = Color(0xffa0a0a0),
					modifier = Modifier.padding(start = 8.dp))
			}
			OutlinedFieldFromTo(prop = field, from = estateFrom, to = estateTo, onValuesChanged = { from, to -> estateFrom = from; estateTo = to })
		}


//		OutlinedFieldFromTo(
//			title = "Rooms",
//			prop = Estate::rooms,
//			estateFrom = estateFrom,
//			estateTo = estateTo,
//			onValueFromChanged = { estateFrom = it },
//			onValueToChanged = { estateTo = it }
//		)
//
//		OutlinedFieldFromTo(
//			title = "Rooms",
//			prop = Estate::rooms,
//			estateFrom = estateFrom,
//			estateTo = estateTo,
//			onValueFromChanged = { estateFrom = it },
//			onValueToChanged = { estateTo = it }
//		)
	}
}
