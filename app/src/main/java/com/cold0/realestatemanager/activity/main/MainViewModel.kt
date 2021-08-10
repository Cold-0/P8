package com.cold0.realestatemanager.activity.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.repository.EstateDatabase
import com.cold0.realestatemanager.repository.Repository
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    val estateList: MutableLiveData<List<Estate>> = MutableLiveData()

    fun initDatabase(context: Context) {
        Repository.db = EstateDatabase.getDatabase(context)
    }

    fun updateEstateList() {
        thread {
            estateList.postValue(Repository.db?.estateDao()?.getAll())
        }
    }

    fun addEstate(estate: Estate) {
        thread {
            Repository.db?.estateDao()?.insert(estate)
            updateEstateList()
        }
    }

    fun addEstate(estateList: List<Estate>) {
        thread {
            Repository.db?.estateDao()?.insert(*(estateList.toTypedArray()))
            updateEstateList()
        }
    }

    fun deleteEstate(index: Int) {
        thread {
            estateList.value?.let { list ->
                val estate: Estate? = list.find { estate -> estate.uid == index }
                estate?.let { it1 ->
                    Repository.db?.estateDao()?.delete(it1)
                    updateEstateList()
                }
            }
        }
    }
}