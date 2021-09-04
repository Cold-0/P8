package com.cold0.realestatemanager.screens.home.estatedetail

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
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Photo
import com.cold0.realestatemanager.screens.ScreensUtils

@ExperimentalCoilApi
@Composable
fun EstateDetailMinimap(localisation: String) {
	val context = LocalContext.current
	Box(modifier = Modifier.fillMaxSize()) {
		Image(
			rememberImagePainter(
				data = ScreensUtils.formatApiRequestGeoapify(400, 400, localisation),
				builder = {
					placeholder(R.drawable.ic_launcher_background)
					error(R.drawable.ic_launcher_foreground)
				}
			),
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.size(250.dp)
				.clickable {
					ScreensUtils.openPhotoViewerActivity(context, Photo(onlineUrl = ScreensUtils.formatApiRequestGeoapify(1024, 1024, localisation)))
				}
				.align(Alignment.Center),
			contentDescription = stringResource(R.string.content_description_mini_map_preview),
		)
	}
}