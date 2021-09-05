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
	var type: EstateType = EstateType.Flat,
	var price: Int = 0,
	var surface: Int = 0,
	var numberRooms: Int = 0,
	var description: String = "No Description",
	var photos: List<Photo> = listOf(Photo(name = "Facade", onlineUrl = "https://picsum.photos/id/155/300/300", localUri = null, description = "")),
	var district: String = "No District",
	var dateAdded: GregorianCalendar = GregorianCalendar(),
	var dateSold: GregorianCalendar = GregorianCalendar(),
	var agent: String = "No one assigned",
	var numberOfRooms: Int = 0,
	var numberOfBathrooms: Int = 0,
	var numberOfBedrooms: Int = 0,
	var address: String = "No Address",
	var location: String = "-74.005157,40.710785",
	var interest: String = "Nothing",
	var status: EstateStatus = EstateStatus.Available,
) : Parcelable {
	fun compareKeys(pair: Pair<UUID, Date>): Boolean {
		return this.uid == pair.first && this.timestamp == pair.second
	}

	fun compareKeys(estate: Estate): Boolean {
		return compareKeys(estate.getKeys())
	}

	fun getKeys(): Pair<UUID, Date> {
		return Pair(uid, timestamp)
	}
}