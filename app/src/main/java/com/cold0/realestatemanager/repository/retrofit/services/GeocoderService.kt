package com.cold0.realestatemanager.repository.retrofit.services

import com.cold0.realestatemanager.BuildConfig
import com.cold0.realestatemanager.model.geocoderapi.GeocoderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderService {
	@GET("api/geocode/json?key=" + BuildConfig.GEOCODING_API_KEY)
	fun getLocation(
		@Query("address") address: String,
	): Call<GeocoderResponse>
}