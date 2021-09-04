package com.cold0.realestatemanager.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Photo(
	var name: String = "",
	var description: String = "",
	var onlineUrl: String = "",
	var localUri: String? = null, // If this is not null then it use this path to load image, else it use the onlineUrl
) : Parcelable