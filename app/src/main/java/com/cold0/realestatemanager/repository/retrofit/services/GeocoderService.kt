package com.cold0.realestatemanager.repository.retrofit.services

import com.cold0.realestatemanager.BuildConfig
import com.cold0.realestatemanager.geocoder.GeocoderResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderService {
	@GET("api/geocode/json?key=" + BuildConfig.MAPS_API_KEY)
	suspend fun getLocation(
		@Query("address") address: String,
	): GeocoderResponse
}