package com.cold0.realestatemanager.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.Photo
import com.cold0.realestatemanager.screens.ScreensUtils

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
                EstateDetailsPhoto(photo)
            }
        }
        Text(text = stringResource(R.string.description), style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
        Text(text = estate.description, style = MaterialTheme.typography.body2, modifier = Modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxSize()) {
            Column(Modifier.weight(1.0f)) {
                EstateDetailsInfoItem(Icons.Default.Face, stringResource(R.string.surface), estate.surface.toString())
                EstateDetailsInfoItem(Icons.Default.Person, stringResource(R.string.number_of_rooms), estate.numberOfRooms.toString())
                EstateDetailsInfoItem(Icons.Default.Info, stringResource(R.string.number_of_bathrooms), estate.numberOfBathrooms.toString())
                EstateDetailsInfoItem(Icons.Default.AccountBox, stringResource(R.string.number_of_bedrooms), estate.numberOfBedrooms.toString())
            }
            Column(Modifier.weight(1.0f)) {
                EstateDetailsInfoItem(Icons.Default.LocationOn, stringResource(R.string.location), estate.address, leftSpacing = 24.dp)
            }
            Column(Modifier.weight(1.0f), verticalArrangement = Arrangement.Top) {
                EstateDetailsMinimap(estate.location)
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun EstateDetailsMinimap(localisation: String) {
    val context = LocalContext.current
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
                ScreensUtils.openPhotoViewerActivity(context, ScreensUtils.formatApiRequestGeoapify(1024, 1024, localisation))
            },
        contentDescription = stringResource(R.string.content_description_mini_map_preview),
    )
}

@Composable
fun EstateDetailsInfoItem(icon: ImageVector, title: String, value: String, leftSpacing: Dp = 64.dp, bottomSpacing: Dp = 8.dp) {
    Row {
        Image(icon, icon.name)
        Text(text = title, modifier = Modifier.padding(paddingValues = PaddingValues(start = 8.dp)))
    }
    Row {
        Spacer(Modifier.width(leftSpacing))
        Text(text = value, textAlign = TextAlign.Center)
    }
    Spacer(Modifier.height(bottomSpacing))
}

@ExperimentalCoilApi
@Composable
fun EstateDetailsPhoto(photo: Photo) {
    val image = rememberImagePainter(photo.url)
    val context = LocalContext.current
    Card(elevation = 4.dp, modifier = Modifier
        .padding(8.dp)
        .clickable {
            ScreensUtils.openPhotoViewerActivity(context, photo.url)
        }
    )
    {
        Box {
            Image(
                image,
                contentDescription = photo.name,
                Modifier.size(108.dp)
            )
            Surface(
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(108.dp),
            ) {
                Text(
                    text = photo.name,
                    style = MaterialTheme.typography.subtitle1.copy(color = Color.White),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp),
                )
            }
        }
    }
}