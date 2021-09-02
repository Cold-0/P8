package com.cold0.realestatemanager.screens.editestate

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.Photo
import com.cold0.realestatemanager.screens.commons.TopBarReturn
import com.cold0.realestatemanager.screens.home.estatedetail.EstateDetailMinimap
import com.cold0.realestatemanager.screens.home.estatedetail.EstateDetailPhotoItem
import com.cold0.realestatemanager.theme.RealEstateManagerTheme


@ExperimentalCoilApi
class EditEstateActivity : ComponentActivity() {

	private val TAG = "EditEstateActivity"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {


			var estate by remember { mutableStateOf(this.intent.extras?.getParcelable<Estate>("estate")!!, policy = neverEqualPolicy()) }
			RealEstateManagerTheme {
				TopBarReturn(this, "Edit Estate") {

					Column(Modifier
						.padding(16.dp)
						.verticalScroll(rememberScrollState())) {
						Text(
							text = stringResource(R.string.media),
							style = MaterialTheme.typography.h5
						)
						LazyRow {
							itemsIndexed(estate.pictures) { index, photo ->
								Column() {
									Row(modifier = Modifier.padding(4.dp)) {
										Button(shape = RoundedCornerShape(20.dp), onClick = {
											estate.pictures -= photo
											estate = estate
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
															estate = estate
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
							item() {
								GetImageFromLibrary(onPhotoSelected = { photo ->
									estate.pictures += photo
									estate = estate
								})
							}
						}
						Text(text = stringResource(R.string.description), style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
						TextField(
							value = estate.description,
							onValueChange = { estate.description = it; estate = estate },
							modifier = Modifier.padding(top = 16.dp)
						)
						Spacer(modifier = Modifier.height(32.dp))
						Row(modifier = Modifier.fillMaxSize()) {
							Column(Modifier.weight(1.0f)) {
								EditEstateField(Icons.Default.Face, stringResource(R.string.surface), estate.surface.toString()) {
									val new = it.toIntOrNull()
									if (new != null)
										estate.surface = new
									estate = estate
								}
								EditEstateField(Icons.Default.Person, stringResource(R.string.number_of_rooms), estate.numberOfRooms.toString()) {
									val new = it.toIntOrNull()
									if (new != null)
										estate.numberOfRooms = new
									estate = estate
								}
								EditEstateField(Icons.Default.Info, stringResource(R.string.number_of_bathrooms), estate.numberOfBathrooms.toString()) {
									val new = it.toIntOrNull()
									if (new != null)
										estate.numberOfBathrooms = new
									estate = estate
								}
								EditEstateField(Icons.Default.AccountBox, stringResource(R.string.number_of_bedrooms), estate.numberOfBedrooms.toString()) {
									val new = it.toIntOrNull()
									if (new != null)
										estate.numberOfBedrooms = new
									estate = estate
								}
							}
							Column(Modifier.weight(1.0f)) {
								EditEstateField(Icons.Default.LocationOn, stringResource(R.string.location), estate.address) {
									estate.address = it
									estate = estate
								}
								EditEstateField(Icons.Default.Info, stringResource(R.string.point_of_interest), estate.interest) {
									estate.interest = it
									estate = estate
								}
							}
							Column(Modifier.weight(1.0f), verticalArrangement = Arrangement.Top) {
								EstateDetailMinimap(estate.location)
							}
						}
					}
				}
			}
		}
	}
}

@ExperimentalCoilApi
@Composable
fun GetImageFromLibrary(onPhotoSelected: (Photo) -> (Unit)) {
	val context = LocalContext.current

	var imageUri by remember { mutableStateOf<Uri?>(null) }
	val bitmap = remember { mutableStateOf<Bitmap?>(null) }
	var enteringName by remember { mutableStateOf("") }
	var openNameDialog by remember { mutableStateOf(false) }
	val launcherGallery = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
		imageUri = uri
		openNameDialog = true
	}

	val launcherCamera = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
		bitmap.value = it
		openNameDialog = true
	}

	Column(modifier = Modifier.fillMaxHeight()) {
		IconButton(
			onClick = {
				launcherGallery.launch("image/*")
			},
			modifier = Modifier.fillMaxSize(),
			content = {
				Icon(imageVector = Icons.Default.Image, contentDescription = "Add picture from galery to Estate", modifier = Modifier
					.fillMaxSize())
			}
		)

		IconButton(
			onClick = {
				launcherCamera.launch()
			},
			modifier = Modifier.fillMaxSize(),
			content = {
				Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = "Add picture from camera to Estate", modifier = Modifier
					.fillMaxSize())
			}
		)
	}

	if (openNameDialog)

		AlertDialog(
			onDismissRequest = {

			},
			title = {
				Text(text = "Set Photo name")
			},
			text = {
				Spacer(modifier = Modifier.height(64.dp))
				TextField(
					value = enteringName,
					onValueChange = {
						enteringName = it
					},
					label = { Text("Photo Name") },
					modifier = Modifier
						.padding(8.dp)
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
						}
						imageUri = null

						bitmap.value?.let { btm ->
							onPhotoSelected(Photo(enteringName, "", btm))
						}
					}) {
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