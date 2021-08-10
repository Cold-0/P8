package com.cold0.realestatemanager.repository

import androidx.room.*
import com.cold0.realestatemanager.model.Estate

@Dao
interface EstateDao {
    @Query("SELECT * FROM Estate")
    fun getAll(): List<Estate>

    @Insert
    fun insert(vararg Estates: Estate)

    @Delete
    fun delete(vararg Estates: Estate)

    @Update
    fun update(vararg Etates: Estate)
}