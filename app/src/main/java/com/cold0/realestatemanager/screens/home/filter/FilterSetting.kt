package com.cold0.realestatemanager.screens.home.filter

import com.cold0.realestatemanager.model.Estate


data class FilterSetting(
	var from: Estate,
	var to: Estate,
	var enabled: Boolean,
	var type: Boolean,
	var status: Boolean,
	var mapOfProps: MutableMap<PropertyContainer, Boolean>,
) {
	companion object {
		val Default = FilterSetting(
			from = (Estate(agent = "", surface = 50, rooms = 0, bedrooms = 0, bathrooms = 1, price = 0)),
			to = Estate(agent = "", surface = 250, rooms = 7, bedrooms = 2, bathrooms = 5, price = 200000),
			enabled = false,
			type = false,
			status = false,
			mapOfProps = mutableMapOf(
				PropertyContainer(stringProps = Estate::agent) to false,
				PropertyContainer(dateProps = Estate::added) to false,
				PropertyContainer(dateProps = Estate::sold) to false,
				PropertyContainer(intProps = Estate::price) to false,
				PropertyContainer(intProps = Estate::surface) to false,
				PropertyContainer(intProps = Estate::rooms) to false,
				PropertyContainer(intProps = Estate::bathrooms) to false,
				PropertyContainer(intProps = Estate::bedrooms) to false,
				PropertyContainer(intProps = Estate::surface) to false,
				PropertyContainer(stringProps = Estate::interest) to false,
				PropertyContainer(stringProps = Estate::description) to false,
				PropertyContainer(stringProps = Estate::address) to false,
			)
		)
	}
}