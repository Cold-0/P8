package com.openclassrooms.realestatemanager.model

data class Estate(
    val name: String = "No Name",
    val type: EstateType = EstateType.None,
    val description: String = "No Description",
    val price: Int = 0
)