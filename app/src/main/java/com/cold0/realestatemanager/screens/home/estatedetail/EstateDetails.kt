package com.cold0.realestatemanager.screens.home.estatedetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate

@ExperimentalCoilApi
@Composable
fun EstateDetails(estate: Estate) {
	Column(Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
		Text(
			text = stringResource(R.string.media),
			style = MaterialTheme.typography.h5
		)
		LazyRow {
			items(estate.pictures) { photo ->
				EstateDetailPhotoItem(photo)
			}
		}
		Text(text = stringResource(R.string.description), style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
		Text(text = estate.description, style = MaterialTheme.typography.body2, modifier = Modifier.padding(top = 16.dp))
		Spacer(modifier = Modifier.height(32.dp))
		Row(modifier = Modifier.fillMaxSize()) {
			Column(Modifier.weight(1.0f)) {
				EstateDetailInfoLabel(Icons.Default.Face, stringResource(R.string.surface), estate.surface.toString())
				EstateDetailInfoLabel(Icons.Default.Person, stringResource(R.string.number_of_rooms), estate.numberOfRooms.toString())
				EstateDetailInfoLabel(Icons.Default.Info, stringResource(R.string.number_of_bathrooms), estate.numberOfBathrooms.toString())
				EstateDetailInfoLabel(Icons.Default.AccountBox, stringResource(R.string.number_of_bedrooms), estate.numberOfBedrooms.toString())
			}
			Column(Modifier.weight(1.0f)) {
				EstateDetailInfoLabel(Icons.Default.LocationOn, stringResource(R.string.location), estate.address, leftSpacing = 24.dp)
			}
			Column(Modifier.weight(1.0f), verticalArrangement = Arrangement.Top) {
				EstateDetailMinimap(estate.location)
			}
		}
	}
}