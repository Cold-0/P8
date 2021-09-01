package com.cold0.realestatemanager.screens.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.cold0.realestatemanager.BuildConfig
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.repository.DummyDataProvider
import com.cold0.realestatemanager.screens.ScreensUtils.openConverterActivity
import com.cold0.realestatemanager.theme.RealEstateManagerTheme
import kotlinx.coroutines.CoroutineScope


@ExperimentalAnimationApi
class HomeActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val viewModel: HomeViewModel by viewModels()
		viewModel.initDatabase(applicationContext)

		setContent {
			var openLeftDrawer by remember { mutableStateOf(true) }

			val estateList by viewModel.estateList.observeAsState()
			viewModel.updateViewEstateList()
			val estateSelected by viewModel.selectedEstate.observeAsState()

			Log.e("", "onCreate: $estateSelected")

			RealEstateManagerTheme {
				HomeTopAppBar(
					viewModel = viewModel,
					listEstate = estateList,
					toggleDrawer = {
						openLeftDrawer = !openLeftDrawer
					}
				) {
					// ----------------------------
					// Message when list is Empty
					// ----------------------------
					if (estateList.isNullOrEmpty()) {
						Column(
							modifier = Modifier.fillMaxSize(),
							verticalArrangement = Arrangement.Center,
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							Text(
								text = "There is no Estate, press the + button in the top right to add new Estate and start editing it",
								style = MaterialTheme.typography.h6.copy(color = Color.LightGray),
								modifier = Modifier.padding(64.dp)
							)
						}
					}
					// ----------------------------
					// When List is not Empty
					// ----------------------------
					else estateList?.let { estateListChecked ->
						if (estateListChecked.find { it.uid == viewModel.selectedEstate.value?.first ?: -1 } == null) {
							if (!viewModel.estateList.value.isNullOrEmpty())
								viewModel.estateList.value?.first()?.let { viewModel.setSelectedEstate(it.uid) }
						}
						Row(Modifier.fillMaxSize()) {
							AnimatedVisibility(visible = openLeftDrawer, enter = expandHorizontally(), exit = shrinkHorizontally()) {
								EstateList(estateListChecked, viewModel)
							}
							estateListChecked.find { it.uid == viewModel.selectedEstate.value?.first ?: 0 }
								?.let { EstateDetails(it) }
						}
					}
				}

				// ----------------------------
				// Debug Build
				// ----------------------------
				if (BuildConfig.DEBUG) {
					DebugView(viewModel = viewModel)
				}
			}
		}
	}
}


@Composable
fun DebugView(viewModel: HomeViewModel) {
	Box(modifier = Modifier.fillMaxSize())
	{
		var expanded by remember { mutableStateOf(false) }

		Button(onClick = {
			expanded = !expanded
		}, modifier = Modifier
			.align(BottomEnd)
			.padding(8.dp)) {
			Icon(imageVector = Icons.Filled.Settings, contentDescription = stringResource(R.string.content_description_debug_button), tint = Color.White)
			DropdownMenu(
				expanded = expanded,
				onDismissRequest = { expanded = false }
			) {
				DropdownMenuItem(
					onClick = {
						viewModel.deleteAllEstate()
						viewModel.addEstate(DummyDataProvider.getRandomEstateList())
					})
				{
					Text("Delete all list and add new Dummies")
				}
			}
		}
	}
}

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
				if (!listEstate.isNullOrEmpty())
					IconButton(onClick = {
						viewModel.selectedEstate.value?.let { viewModel.deleteEstate(it.first) }
						if (listEstate.size > 1)
							viewModel.selectedEstate.value = Pair(listEstate.first().uid, listEstate.first().timestamp)
					}) {
						Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.content_description_edit_current_selected_estate), tint = Color.White)
					}

				// ----------------------------
				// More Vertical Button and Drop Down Menu (Only show if Estate list isn't Empty)
				// ----------------------------
				var threeDotExpanded by remember { mutableStateOf(false) }
				4
				if (!listEstate.isNullOrEmpty()) {
					val context = LocalContext.current

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
						DropdownMenuItem(onClick = { openConverterActivity(context) }) {
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