package com.openclassrooms.realestatemanager.activity.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.DataProvider
import com.openclassrooms.realestatemanager.model.Estate

class MainActivityViewModel : ViewModel() {
    val estateList: MutableLiveData<List<Estate>> = MutableLiveData(DataProvider.estateList)

    fun addEstate(estate: Estate) {
        estateList.value = estateList.value?.plus(listOf(estate))
    }
}