package com.cold0.realestatemanager.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.Photo
import com.cold0.realestatemanager.theme.RealEstateManagerTheme
import com.cold0.realestatemanager.utils.ActivityUtils.formatApiRequestGeoapify
import com.cold0.realestatemanager.utils.ActivityUtils.openImageActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.*

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: EstateViewModel by viewModels()
        viewModel.initDatabase(applicationContext)
        setContent {
            val configuration = LocalConfiguration.current

            var openLeftDrawer by remember { mutableStateOf(true) }
            var currentSelectedEstateIndex by remember { mutableStateOf(-1) }

            val estateList by viewModel.estateList.observeAsState()
            viewModel.updateViewEstateList()

            // Add Item to List and Animate it
            val coroutineAddNewItem = rememberCoroutineScope()
            val listState = rememberLazyListState()

            RealEstateManagerTheme {
                TopApplicationBar(
                    coroutineScope = coroutineAddNewItem,
                    viewModel = viewModel,
                    listState = listState,
                    currentEstateID = currentSelectedEstateIndex,
                    listEstate = estateList,
                    toggleDrawer = {
                        openLeftDrawer = !openLeftDrawer
                    },
                    setCurrentSelectedEstate = { id: Int ->
                        currentSelectedEstateIndex = id
                    }
                ) {
                    // When List is Empty we show a message
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
                    // When List isn't Empty
                    else estateList?.let { estateListChecked ->
                        if (currentSelectedEstateIndex == -1)
                            currentSelectedEstateIndex = estateListChecked.first().uid
                        Row(Modifier.fillMaxSize()) {
                            AnimatedVisibility(visible = openLeftDrawer, enter = expandHorizontally(), exit = shrinkHorizontally()) {
                                RealEstateList(listState, estateListChecked, currentSelectedEstateIndex) { selectedID ->
                                    currentSelectedEstateIndex = selectedID
                                    if (configuration.screenWidthDp <= 450)
                                        openLeftDrawer = !openLeftDrawer
                                }
                            }
                            estateListChecked.find { it.uid == currentSelectedEstateIndex }
                                ?.let { RealEstateInfo(it) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopApplicationBar(
    coroutineScope: CoroutineScope,
    viewModel: EstateViewModel,
    listState: LazyListState,
    listEstate: List<Estate>?,
    currentEstateID: Int,
    toggleDrawer: () -> Unit,
    setCurrentSelectedEstate: (Int) -> Unit,
    content: @Composable () -> Unit,
) {
    // Show Content with a padding equal to the TopAppBar size
    Box(modifier = Modifier.padding(top = 56.dp), content = { content() })
    TopAppBar(contentPadding = PaddingValues(start = 16.dp), content = {
        if (!listEstate.isNullOrEmpty())
            Icon(
                Icons.Default.Menu,
                stringResource(R.string.content_description_open_left_list),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable(onClick = {
                        toggleDrawer()
                    })
                    .padding(horizontal = 8.dp)
            )
        Text(text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h6.copy(color = Color.White),
            fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.content_description_add_real_estate),
                modifier = Modifier
                    .clickable(onClick = {
                        viewModel.addEstate(Estate())
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    })
                    .padding(16.dp, 8.dp))
            if (!listEstate.isNullOrEmpty())
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.content_description_add_real_estate),
                    modifier = Modifier
                        .clickable(onClick = {
                            viewModel.deleteEstate(currentEstateID)
                            if (listEstate.size > 1)
                                setCurrentSelectedEstate(listEstate.first().uid)
                        })
                        .padding(16.dp, 8.dp))
        }
    })
}

@ExperimentalAnimationApi
@Composable
fun RealEstateList(listState: LazyListState, estateList: List<Estate>, currentEstateID: Int, selected: (Int) -> Unit = {}) {
    LazyColumn(state = listState, modifier = Modifier
        .fillMaxHeight()
        .drawBehind {
            val strokeWidth = 1 * density
            val y = size.height - strokeWidth / 2
            drawLine(
                Color.LightGray,
                Offset(size.width, 0f),
                Offset(size.width, y),
                strokeWidth
            )
        }) {
        items(estateList) { estate ->
            RealEstateListItem(estate, currentEstateID == estate.uid) {
                selected(estate.uid)
            }
        }
    }
}

@Composable
fun RealEstateListItem(estate: Estate, isSelected: Boolean, selected: () -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val small = configuration.screenWidthDp <= 450
    Row(
        modifier = Modifier
            .width(if (small) configuration.screenWidthDp.dp else 250.dp)
            .background(if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.background)
            .drawBehind {
                val strokeWidth = 1 * density
                val y = size.height - strokeWidth / 2
                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }
            .clickable { selected() }
    )
    {
        Image(
            painter = rememberImagePainter(estate.pictures.first().url),
            contentDescription = estate.pictures.first().name,
            modifier = Modifier.size(108.dp)
        )
        Column(
            Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = estate.type.toString(), fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(text = estate.district, fontSize = 15.sp, color = Color.DarkGray)

            // Format value to USD formatting
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            Text(text = format.format(estate.price),
                color = if (isSelected) Color.White else MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.h6.copy(fontSize = 19.sp),
                fontWeight = FontWeight.ExtraBold)
        }
    }

}

@Composable
fun RealEstateInfo(estate: Estate) {
    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.media),
            style = MaterialTheme.typography.h5
        )
        LazyRow {
            items(estate.pictures) { photo ->
                RealEstatePhoto(photo)
            }
        }
        Text(text = stringResource(R.string.description), style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
        Text(text = estate.description, style = MaterialTheme.typography.body2, modifier = Modifier.padding(top = 16.dp))
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxSize()) {
            Column(Modifier.weight(1.0f)) {
                InfoDetailItem(Icons.Default.Face, stringResource(R.string.surface), estate.surface.toString())
                InfoDetailItem(Icons.Default.Person, stringResource(R.string.number_of_rooms), estate.numberOfRooms.toString())
                InfoDetailItem(Icons.Default.Info, stringResource(R.string.number_of_bathrooms), estate.numberOfBathrooms.toString())
                InfoDetailItem(Icons.Default.AccountBox, stringResource(R.string.number_of_bedrooms), estate.numberOfBedrooms.toString())
            }
            Column(Modifier.weight(1.0f)) {
                InfoDetailItem(Icons.Default.LocationOn, stringResource(R.string.location), estate.address, leftSpacing = 24.dp)
            }
            Column(Modifier.weight(1.0f), verticalArrangement = Arrangement.Top) {
                MapPinView()
            }
        }
    }
}

@Composable
fun MapPinView(localisation: String = "-74.005157,40.710785") {
    val context = LocalContext.current
    Image(
        rememberImagePainter(
            data = formatApiRequestGeoapify(400, 400, localisation),
            builder = {
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_foreground)
            }
        ),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(250.dp)
            .clickable {
                openImageActivity(context, formatApiRequestGeoapify(1024, 1024, localisation))
            },
        contentDescription = stringResource(R.string.content_description_mini_map_preview),
    )
}

@Composable
fun InfoDetailItem(icon: ImageVector, title: String, value: String, leftSpacing: Dp = 64.dp, bottomSpacing: Dp = 8.dp) {
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

@Composable
fun RealEstatePhoto(photo: Photo) {
    val image = rememberImagePainter(photo.url)
    val context = LocalContext.current
    Card(elevation = 4.dp, modifier = Modifier
        .padding(8.dp)
        .clickable {
            openImageActivity(context, photo.url)
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
                        .align(Center)
                        .padding(8.dp),
                )
            }
        }
    }
}