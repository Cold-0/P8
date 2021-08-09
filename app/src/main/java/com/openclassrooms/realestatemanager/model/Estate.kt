package com.openclassrooms.realestatemanager.model

import java.util.*

data class Estate(
    val type: EstateType = EstateType.None,
    val price: Int = 0,
    val surface: Int = 0,
    val numberRooms: Int = 0,
    val description: String = "No Description",
    val pictures: List<Pair<String, String>> = listOf(Pair("Facade", "https://picsum.photos/id/155/300/300")),
    val district: String = "No District",
    val dateAdded: Date = Date(),
    val dateSold: Date? = null,
    val agent: String = "No one assigned",
    val numberOfRooms: Int = 0,
    val numberOfBathrooms: Int = 0,
    val numberOfBedrooms: Int = 0,
    val address: String = "No Address",
)