package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.openclassrooms.realestatemanager.ui.theme.RealEstateManagerTheme
import java.text.NumberFormat
import java.util.*

enum class EstateType(val id: Int) {
    None(0),
    Flat(1),
    House(2),
    Duplex(3),
    Penthouse(4)
}

data class Estate(
    val name: String = "No Name",
    val type: EstateType = EstateType.None,
    val description: String = "No Description",
    val price: ULong = 0UL
)

val loremIpsum: String =
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pulvinar erat eget auctor ultricies. Vestibulum id  
    purus iaculis, semper mauris id, mollis velit. Maecenas in tempor metus. Sed sed lectus tellus. Duis condimentum odio  
    arcu, nec sodales nisl feugiat at. Nulla ut nisi eu lorem pulvinar efficitur. Nunc risus felis, fringilla et tempor  
    quis, convallis quis dolor. Mauris varius mattis imperdiet. Quisque ullamcorper erat ut dui tempus gravida. Maecenas  
    laoreet et quam vel fringilla. Quisque sed libero varius, auctor augue non, viverra mi. Praesent cursus enim eu mauris  
    suscipit ornare. In pulvinar nulla finibus ante ultrices, at rhoncus nulla tristique. Ut at sapien ac massa iaculis  
    pharetra non quis metus. Morbi quis ullamcorper diam, sit amet blandit velit."""

object DataProvider {
    val estateList: List<Estate> = listOf(
        Estate(
            "Manhattan",
            EstateType.Flat,
            loremIpsum,
            17870000UL
        ),
        Estate(
            "Montauk",
            EstateType.House,
            loremIpsum,
            21130000UL
        ),
        Estate(
            "Brooklyn",
            EstateType.Duplex,
            loremIpsum,
            13990000UL
        ),
        Estate(
            "Southampton",
            EstateType.House,
            loremIpsum,
            41480000UL
        ),
        Estate(
            "Upper East Side",
            EstateType.Penthouse,
            loremIpsum,
            29872000UL
        ),
        Estate(
            "Manhattan",
            EstateType.Flat,
            loremIpsum,
            17870000UL
        ),
        Estate(
            "Montauk",
            EstateType.House,
            loremIpsum,
            21130000UL
        ),
        Estate(
            "Brooklyn",
            EstateType.Duplex,
            loremIpsum,
            13990000UL
        ),
        Estate(
            "Southampton",
            EstateType.House,
            loremIpsum,
            41480000UL
        ),
        Estate(
            "Upper East Side",
            EstateType.Penthouse,
            loremIpsum,
            29872000UL
        ),
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealEstateManagerTheme {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp)
                ) {
                    RealEstateList()
                    RealEstateInfo(estate = DataProvider.estateList.first())
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
        items(DataProvider.estateList) { estate ->
            RealEstateListItem(estate)
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
            format.format(1000000)
            Text(text = "$17,870,000")
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
    Box(Modifier.padding(8.dp)) {
        Image(
            rememberImagePainter("https://picsum.photos/400"),
            contentDescription = "A Photo",
            Modifier.size(108.dp)
        )
        Surface(
            color = Color.Black.copy(alpha = 0.5f), modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(108.dp)
        ) {
            Text(
                text = "PhotoX",
                style = MaterialTheme.typography.subtitle1.copy(color = Color.White),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RealEstateListPreview() {
    RealEstateManagerTheme {
        RealEstateList()
    }
}

@Preview(showBackground = true)
@Composable
fun RealEstateItemPreview() {
    RealEstateManagerTheme {
        RealEstateListItem(Estate("Manhattan", EstateType.Flat, description = loremIpsum, 17870000UL))
    }
}