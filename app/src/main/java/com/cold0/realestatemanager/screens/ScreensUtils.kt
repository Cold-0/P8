package com.cold0.realestatemanager.screens

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.BuildConfig
import com.cold0.realestatemanager.screens.converter.ConverterActivity
import com.cold0.realestatemanager.screens.photoviewer.PhotoViewerActivity
import java.util.*


@ExperimentalCoilApi
object ScreensUtils {
	@ExperimentalCoilApi
	fun openPhotoViewerActivity(context: Context, photo: String) {
		val intent = Intent(context, PhotoViewerActivity::class.java).apply {
			putExtra("img", photo)
		}
		ContextCompat.startActivity(context, intent, null)
	}

	fun openConverterActivity(context: Context) {
		val intent = Intent(context, ConverterActivity::class.java).apply {
			//putExtra("img", photo)
		}
		ContextCompat.startActivity(context, intent, null)
	}

	// --------------------------------------------
	// Fix register for Activity not sending result
	// --------------------------------------------
	@Composable
	fun <I, O> registerForActivityResult(
		contract: ActivityResultContract<I, O>,
		onResult: (O) -> Unit,
	): ActivityResultLauncher<I> {
		val owner = LocalContext.current as ActivityResultRegistryOwner
		val activityResultRegistry = owner.activityResultRegistry
		val currentOnResult = rememberUpdatedState(onResult)
		val key = remember { UUID.randomUUID().toString() }
		val realLauncher = remember { mutableStateOf<ActivityResultLauncher<I>?>(null) }
		val returnedLauncher = remember {
			object : ActivityResultLauncher<I>() {
				override fun launch(input: I, options: ActivityOptionsCompat?) {
					realLauncher.value?.launch(input, options)
				}

				override fun unregister() {
					realLauncher.value?.unregister()
				}

				override fun getContract() = contract
			}
		}
		DisposableEffect(activityResultRegistry, key, contract) {
			realLauncher.value = activityResultRegistry.register(key, contract) {
				currentOnResult.value(it)
			}
			onDispose {
				realLauncher.value?.unregister()
			}
		}
		return returnedLauncher
	}


	fun formatApiRequestGeoapify(
		width: Int = 400,
		height: Int = 400,
		localisation: String = "-74.005157,40.710785",
		apiKey: String = BuildConfig.GEOAPIFY_KEY,
	): String {
		return "https://maps.geoapify.com/v1/staticmap" +
				"?style=osm-bright-grey" +
				"&width=$width&height=$height" +
				"&center=lonlat:$localisation" +
				"&zoom=16.4226&pitch=44" +
				"&marker=lonlat:$localisation;color:%23ff0000;size:medium" +
				"&apiKey=$apiKey"
	}
}