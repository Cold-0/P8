package com.cold0.realestatemanager.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(primaryKeys = ["uid", "timestamp"])
data class Estate(
	val uid: UUID = UUID.randomUUID(),
	val timestamp: Date = Date(),
	var type: EstateType = EstateType.None,
	var price: Int = 0,
	var surface: Int = 0,
	var numberRooms: Int = 0,
	var description: String = "No Description",
	var photos: List<Photo> = listOf(Photo(name = "Facade", onlineUrl = "https://picsum.photos/id/155/300/300", null)),
	var district: String = "No District",
	var dateAdded: String = "",
	var dateSold: String = "",
	var agent: String = "No one assigned",
	var numberOfRooms: Int = 0,
	var numberOfBathrooms: Int = 0,
	var numberOfBedrooms: Int = 0,
	var address: String = "No Address",
	var location: String = "-74.005157,40.710785",
	var interest: String = "Nothing",
) : Parcelable {
	fun compareKeys(estate: Estate): Boolean {
		return this.uid == estate.uid && this.timestamp == estate.timestamp
	}

	fun getKeys(): Pair<UUID, Date> {
		return Pair(uid, timestamp)
	}
}