package com.cold0.realestatemanager

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cold0.realestatemanager.repository.dummy.EstateDummyData
import com.cold0.realestatemanager.screens.home.filter.FilterSettings
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FilterInstrumentedTest {
	@Test
	fun getOne() {
		val originalList = EstateDummyData.getRandomEstateList()
		val filteredList = originalList.toList()
		val filterSettings = FilterSettings.Default.copy()
		filterSettings.type = true
	}
}