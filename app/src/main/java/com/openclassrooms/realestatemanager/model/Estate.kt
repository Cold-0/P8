package com.openclassrooms.realestatemanager.model

import java.util.*

data class Estate(
    val type: EstateType = EstateType.None,
    val price: Int = 0,
    val area: Int = 0,
    val numberRooms: Int = 0,
    val description: String = "No Description",
    val pictures: List<String> = listOf(),
    val address: String = "No Address",
    val dateAdded: Date = Date(),
    val dateSold: Date? = null,
    val agent: String = "No one assigned"
)