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
		val Default = FilterSetting(
			from = (Estate(agent = "", surface = 50, rooms = 0, bedrooms = 0, bathrooms = 1, price = 0)),
			to = Estate(agent = "", surface = 250, rooms = 7, bedrooms = 2, bathrooms = 5, price = 200000)
		)
	}
}