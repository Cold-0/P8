package com.cold0.realestatemanager.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cold0.realestatemanager.model.Estate

@Database(entities = [Estate::class], version = 1)
@TypeConverters(EstateDatabaseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun estateDao(): EstateDao
}