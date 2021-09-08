package com.cold0.realestatemanager.screens.home

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.ComposeUtils
import com.cold0.realestatemanager.ComposeUtils.registerForActivityResult
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.EstateStatus
import com.cold0.realestatemanager.notifications.NotificationHelper
import com.cold0.realestatemanager.screens.commons.OutlinedDatePickerButton
import com.cold0.realestatemanager.screens.editestate.EditEstateActivity
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalPermissionsApi
@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun HomeTopBar(
	viewModel: HomeViewModel,
	listEstate: List<Estate>?,
	mapOpen: Boolean,
	toggleDrawer: () -> Unit,
	toggleMap: () -> Unit,
	content: @Composable () -> Unit,
) {
	Column {
		TopAppBar(
			elevation = 4.dp,
			title = { Text(if (!mapOpen) "Home" else "Map") },
			navigationIcon = {
				if (!mapOpen) {
					if (!listEstate.isNullOrEmpty())
						IconButton(onClick = { toggleDrawer() }) {
							Icon(Icons.Filled.Menu, contentDescription = stringResource(R.string.content_description_open_left_list), tint = Color.White)
						}
				} else {
					IconButton(onClick = { toggleMap() }) {
						Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.content_description_open_left_list), tint = Color.White)
					}
				}
			},
			actions = {
				val context = LocalContext.current
				val intent = Intent(context, EditEstateActivity::class.java)

				// ----------------------------
				// Launcher for Edit Menu
				// ----------------------------
				val launcherEdit = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), onResult = { result ->
					if (result.resultCode == Activity.RESULT_OK) {
						result.data?.getParcelableExtra<Estate>("estateReturn")?.let {
							viewModel.updateEstate(it)
						}
					}
				})
				// ----------------------------
				// Launcher for Add Menu
				// ----------------------------
				val launcherAdd = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), onResult = { result ->
					if (result.resultCode == Activity.RESULT_OK) {
						result.data?.getParcelableExtra<Estate>("estateReturn")?.let {
							viewModel.addEstate(it)
							viewModel.setSelectedEstate(it.uid)
							NotificationHelper.sendSimpleNotification(
								context = context,
								title = "Real Estate Manager",
								message = "Succefully added new Estate",
								intent = Intent(context, HomeActivity::class.java),
								reqCode = 10001
							)
						}
					}
				})

				// ----------------------------
				// Add Button
				// ----------------------------
				if (!mapOpen)
					IconButton(
						onClick = {
							intent.putExtra("estate", Estate())
							intent.putExtra("title", "Add Estate")
							launcherAdd.launch(intent)
						},
					) {
						Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.content_description_add_real_estate), tint = Color.White)
					}

				// ----------------------------
				// Edit Button (Only show if Estate list isn't Empty)
				// ----------------------------
				if (!listEstate.isNullOrEmpty() && !mapOpen)
					IconButton(onClick = {
						intent.putExtra("estate", viewModel.getSelectedEstate())
						intent.putExtra("title", "Edit Estate")
						launcherEdit.launch(intent)
					}) {
						Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.content_description_edit_current_selected_estate), tint = Color.White)
					}

				// ----------------------------
				// MAP
				// ----------------------------
				if (!mapOpen)
					IconButton(
						onClick = {
							toggleMap()
						},
					) {
						Icon(imageVector = Icons.Filled.LocationOn, contentDescription = stringResource(R.string.content_description_add_real_estate), tint = Color.White)
					}

				// ----------------------------
				// More Vertical Button and Drop Down Menu (Only show if Estate list isn't Empty)
				// ----------------------------
				var threeDotExpanded by remember { mutableStateOf(false) }

				if (!listEstate.isNullOrEmpty() && !mapOpen) {
					IconButton(
						onClick = {
							threeDotExpanded = !threeDotExpanded
						},
					) {
						Icon(imageVector = Icons.Filled.MoreVert, contentDescription = stringResource(R.string.content_description_add_real_estate), tint = Color.White)
					}

					var openDialog by remember { mutableStateOf(false) }
					val estate = viewModel.getSelectedEstate()
					if (openDialog) {
						AlertDialog(
							modifier = Modifier.padding(8.dp),
							onDismissRequest = {},
							title = { Text("Sold Date") },
							text = {
								Box(modifier = Modifier.fillMaxWidth()) {
									OutlinedDatePickerButton(Modifier.align(Alignment.Center)) {
										estate.sold = it
									}
								}
							},
							confirmButton = {
								Button(onClick = {
									openDialog = false
									estate.status = EstateStatus.Sold
									viewModel.updateEstate(estate)
								}) {
									Text("Ok")
								}
							},
							dismissButton = {
								Button(onClick = { openDialog = false }) {
									Text("Cancel")
								}
							}
						)
					}

					DropdownMenu(
						offset = DpOffset((-4).dp, 4.dp),
						expanded = threeDotExpanded,
						onDismissRequest = { threeDotExpanded = false },
					) {
						if (estate.status == EstateStatus.Available) {
							DropdownMenuItem(onClick = { openDialog = true }) {
								Text("Mark as sold")
							}
							Divider()
						}
						DropdownMenuItem(onClick = { ComposeUtils.openSimulatorActivity(context) }) {
							Text("Simulator")
						}
						DropdownMenuItem(onClick = { ComposeUtils.openConverterActivity(context) }) {
							Text("Converter")
						}
					}
				}
			}
		)

		// ----------------------------
		// Main Content
		// ----------------------------
		content()
	}
}