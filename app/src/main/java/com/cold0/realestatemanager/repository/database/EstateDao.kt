package com.cold0.realestatemanager.repository.database

import androidx.room.*
import com.cold0.realestatemanager.model.Estate
import java.util.*

@Dao
interface EstateDao {
	@Query("SELECT * FROM Estate")
	fun getAll(): List<Estate>

	@Insert
	fun insert(vararg Estates: Estate)

	@Delete
	fun delete(vararg Estates: Estate)

	@Query("DELETE FROM estate WHERE uid = :uid")
	fun deleteByUID(uid: UUID)

	@Query("DELETE FROM estate WHERE uid = :uid AND timestamp = :timestamp")
	fun deleteByUIDAndTimestamp(uid: UUID, timestamp: Date)

	@Query("DELETE FROM estate")
	fun deleteAll()

	@Update
	fun update(vararg Estates: Estate)
}