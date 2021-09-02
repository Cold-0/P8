package com.cold0.realestatemanager.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Photo(
	var name: String = "",
	var url: String = "",
	var data: Bitmap? = null,
) : Parcelable