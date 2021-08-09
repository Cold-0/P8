package com.openclassrooms.realestatemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            RealEstateManagerTheme {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp)
                ) {
                    RealEstateList(openDrawer)
                    RealEstateInfo(estate = DataProvider.estateList.first())
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

@ExperimentalAnimationApi
@Composable
fun RealEstateList(openDrawer: Boolean) {
    AnimatedVisibility(visible = openDrawer, enter = expandHorizontally(), exit = shrinkHorizontally()) {
        LazyColumn() {
            items(DataProvider.estateList) { estate ->
                RealEstateListItem(estate)
            }
        }
    }
}

@Composable
fun RealEstateListItem(estate: Estate) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.25f)
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
            Text(text = estate.name)

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
        Text(text = "Media", style = MaterialTheme.typography.h5)
        LazyRow()
        {
            items(10)
            {
                RealEstatePhoto(estate, it)
            }
        }
        Text(text = "Description", style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
        Text(text = stringResource(R.string.lorem_ipsum), style = MaterialTheme.typography.body2, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun RealEstatePhoto(estate: Estate, id: Int) {
    Card(elevation = 4.dp, modifier = Modifier.padding(8.dp)) {
        Box() {
            Image(
                rememberImagePainter("https://picsum.photos/400"),
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
        RealEstateList(true)
    }
}

@Preview(showBackground = true)
@Composable
fun RealEstateItemPreview() {
    RealEstateManagerTheme {
        RealEstateListItem(Estate("Manhattan", EstateType.Flat, description = DataProvider.loremIpsum, 17870000))
    }
}