package com.cold0.realestatemanager.screens.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.repository.Repository
import com.cold0.realestatemanager.repository.database.EstateDatabase
import java.util.*
import kotlin.concurrent.thread

class HomeViewModel : ViewModel() {
    val estateList: MutableLiveData<List<Estate>> = MutableLiveData()
    val selectedEstate: MutableLiveData<Long> = MutableLiveData()

    fun initDatabase(context: Context) {
        Repository.db = EstateDatabase.getDatabase(context)
    }

    fun updateViewEstateList() {
        thread {
            estateList.postValue(Repository.db?.estateDao()?.getAll())
        }
    }

    fun addEstate(estate: Estate) {
        thread {
            Repository.db?.estateDao()?.insert(estate)
            updateViewEstateList()
        }
    }

    fun addEstate(estateList: List<Estate>) {
        thread {
            Repository.db?.estateDao()?.insert(*(estateList.toTypedArray()))
            updateViewEstateList()
        }
    }

    fun deleteAllEstate() {
        thread() {
            Repository.db?.estateDao()?.deleteAll()
            updateViewEstateList()
        }
    }

    fun deleteEstate(index: UUID) {
        thread {
            estateList.value?.let { list ->
                val estate: Estate? = list.find { estate -> estate.uid == index }
                estate?.let { it1 ->
                    Repository.db?.estateDao()?.delete(it1)
                    updateViewEstateList()
                }
            }
        }
    }
}