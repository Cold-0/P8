package com.cold0.realestatemanager.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.BuildConfig
import com.cold0.realestatemanager.ComposeUtils.RunWithLifecycleOwner
import com.cold0.realestatemanager.ComposeUtils.getScreenInfo
import com.cold0.realestatemanager.network.NetworkStatus
import com.cold0.realestatemanager.network.NetworkStatusTracker
import com.cold0.realestatemanager.network.NetworkStatusViewModel
import com.cold0.realestatemanager.screens.home.estatedetail.EstateDetails
import com.cold0.realestatemanager.screens.home.estatelist.EstateList
import com.cold0.realestatemanager.theme.RealEstateManagerTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
class HomeActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val viewModel: HomeViewModel by viewModels()
		viewModel.initDatabase(applicationContext)

		val networkViewModel: NetworkStatusViewModel by lazy {
			ViewModelProvider(
				this,
				object : ViewModelProvider.Factory {
					override fun <T : ViewModel?> create(modelClass: Class<T>): T {
						val networkStatusTracker = NetworkStatusTracker(this@HomeActivity)
						return NetworkStatusViewModel(networkStatusTracker) as T
					}
				},
			).get(NetworkStatusViewModel::class.java)
		}

		setContent {
			val (small) = getScreenInfo()

			var openLeftDrawer by remember { mutableStateOf(true) }

			val estateList by viewModel.rememberEstateList()
			viewModel.updateEstateListFromDB()
			val estateSelected by viewModel.rememberEstateSelected()

			val networkStatusState = networkViewModel.networkState.observeAsState()

			RunWithLifecycleOwner {
				viewModel.ObserveEstateSelected(it) {
					if (small)
						openLeftDrawer = false
				}
			}

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
					else {
						estateList?.let { estateListChecked ->
							Row(Modifier.fillMaxSize()) {
								val pair = estateSelected ?: Pair(UUID.randomUUID(), Date())
								AnimatedVisibility(visible = openLeftDrawer, enter = expandHorizontally(), exit = shrinkHorizontally()) {
									EstateList(estateListChecked, pair, viewModel)
								}
								Column()
								{
									AnimatedVisibility(
										visible = networkStatusState.value == NetworkStatus.Unavailable,
										enter = expandVertically(),
										exit = shrinkVertically()
									) {
										Box()
										{
											Surface(color = Color(ColorUtils.HSLToColor(floatArrayOf(0.0f, 0.75f, 0.5f)))) {
												Text(text = "No Internet Connection, Viewing Local Copy",
													color = Color.White,
													textAlign = TextAlign.Center,
													modifier = Modifier
														.align(Alignment.Center)
														.fillMaxWidth()
														.padding(4.dp)

												)
											}
										}
									}
									EstateDetails(viewModel.getSelectedEstate())
								}
							}
						}
					}
				}

				// ----------------------------
				// Debug Build Only
				// ----------------------------
				if (BuildConfig.DEBUG) {
					HomeDebug(viewModel = viewModel)
				}
			}
		}
	}
}