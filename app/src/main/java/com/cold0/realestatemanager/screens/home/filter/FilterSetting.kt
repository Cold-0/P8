package com.cold0.realestatemanager.screens.home.filter

import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.EstateStatus
import com.cold0.realestatemanager.model.EstateType
import java.util.*
import kotlin.reflect.KMutableProperty1

data class FilterSetting(
	var type: Pair<Boolean, EstateType>? = null,
	var status: Pair<Boolean, EstateStatus>? = null,
	var mapOfIntProps: MutableMap<KMutableProperty1<Estate, Int>, Boolean>? = null,
	var mapOfStringProps: MutableMap<KMutableProperty1<Estate, String>, Boolean>? = null,
	var mapOfDateProps: MutableMap<KMutableProperty1<Estate, Date>, Boolean>? = null,
) {}