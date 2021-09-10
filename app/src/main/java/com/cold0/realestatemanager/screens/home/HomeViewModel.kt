package com.cold0.realestatemanager.screens.home

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.repository.EstateRepository
import com.cold0.realestatemanager.repository.database.EstateDatabase
import com.cold0.realestatemanager.screens.home.filter.FilterSettings
import com.cold0.realestatemanager.screens.home.filter.FilterUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlin.concurrent.thread

class HomeViewModel : ViewModel() {
	// ----------------------------
	// Lazy Database Init
	// ----------------------------
	fun initDatabase(context: Context) {
		EstateRepository.db = EstateDatabase.getInstance(context)
	}

	// ----------------------------
	// LiveData
	// ----------------------------
	private val estateList: MutableLiveData<List<Estate>> = MutableLiveData(listOf())

	@Composable
	fun rememberEstateList(): State<List<Estate>?> {
		return estateList.observeAsState()
	}

	private val estateSelected: MutableLiveData<Long> = MutableLiveData(0)

	@Composable
	fun rememberEstateSelected(): State<Long?> {
		return estateSelected.observeAsState()
	}

	@Composable
	fun ObserveEstateSelected(lifecycleOwner: LifecycleOwner, onUpdate: (Long) -> (Unit)) {
		estateSelected.observe(lifecycleOwner, onUpdate)
	}

	private val filterSetting = MutableLiveData(FilterSettings.Default.copy())

	fun setFilterSetting(fs: FilterSettings) {
		filterSetting.postValue(fs)
		updateEstateListFromDB()
	}

	fun getFilterSetting(): FilterSettings {
		return filterSetting.value ?: FilterSettings.Default.copy()
	}

	// ----------------------------
	// Set/Get Selected Estate
	// ----------------------------
	fun setSelectedEstate(uid: Long) {
		estateSelected.postValue(uid)
	}

	fun getSelectedEstate(): Estate {
		return estateList.value?.find {
			it.uid == estateSelected.value ?: false
		} ?: getFirstEstate()
	}

	private fun getFirstEstate(): Estate {
		estateList.value?.first()?.let {
			setSelectedEstate(it.uid)
			return it
		}
		return Estate()
	}

	// ----------------------------
	// EstateList Method
	// ----------------------------
	fun updateEstateListFromDB() {
		thread {
			val list = FilterUtils.filterList(EstateRepository.db?.estateDao()?.getAll(), filterSetting.value)
			estateList.postValue(list)
		}
	}



	@DelicateCoroutinesApi
	fun addEstate(estate: Estate) {
		EstateRepository.callGeocoderService(estate.address) { lat, lng ->
			estate.latitude = lat
			estate.longitude = lng
			thread() {
				EstateRepository.db?.estateDao()?.insert(estate)
				setSelectedEstate(estate.uid)
				updateEstateListFromDB()
			}
		}
	}


	fun addEstates(estateList: List<Estate>) {
		thread {
			EstateRepository.db?.estateDao()?.insert(*(estateList.toTypedArray()))
			updateEstateListFromDB()
		}
	}

	@DelicateCoroutinesApi
	fun updateEstate(estate: Estate) {
		EstateRepository.callGeocoderService(estate.address) { lat, lng ->
			estate.latitude = lat
			estate.longitude = lng
			thread() {
				EstateRepository.db?.estateDao()?.update(estate)
				updateEstateListFromDB()
			}
		}
	}

	// ----------------------------
	// Delete
	// ----------------------------
	fun deleteEstate(uid: Long) {
		thread {
			EstateRepository.db?.estateDao()?.delete(uid)
			updateEstateListFromDB()
		}
	}

	fun deleteEstate(estate: Estate) {
		thread {
			EstateRepository.db?.estateDao()?.delete(estate)
			updateEstateListFromDB()
		}
	}

	fun deleteEstate(estateList: List<Estate>) {
		thread {
			EstateRepository.db?.estateDao()?.delete(*(estateList.toTypedArray()))
			updateEstateListFromDB()
		}
	}

	fun deleteAllEstate() {
		thread {
			EstateRepository.db?.estateDao()?.deleteAll()
			updateEstateListFromDB()
		}
	}
}