package com.cold0.realestatemanager.activity.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.repository.AppDatabase
import com.cold0.realestatemanager.repository.DataProvider
import com.cold0.realestatemanager.repository.Repository
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    val estateList: MutableLiveData<List<Estate>> = MutableLiveData(DataProvider.estateList)

    fun initDatabase(context: Context) {
        Repository.db = Room.databaseBuilder(context, AppDatabase::class.java, "realEstateOffline.SQLite.db").build()
    }

    fun getAllEstate() {
        thread {
            estateList.postValue(Repository.db?.estateDao()?.getAll())
        }
    }

    fun addEstate(estate: Estate): Int {
        thread {
            Repository.db?.estateDao()?.insert(estate)
        }
        val value = estateList.value
        return if (value != null) {
            estateList.postValue(value + listOf(estate))
            value.size
        } else
            -1
    }

    fun deleteEstate(index: Int) {
        estateList.value?.let { list ->
            val estate: Estate? = list.find { estate -> estate.uid == index }
            estate?.let { it1 ->
                thread {
                    Repository.db?.estateDao()?.delete(it1)
                }
                estateList.postValue(list - it1)
            }
        }
    }
}