package com.cold0.realestatemanager.screens.commons

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DatePicker(showDialog: Boolean, onDatePicked: (Date) -> (Unit)) {
	if (showDialog) {
		val context = LocalContext.current
		val mYear: Int
		val mMonth: Int
		val mDay: Int
		val now = Calendar.getInstance()
		mYear = now.get(Calendar.YEAR)
		mMonth = now.get(Calendar.MONTH)
		mDay = now.get(Calendar.DAY_OF_MONTH)
		now.time = Date()
		DatePickerDialog(
			context,
			{ _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
				val cal = Calendar.getInstance()
				cal.set(year, month, dayOfMonth)
				onDatePicked(cal.time)
			}, mYear, mMonth, mDay
		).show()
	}
}