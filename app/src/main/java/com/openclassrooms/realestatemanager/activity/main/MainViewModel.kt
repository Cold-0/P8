package com.openclassrooms.realestatemanager.activity.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.DataProvider
import com.openclassrooms.realestatemanager.model.Estate
import java.util.*

class MainViewModel : ViewModel() {
    val estateList: MutableLiveData<List<Estate>> = MutableLiveData(DataProvider.estateList)

    fun addEstate(estate: Estate): Int {
        val value = estateList.value
        return if (value != null) {
            estateList.value = value + listOf(estate)
            value.size
        } else
            -1
    }

    fun deleteEstate(index: Int) {
        estateList.value?.toMutableList()?.let {
            it.removeAt(index)
            estateList.value = Collections.unmodifiableList(it)
        }
    }
}