package com.cold0.realestatemanager.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(primaryKeys = ["uid", "added"])
data class Estate(
	var uid: UUID = UUID.randomUUID(),
	var added: Date = Date(),
	var type: EstateType = EstateType.Flat,
	var price: Int = 0,
	var surface: Int = 0,
	var description: String = "",
	var photos: List<Photo> = listOf(Photo(name = "Facade", onlineUrl = "https://picsum.photos/id/155/300/300", localUri = null, description = "")),
	var district: String = "",
	var sold: Date = Date(),
	var agent: String = "",
	var rooms: Int = 0,
	var bathrooms: Int = 0,
	var bedrooms: Int = 0,
	var address: String = "",
	var latitude: Double = 40.592620,
	var longitude: Double = -73.988640,
	var interest: String = "",
	var status: EstateStatus = EstateStatus.Available,
) : Parcelable {
	fun compareKeys(pair: Pair<UUID, Date>): Boolean {
		return this.uid == pair.first && this.added == pair.second
	}

	fun getKeys(): Pair<UUID, Date> {
		return Pair(uid, added)
	}
}