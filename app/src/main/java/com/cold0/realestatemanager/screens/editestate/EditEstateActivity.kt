package com.cold0.realestatemanager.screens.editestate

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.Photo
import com.cold0.realestatemanager.screens.commons.TopBarReturn
import com.cold0.realestatemanager.screens.home.estatedetail.EstateDetailMinimap
import com.cold0.realestatemanager.screens.home.estatedetail.EstateDetailPhotoItem
import com.cold0.realestatemanager.theme.RealEstateManagerTheme
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextInt


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
			}

			// ----------------------------
			// Minimap
			// ----------------------------
			Column(Modifier
				.weight(1.0f)
				.padding(8.dp), verticalArrangement = Arrangement.Top) {
				EstateDetailMinimap(estate.location)
			}
		}
	}
}

@ExperimentalCoilApi
@Composable
fun EditEstatePhotoList(estate: Estate, onEstateChange: (Estate) -> (Unit)) {
	LazyRow {
		itemsIndexed(estate.pictures) { index, photo ->
			Column {
				Row(modifier = Modifier.padding(4.dp)) {
					Button(shape = RoundedCornerShape(20.dp), onClick = {
						estate.pictures -= photo
						onEstateChange(estate)
					}) {
						Icon(imageVector = Icons.Filled.Delete,
							contentDescription = "Remove picture from Estate",
							tint = Color.White)
					}
					var openDialog by remember { mutableStateOf(false) }
					Button(shape = RoundedCornerShape(20.dp), onClick = {
						openDialog = true

					}) {
						Icon(imageVector = Icons.Filled.Edit,
							contentDescription = "Rename picture from Estate",
							tint = Color.White)
					}

					if (openDialog) {
						var newName by remember { mutableStateOf("") }

						AlertDialog(
							onDismissRequest = { openDialog = false },
							title = { Text(text = "Dialog Title") },
							text = {
								Spacer(modifier = Modifier.height(64.dp))
								TextField(
									value = newName,
									onValueChange = {
										newName = it
									},
									label = { Text("Photo Name") },
									modifier = Modifier
										.padding(8.dp)
								)
							},
							confirmButton = {
								Button(
									onClick = {
										val mList = estate.pictures.toMutableList()
										photo.name = newName
										mList[index] = photo
										onEstateChange(estate)
										openDialog = false
									}
								) {
									Text("Confirm New Name")
								}
							},
							dismissButton = {
								Button(
									onClick = { openDialog = false }) {
									Text("Cancel")

								}
							}
						)
					}
				}
				EstateDetailPhotoItem(photo)
			}
		}
		item {
			EditEstatePhotoGetter(onPhotoSelected = { photo ->
				estate.pictures += photo
				onEstateChange(estate)
			})
		}
	}
}

@SuppressLint("SimpleDateFormat")
@ExperimentalCoilApi
@Composable
fun EditEstatePhotoGetter(onPhotoSelected: (Photo) -> (Unit)) {
	val context = LocalContext.current

	var imageUri by remember { mutableStateOf<Uri?>(null) }
	val bitmap = remember { mutableStateOf<Bitmap?>(null) }
	var enteringName by remember { mutableStateOf("") }
	var openNameDialog by remember { mutableStateOf(false) }

	val launcherGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
		if (uri != null) {
			imageUri = uri
			openNameDialog = true
		} else {
			imageUri = null
			openNameDialog = false
		}
	}
	val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
		if (it != null) {
			bitmap.value = it
			openNameDialog = true
		} else {
			bitmap.value = null
			openNameDialog = false
		}
	}

	Column(modifier = Modifier.fillMaxHeight()) {
		IconButton(
			onClick = { launcherGallery.launch("image/*") },
			modifier = Modifier.fillMaxSize(),
			content = {
				Icon(
					imageVector = Icons.Default.Image,
					contentDescription = "Add picture from gallery to Estate",
					modifier = Modifier.fillMaxSize())
			}
		)

		IconButton(
			onClick = { launcherCamera.launch() },
			modifier = Modifier.fillMaxSize(),
			content = {
				Icon(
					imageVector = Icons.Default.AddAPhoto,
					contentDescription = "Add picture from camera to Estate",
					modifier = Modifier.fillMaxSize())
			}
		)
	}

	if (openNameDialog) AlertDialog(
		onDismissRequest = {},
		title = { Text(text = "Set Photo name") },
		text = {
			TextField(
				value = enteringName,
				onValueChange = { enteringName = it },
				label = { Text("Photo Name") },
				modifier = Modifier.padding(8.dp)
			)
		},
		confirmButton = {
			Button(
				onClick = {
					imageUri?.let {
						if (Build.VERSION.SDK_INT < 28) {
							bitmap.value = MediaStore.Images
								.Media.getBitmap(context.contentResolver, it)
						} else {
							val source = ImageDecoder
								.createSource(context.contentResolver, it)
							bitmap.value = ImageDecoder.decodeBitmap(source)
						}
						imageUri = null
					}

					val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + nextInt() % 9998 + ".jpg"
					val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
					val copyDestination = File(storageDir, timeStamp)
					val fOut = FileOutputStream(copyDestination);
					bitmap.value?.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

					onPhotoSelected(Photo(enteringName, "", copyDestination.absolutePath))
				}
			) {
				Text("Add Photo")
			}
		},
		dismissButton = {
			Button(
				onClick = {
					openNameDialog = false
				}) {
				Text("Cancel")
			}
		}
	)
}