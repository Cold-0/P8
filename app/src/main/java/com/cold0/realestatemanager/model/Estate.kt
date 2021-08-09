package com.cold0.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Estate(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    val type: EstateType = EstateType.None,
    val price: Int = 0,
    val surface: Int = 0,
    val numberRooms: Int = 0,
    val description: String = "No Description",
    val pictures: List<Photo> = listOf(Photo(name = "Facade", url = "https://picsum.photos/id/155/300/300")),
    val district: String = "No District",
    val dateAdded: String = "",
    val dateSold: String = "",
    val agent: String = "No one assigned",
    val numberOfRooms: Int = 0,
    val numberOfBathrooms: Int = 0,
    val numberOfBedrooms: Int = 0,
    val address: String = "No Address",
)