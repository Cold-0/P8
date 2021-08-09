package com.openclassrooms.realestatemanager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.EstateType
import com.openclassrooms.realestatemanager.ui.theme.RealEstateManagerTheme
import java.text.NumberFormat
import java.util.*

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var openDrawer by remember { mutableStateOf(true) }
            var currentEstate by remember { mutableStateOf(0) }
            RealEstateManagerTheme {
                Box() {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(top = 64.dp)
                    ) {
                        AnimatedVisibility(visible = openDrawer, enter = expandHorizontally(), exit = shrinkHorizontally()) {
                            RealEstateList(currentEstate) {
                                currentEstate = it
                            }
                        }
                        RealEstateInfo(DataProvider.estateList[currentEstate])
                    }
                    TopAppBar(contentPadding = PaddingValues(16.dp, 8.dp)) {
                        Icon(
                            Icons.Default.Menu,
                            "LeftMenuOpen",
                            modifier = Modifier.clickable(onClick = {
                                openDrawer = !openDrawer
                            })
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun RealEstateList(currentEstate: Int, setEstate: (Int) -> Unit = {}) {
    LazyColumn() {
        itemsIndexed(DataProvider.estateList) { id, estate ->
            RealEstateListItem(estate, currentEstate, id) {
                setEstate(id)
            }
        }
    }
}

@Composable
fun RealEstateListItem(estate: Estate, current: Int, id: Int, click: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.25f)
            .background(if (current == id) MaterialTheme.colors.secondary else MaterialTheme.colors.background)
            .drawBehind {
                val strokeWidth = 1 * density
                val y = size.height - strokeWidth / 2
                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
                drawLine(
                    Color.LightGray,
                    Offset(size.width, 0f),
                    Offset(size.width, y),
                    strokeWidth
                )
            }
            .clickable {
                click()
            }
    )
    {
        Image(
            painter = rememberImagePainter("https://picsum.photos/400"),
            contentDescription = "Avatar",
            modifier = Modifier.size(108.dp)
        )
        Column(
            Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = estate.type.toString())
            Text(text = estate.address)

            // Format value to USD formatting
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            Text(text = format.format(estate.price))
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
        Text(text = estate.address, style = MaterialTheme.typography.h5)
        LazyRow()
        {
            items(10)
            {
                RealEstatePhoto(estate, it)
            }
        }
        Text(text = "Description", style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
        Text(text = estate.description, style = MaterialTheme.typography.body2, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun RealEstatePhoto(estate: Estate, id: Int) {
    val image = rememberImagePainter("https://picsum.photos/400")
    val context = LocalContext.current
    Card(elevation = 4.dp, modifier = Modifier
        .padding(8.dp)
        .clickable {
            val intent = Intent(context, ImageViewActivity::class.java).apply {
                putExtra("img", "https://picsum.photos/400")
            }
            startActivity(context, intent, null)
        }
    )
    {
        Box() {
            Image(
                image,
                contentDescription = "A Photo",
                Modifier.size(108.dp)
            )
            Surface(
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .width(108.dp),
            ) {
                Text(
                    text = "Photo $id",
                    style = MaterialTheme.typography.subtitle1.copy(color = Color.White),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp),
                )
            }
        }
    }
}

// ----------------------------------------------------------------------------
//
// PREVIEW
//
// ----------------------------------------------------------------------------
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun RealEstateListPreview() {
    RealEstateManagerTheme {
        RealEstateList(0)
    }
}

@Preview(showBackground = true)
@Composable
fun RealEstateItemPreview() {
    RealEstateManagerTheme {
        RealEstateListItem(
            Estate(address = "Manhattan", type = EstateType.Flat, description = DataProvider.loremIpsum, price = 17870000),
            0,
            0
        )
    }
}