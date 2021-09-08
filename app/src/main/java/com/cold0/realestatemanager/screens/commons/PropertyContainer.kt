package com.cold0.realestatemanager.screens.commons

import com.cold0.realestatemanager.model.Estate
import java.util.*
import kotlin.reflect.KMutableProperty1

data class PropertyContainer(
	var intProps: KMutableProperty1<Estate, Int>? = null,
	var stringProps: KMutableProperty1<Estate, String>? = null,
	var dateProps: KMutableProperty1<Estate, Date>? = null,
) {
	fun getName(): String {
		if (intProps != null)
			return (intProps?.name)?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }.toString()

		if (stringProps != null)
			return (stringProps?.name)?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }.toString()

		if (dateProps != null)
			return dateProps?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }.toString()

		return "Error"
	}
}