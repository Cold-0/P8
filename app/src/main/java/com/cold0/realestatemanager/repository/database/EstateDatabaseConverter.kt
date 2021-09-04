package com.cold0.realestatemanager.repository.database

import androidx.room.TypeConverter
import com.cold0.realestatemanager.model.EstateStatus
import com.cold0.realestatemanager.model.EstateType
import com.cold0.realestatemanager.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class EstateDatabaseConverter {
	// Gson serializer
	private var gson: Gson = Gson()

	// ----------------------
	// Timestamp (Date)
	// ----------------------
	@TypeConverter
	fun timestampFromDate(date: Date): Long {
		return date.time
	}

	@TypeConverter
	fun dateFromTimestamp(timestamp: Long): Date {
		return Date(timestamp)
	}

	// ----------------------
	// Date (Calendar)
	// ----------------------
	@TypeConverter
	fun fromLong(value: Long): GregorianCalendar {
		val cal = GregorianCalendar()
		cal.time = Date(value)
		return cal
	}

	@TypeConverter
	fun toLong(calendar: GregorianCalendar): Long {
		val cal: Calendar = calendar
		return cal.time.time
	}

	// ----------------------
	// UUID
	// ----------------------
	@TypeConverter
	fun fromUUID(uuid: UUID): String {
		return uuid.toString()
	}

	@TypeConverter
	fun uuidFromString(string: String): UUID {
		return UUID.fromString(string)
	}

	// ----------------------
	// EstateType
	// ----------------------
	@TypeConverter
	fun toEstateType(value: Int) = enumValues<EstateType>()[value]

	@TypeConverter
	fun fromEstateType(value: EstateType) = value.ordinal

	// ----------------------
	// EstateStatus
	// ----------------------
	@TypeConverter
	fun toEstateStatus(value: Int) = enumValues<EstateStatus>()[value]

	@TypeConverter
	fun fromEstateStatus(value: EstateStatus) = value.ordinal

	// ----------------------
	// Photo List
	// ----------------------
	@TypeConverter
	fun toPhotoList(data: String?): List<Photo> {
		if (data == null) {
			return Collections.emptyList()
		}
		val listType: Type = object : TypeToken<List<Photo?>?>() {}.type
		return gson.fromJson(data, listType)
	}

	@TypeConverter
	fun fromPhotoList(someObjects: List<Photo?>?): String {
		return gson.toJson(someObjects)
	}
}