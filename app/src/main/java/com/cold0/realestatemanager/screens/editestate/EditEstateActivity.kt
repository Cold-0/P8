package com.cold0.realestatemanager.screens.editestate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.screens.commons.TopBarReturn
import com.cold0.realestatemanager.screens.home.estatedetail.EstateDetailMinimap
import com.cold0.realestatemanager.theme.RealEstateManagerTheme


@ExperimentalCoilApi
class EditEstateActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			var estate by remember { mutableStateOf(this.intent.extras?.getParcelable<Estate>("estate")!!, policy = neverEqualPolicy()) }
			RealEstateManagerTheme {
				TopBarReturn(this, "Edit Estate") {
					Box {
						EditEstateMain(estate, onEstateChange = { estate = it })

						// ----------------------------
						// Save Float Button
						// ----------------------------
						FloatingActionButton(
							modifier = Modifier
								.align(Alignment.BottomEnd)
								.padding(8.dp),
							onClick = {
								val result = Intent().putExtra("estateReturn",
									estate)
								setResult(Activity.RESULT_OK, result)
								finish()
							}) {
							Icon(Icons.Filled.Save, "Save current Estate", tint = Color.White)
						}
					}
				}
			}
		}
	}
}

@ExperimentalCoilApi
@Composable
private fun EditEstateMain(estate: Estate, onEstateChange: (Estate) -> Unit) {
	val configuration = LocalConfiguration.current
	val small = configuration.screenWidthDp <= 450

	Column(Modifier
		.padding(16.dp)
		.verticalScroll(rememberScrollState())) {

		// ----------------------------
		// Photo List
		// ----------------------------
		Text(
			text = stringResource(R.string.media),
			style = MaterialTheme.typography.h5
		)
		EditEstatePhotoList(estate, onEstateChange = { onEstateChange(it) })

		// ----------------------------
		// Description
		// ----------------------------
		Text(text = stringResource(R.string.description), style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
		TextField(
			value = estate.description,
			onValueChange = { estate.description = it; onEstateChange(estate) },
			modifier = Modifier.padding(top = 16.dp)
		)
		Spacer(modifier = Modifier.height(32.dp))
		Row(modifier = Modifier.fillMaxSize()) {
			// ----------------------------
			// First Column
			// ----------------------------
			Column(Modifier
				.weight(1.0f)
				.padding(8.dp)) {
				EditEstateField(Icons.Default.Face, stringResource(R.string.surface), estate.surface.toString(), keyboardType = KeyboardType.Number) {
					val new = it.toIntOrNull()
					if (new != null)
						estate.surface = new
					onEstateChange(estate)
				}
				EditEstateField(Icons.Default.Person,
					stringResource(R.string.number_of_rooms),
					estate.numberOfRooms.toString(),
					keyboardType = KeyboardType.Number) {
					val new = it.toIntOrNull()
					if (new != null)
						estate.numberOfRooms = new
					onEstateChange(estate)
				}
				EditEstateField(Icons.Default.Info,
					stringResource(R.string.number_of_bathrooms),
					estate.numberOfBathrooms.toString(),
					keyboardType = KeyboardType.Number) {
					val new = it.toIntOrNull()
					if (new != null)
						estate.numberOfBathrooms = new
					onEstateChange(estate)
				}
				EditEstateField(Icons.Default.AccountBox,
					stringResource(R.string.number_of_bedrooms),
					estate.numberOfBedrooms.toString(),
					keyboardType = KeyboardType.Number) {
					val new = it.toIntOrNull()
					if (new != null)
						estate.numberOfBedrooms = new
					onEstateChange(estate)
				}
			}

			// ----------------------------
			// Second Column
			// ----------------------------
			Column(Modifier
				.weight(1.0f)
				.padding(8.dp)) {
				EditEstateField(Icons.Default.LocationOn, stringResource(R.string.location), estate.address) {
					estate.address = it
					onEstateChange(estate)
				}
				EditEstateField(Icons.Default.Info, stringResource(R.string.point_of_interest), estate.interest) {
					estate.interest = it
					onEstateChange(estate)
				}
				EditEstateField(Icons.Default.ManageAccounts, stringResource(R.string.agent), estate.agent) {
					estate.agent = it
					onEstateChange(estate)
				}
			}

			// ----------------------------
			// Minimap
			// ----------------------------
			if (!small)
				Column(Modifier
					.weight(1.0f)
					.padding(8.dp), verticalArrangement = Arrangement.Top) {
					EstateDetailMinimap(estate.location)
				}
			else
				EstateDetailMinimap(estate.location)
		}
	}
}
