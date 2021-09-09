package com.cold0.realestatemanager.screens.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.cold0.realestatemanager.ComposeUtils
import com.cold0.realestatemanager.ComposeUtils.format
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Photo
import com.google.android.libraries.maps.model.LatLng

@ExperimentalCoilApi
@Composable
fun EstateDetailMinimap(localisation: LatLng) {
	val context = LocalContext.current
	Box(modifier = Modifier.fillMaxSize()) {
		Image(
			rememberImagePainter(
				data = ComposeUtils.formatApiRequestStaticMap(400, 400, 19, "${localisation.latitude.format(6)},${localisation.longitude.format(6)}"),
				builder = {
					placeholder(R.drawable.ic_launcher_background)
					error(R.drawable.ic_launcher_foreground)
				}
			),
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.size(250.dp)
				.clickable {
					ComposeUtils.openPhotoViewerActivity(context,
						Photo(onlineUrl = ComposeUtils.formatApiRequestStaticMap(1024, 1024, 14, "${localisation.latitude.format(6)},${localisation.longitude.format(6)}")))
				}
				.align(Alignment.Center),
			contentDescription = stringResource(R.string.content_description_mini_map_preview),
		)
	}
}