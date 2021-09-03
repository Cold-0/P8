package com.cold0.realestatemanager.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Photo(
	var name: String = "",
	var placeHolder: String = "",
	var uri: String? = null,
) : Parcelable