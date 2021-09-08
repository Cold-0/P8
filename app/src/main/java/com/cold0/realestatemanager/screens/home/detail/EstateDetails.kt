package com.cold0.realestatemanager.screens.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.ComposeUtils.estateFormat
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.EstateStatus
import com.google.android.libraries.maps.model.LatLng

@ExperimentalCoilApi
@Composable
fun EstateDetails(estate: Estate) {
	val configuration = LocalConfiguration.current
	val small = configuration.screenWidthDp <= 450
	Column(Modifier
		.padding(16.dp, 0.dp)
		.verticalScroll(rememberScrollState())) {
		// ----------------------------
		// Info
		// ----------------------------
		Row(modifier = Modifier.fillMaxSize()) {
			// ----------------------------
			// Column 1
			// ----------------------------
			Column(Modifier
				.weight(1.0f)
				.fillMaxWidth()
				.padding(8.dp)) {
				OutlinedText(value = estate.added.estateFormat(), modifier = Modifier.fillMaxWidth(), title = "Added")
			}
			// ----------------------------
			// Column 2
			// ----------------------------
			Column(Modifier
				.weight(1.0f)
				.fillMaxWidth()
				.padding(8.dp)) {
				val stringToPrint = if (estate.status == EstateStatus.Available)
					"Available"
				else "Sold on " + estate.sold.estateFormat()
				OutlinedText(stringToPrint, Modifier.fillMaxWidth(), title = "Status")
			}
		}

		// ----------------------------
		// Media
		// ----------------------------
		if (estate.photos.isNotEmpty())
			LazyRow {
				items(estate.photos) { photo ->
					EstateDetailPhotoItem(photo)
				}
			}
		else
			Box(Modifier
				.height(108.dp)
				.fillMaxWidth())
			{
				Column(Modifier.align(Alignment.Center)) {
					Text("No Photo Available", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), color = Color.LightGray)
					Box(modifier = Modifier.fillMaxWidth())
					{
						Row(modifier = Modifier.align(Alignment.Center)) {
							Text("Press on the ", color = Color.LightGray)
							Image(imageVector = Icons.Default.Edit, contentDescription = null, colorFilter = ColorFilter.tint(Color.LightGray))
							Text(" icon on the toolbar to add more photo in Edit Mode", color = Color.LightGray)
						}
					}
				}
			}
		// ----------------------------
		// Description
		// ----------------------------
		OutlinedText(estate.description, title = stringResource(R.string.description), modifier = Modifier.fillMaxWidth())
		Row(modifier = Modifier.fillMaxSize()) {
			// ----------------------------
			// Column 1
			// ----------------------------
			Column(Modifier
				.weight(1.0f)
				.padding(8.dp)) {
				OutlinedText(estate.surface.toString(), leadingIcon = Icons.Default.Face, title = stringResource(R.string.surface))
				OutlinedText(estate.rooms.toString(), leadingIcon = Icons.Default.Person, title = stringResource(R.string.rooms))
				OutlinedText(estate.bathrooms.toString(), leadingIcon = Icons.Default.Info, title = stringResource(R.string.bathrooms))
				OutlinedText(estate.bedrooms.toString(), leadingIcon = Icons.Default.AccountBox, title = stringResource(R.string.bedrooms))
			}
			// ----------------------------
			// Column 2
			// ----------------------------
			Column(Modifier
				.weight(1.0f)
				.padding(8.dp)) {
				OutlinedText(estate.address, leadingIcon = Icons.Default.Place, title = stringResource(R.string.location))
				OutlinedText(estate.interest, leadingIcon = Icons.Default.Info, title = stringResource(R.string.point_of_interest))
				OutlinedText(estate.agent, leadingIcon = Icons.Default.ManageAccounts, title = stringResource(R.string.agent))
			}
			// ----------------------------
			// Column 3 - Minimap
			// ----------------------------
			if (!small) // If DPI width is big
				Column(Modifier
					.weight(1.0f)
					.padding(8.dp)
					.fillMaxHeight()) {
					EstateDetailMinimap(LatLng(estate.latitude, estate.longitude))
				}
		}
		if (small) // If DPI width is small
			EstateDetailMinimap(LatLng(estate.latitude, estate.longitude))
	}
}