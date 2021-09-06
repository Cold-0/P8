package com.cold0.realestatemanager.screens.home.estatelist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun EtateListFilter() {
	Column(Modifier
		.fillMaxHeight()
		.padding(8.dp)
		.fillMaxWidth()
		.verticalScroll(rememberScrollState())) {
		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}

		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}

		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}
		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}

		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}

		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}
		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}

		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}

		Text("Surface", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
		Row() {
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
			Spacer(Modifier
				.width(8.dp))
			TextField(value = "", onValueChange = {}, modifier = Modifier
				.weight(1.0f))
		}

	}
}