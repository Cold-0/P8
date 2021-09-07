package com.cold0.realestatemanager.screens.home.filter

import com.cold0.realestatemanager.model.Estate


data class FilterSetting(
	var from: Estate,
	var to: Estate,
	var enabled: Boolean = false,
	var type: Boolean = false,
	var status: Boolean = false,
	var mapOfProps: Map<PropertyContainer, Boolean> = mapOf(),
) {
	companion object {
		val Disabled = FilterSetting(from = Estate(), to = Estate())
	}
}