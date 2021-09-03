package com.cold0.realestatemanager.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Photo(
	var name: String = "",
	var onlineUrl: String = "",
	var localUri: String? = null,
) : Parcelable