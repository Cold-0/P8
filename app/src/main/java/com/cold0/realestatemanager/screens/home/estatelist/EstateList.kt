package com.cold0.realestatemanager.screens.home.estatelist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.cold0.realestatemanager.ComposeUtils
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.screens.home.HomeViewModel
import java.util.*

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun EstateList(estateList: List<Estate>, estateSelected: Pair<UUID, Date>, viewModel: HomeViewModel) {
	val (small, width) = ComposeUtils.getScreenInfo()

	Column(Modifier.width(if (small) width.dp else 250.dp)) {
		var filterDialogOpenned by remember { mutableStateOf(false) }

		Button(
			onClick = { filterDialogOpenned = !filterDialogOpenned },
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp)
		) {
			Text("Filter")
		}

		AnimatedVisibility(
			visible = filterDialogOpenned,
			enter = expandVertically(
				animationSpec = tween(
					durationMillis = 500,
					easing = FastOutSlowInEasing
				)
			),
			exit = shrinkVertically(
				animationSpec = tween(
					durationMillis = 500,
					easing = FastOutSlowInEasing
				)
			)
		)
		{
			EtateListFilter()
		}

		AnimatedVisibility(
			visible = !filterDialogOpenned,
			enter = expandVertically(
				animationSpec = tween(
					durationMillis = 500,
					easing = FastOutSlowInEasing
				)
			),
			exit = shrinkVertically(
				animationSpec = tween(
					durationMillis = 500,
					easing = FastOutSlowInEasing
				)
			)
		) {
			LazyColumn(modifier = Modifier
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
					EstateListItem(estate, estate.compareKeys(estateSelected), viewModel = viewModel)
				}
			}
		}
	}
}


