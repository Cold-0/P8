package com.openclassrooms.realestatemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openclassrooms.realestatemanager.ui.theme.P8Theme

data class Estate(val name: String = "No Name", val description: String = "No Description");

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P8Theme {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp)
                ) {
                    RealEstateList()
                    RealEstateInfo()
                }
                TopAppBar() {
                    Text(text = "Flat")
                }
            }
        }
    }
}

@Composable
fun RealEstateList() {
    LazyColumn() {
        items(100) {
            RealEstateListItem("Android")
        }
    }
}

@Composable
fun RealEstateListItem(name: String) {
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
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "Avatar")
        Column(
            Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Flat")
            Text(text = "Manhattan")
            Text(text = "$17,870,000")
        }
    }

}

@Composable
fun RealEstateInfo() {
    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Media", style = MaterialTheme.typography.h5)
        LazyRow()
        {
            items(50)
            {
                RealEstatePhoto()
            }
        }
        Text(text = "Description", style = MaterialTheme.typography.h5, modifier = Modifier.padding(top = 16.dp))
        Text(text = stringResource(R.string.lorem_ipsum), style = MaterialTheme.typography.body2, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun RealEstatePhoto() {
    Box(Modifier.padding(8.dp)) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "A Photo")
        Text(
            text = "PhotoX",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    P8Theme {
        RealEstateListItem("Android")
    }
}