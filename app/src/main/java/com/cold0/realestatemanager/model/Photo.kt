package com.cold0.realestatemanager.model

import androidx.room.Entity

@Entity
data class Photo(
    val name: String = "",
    val url: String = "",
)