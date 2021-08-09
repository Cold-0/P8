package com.cold0.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    val name: String = "",
    val url: String = "",
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
)