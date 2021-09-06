package com.cold0.realestatemanager.network

sealed class NetworkStatus {
	object Available : NetworkStatus()
	object Unavailable : NetworkStatus()
}