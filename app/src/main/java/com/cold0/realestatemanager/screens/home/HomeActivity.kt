package com.cold0.realestatemanager.screens.home

import android.os.Bundle
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
import androidx.compose.ui.platform.LocalConfiguration
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
import kotlinx.coroutines.launch
import java.util.*


@ExperimentalAnimationApi
class HomeActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val viewModel: HomeViewModel by viewModels()
		viewModel.initDatabase(applicationContext)

		setContent {
			val configuration = LocalConfiguration.current

			var openLeftDrawer by remember { mutableStateOf(true) }
			var currentSelectedEstateIndex by remember { mutableStateOf(UUID(0, 0)) }

			val estateList by viewModel.estateList.observeAsState()
			viewModel.updateViewEstateList()

			// Add Item to List and Animate it
			val coroutineAddNewItem = rememberCoroutineScope()
			val listState = rememberLazyListState()

			RealEstateManagerTheme {
				HomeTopAppBar(
					coroutineScope = coroutineAddNewItem,
					viewModel = viewModel,
					listState = listState,
					currentEstateID = currentSelectedEstateIndex,
					listEstate = estateList,
					toggleDrawer = {
						openLeftDrawer = !openLeftDrawer
					},
					setCurrentSelectedEstate = { id: UUID ->
						currentSelectedEstateIndex = id
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
						if (currentSelectedEstateIndex == UUID(0, 0))
							currentSelectedEstateIndex = estateListChecked.first().uid
						Row(Modifier.fillMaxSize()) {
							AnimatedVisibility(visible = openLeftDrawer, enter = expandHorizontally(), exit = shrinkHorizontally()) {
								EstateList(listState, estateListChecked, currentSelectedEstateIndex) { selectedID ->
									currentSelectedEstateIndex = selectedID
									if (configuration.screenWidthDp <= 450)
										openLeftDrawer = !openLeftDrawer
								}
							}
							estateListChecked.find { it.uid == currentSelectedEstateIndex }
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
	coroutineScope: CoroutineScope,
	viewModel: HomeViewModel,
	listState: LazyListState,
	listEstate: List<Estate>?,
	currentEstateID: UUID,
	toggleDrawer: () -> Unit,
	setCurrentSelectedEstate: (UUID) -> Unit,
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
						coroutineScope.launch {
							listState.animateScrollToItem(0)
						}
					},
				) {
					Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.content_description_add_real_estate), tint = Color.White)
				}

				// ----------------------------
				// Remove Button (Only show if Estate list isn't Empty)
				// ----------------------------
				if (!listEstate.isNullOrEmpty())
					IconButton(onClick = {
						viewModel.deleteEstate(currentEstateID)
						if (listEstate.size > 1)
							setCurrentSelectedEstate(listEstate.first().uid)
					}) {
						Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.content_description_edit_current_selected_estate), tint = Color.White)
					}

				// ----------------------------
				// More Vertical Button and Drop Down Menu (Only show if Estate list isn't Empty)
				// ----------------------------
				var expanded by remember { mutableStateOf(false) }

				if (!listEstate.isNullOrEmpty()) {
					val context = LocalContext.current

					IconButton(
						onClick = {
							expanded = !expanded
						},
					) {
						Icon(imageVector = Icons.Filled.MoreVert, contentDescription = stringResource(R.string.content_description_add_real_estate), tint = Color.White)
					}

					DropdownMenu(
						offset = DpOffset((-4).dp, 4.dp),
						expanded = expanded,
						onDismissRequest = { expanded = false }
					) {
						DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
							Text("Refresh")
						}
						DropdownMenuItem(onClick = { /* Handle settings! */ }) {
							Text("Settings")
						}
						Divider()
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