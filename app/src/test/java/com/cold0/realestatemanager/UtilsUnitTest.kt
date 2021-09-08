package com.cold0.realestatemanager

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsUnitTest {
	@Test
	fun testMoneyConvert() {
		// ----------------------------
		// Euro to Dollars
		// ----------------------------
		assertEquals(72439, Utils.convertDollarToEuro(85553))
		assertEquals(914456, Utils.convertDollarToEuro(1080000))


		// ----------------------------
		// Dollars to Euro
		// ----------------------------
		assertEquals(101041, Utils.convertEuroToDollar(85553))
		assertEquals(1275512, Utils.convertEuroToDollar(1080000))
	}

	@Test
	fun testDateFormat() {
		// ----------------------------
		// Today Date Format
		// ----------------------------
		assertEquals("08/09/2021", Utils.getTodayDate())
	}
}