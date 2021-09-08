package com.cold0.realestatemanager.repository

import com.cold0.realestatemanager.repository.database.EstateDatabase
import com.cold0.realestatemanager.repository.retrofit.RetrofitInstance
import com.cold0.realestatemanager.repository.retrofit.services.GeocoderService

object EstateRepository {
	var db: EstateDatabase? = null
	val geocoderService: GeocoderService = RetrofitInstance.geocoderService
}