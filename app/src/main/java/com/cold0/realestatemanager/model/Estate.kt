package com.cold0.realestatemanager.model

import androidx.room.Entity
import java.util.*

@Entity(primaryKeys = ["uid", "timestamp"])
data class Estate(
    val uid: UUID = UUID.randomUUID(),
    val timestamp: Date = Date(),
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
    val location: String = "-74.005157,40.710785",
)