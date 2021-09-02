package com.cold0.realestatemanager.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cold0.realestatemanager.R
import com.cold0.realestatemanager.repository.DummyDataProvider

@Composable
fun HomeDebugView(viewModel: HomeViewModel) {
	Box(modifier = Modifier.fillMaxSize())
	{
		var expanded by remember { mutableStateOf(false) }

		Button(onClick = {
			expanded = !expanded
		}, modifier = Modifier
			.align(Alignment.BottomEnd)
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