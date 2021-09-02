package com.cold0.realestatemanager.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.screens.ScreensUtils

@ExperimentalCoilApi
@Composable
fun HomeTopAppBar(
	viewModel: HomeViewModel,
	listEstate: List<Estate>?,
	toggleDrawer: () -> Unit,
	content: @Composable () -> Unit,
) {
	Column {
		TopAppBar(
			elevation = 4.dp,
			title = { Text("Real Estate Manager") },
			navigationIcon = {
				if (!listEstate.isNullOrEmpty())
					IconButton(onClick = { toggleDrawer() }) {
						Icon(Icons.Filled.Menu, contentDescription = stringResource(R.string.content_description_open_left_list), tint = Color.White)
					}
			},
			actions = {
				val context = LocalContext.current

				// ----------------------------
				// Add Button
				// ----------------------------
				IconButton(
					onClick = {
						viewModel.addEstate(Estate())
					},
				) {
					Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.content_description_add_real_estate), tint = Color.White)
				}

				// ----------------------------
				// Remove Button (Only show if Estate list isn't Empty)
				// ----------------------------
				var estateToEdit by remember { mutableStateOf<Estate?>(null) } // Needed because onClick can't be @Composable
				if (!listEstate.isNullOrEmpty())
					IconButton(onClick = {
						estateToEdit = viewModel.getSelectedEstate()
					}) {
						Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.content_description_edit_current_selected_estate), tint = Color.White)
					}

				estateToEdit?.let {
					ScreensUtils.OpenEditEstateActivity(context, estateToEdit!!) { estate ->
						viewModel.updateEstate(estate)
					}
					estateToEdit = null
				}

				// ----------------------------
				// More Vertical Button and Drop Down Menu (Only show if Estate list isn't Empty)
				// ----------------------------
				var threeDotExpanded by remember { mutableStateOf(false) }

				if (!listEstate.isNullOrEmpty()) {


					IconButton(
						onClick = {
							threeDotExpanded = !threeDotExpanded
						},
					) {
						Icon(imageVector = Icons.Filled.MoreVert, contentDescription = stringResource(R.string.content_description_add_real_estate), tint = Color.White)
					}

					DropdownMenu(
						offset = DpOffset((-4).dp, 4.dp),
						expanded = threeDotExpanded,
						onDismissRequest = { threeDotExpanded = false }
					) {
						DropdownMenuItem(onClick = {
							viewModel.deleteEstate(viewModel.getSelectedEstate().getKeys())
							if (listEstate.size > 1)
								viewModel.setSelectedEstate(Pair(listEstate.first().uid, listEstate.first().timestamp))
						}) {
							Text("Delete Estate")
						}
						Divider()
						DropdownMenuItem(onClick = { ScreensUtils.openConverterActivity(context) }) {
							Text("Converter Tool")
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