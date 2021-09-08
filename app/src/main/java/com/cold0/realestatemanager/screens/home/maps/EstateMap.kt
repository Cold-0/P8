package com.cold0.realestatemanager.screens.home.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.cold0.realestatemanager.model.Estate
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EstateMap(list: List<Estate>, modifier: Modifier = Modifier) {
	val mapView = rememberMapViewWithLifecycle()
	AndroidView(factory = { mapView }, modifier = modifier) { view ->
		CoroutineScope(Dispatchers.Main).launch {
			val googleMap = view.awaitMap()
			list.forEach {
				val latlng = LatLng(it.latitude, it.longitude)
				val markerOptionsDestination = MarkerOptions()
					.title(it.address)
					.position(latlng)
				googleMap.addMarker(markerOptionsDestination)
			}
		}
	}
}