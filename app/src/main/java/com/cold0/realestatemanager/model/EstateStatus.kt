package com.cold0.realestatemanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class EstateStatus(val id: Int) : Parcelable {
	None(0),
	Available(1),
	Sold(2)
}
