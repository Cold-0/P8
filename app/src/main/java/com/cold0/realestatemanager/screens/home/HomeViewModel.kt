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
import com.cold0.realestatemanager.screens.home.filter.FilterSetting
import com.cold0.realestatemanager.screens.home.filter.PropertyContainer
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

	private val filterSetting = MutableLiveData(FilterSetting.Default.copy())

	fun setFilterSetting(fs: FilterSetting) {
		filterSetting.postValue(fs)
		updateEstateListFromDB()
	}

	fun getFilterSetting(): FilterSetting {
		return filterSetting.value ?: FilterSetting.Default.copy()
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

	private fun filterList(estate: Estate, from: Estate, to: Estate, prop: PropertyContainer): Boolean {
		when {
			prop.intProps != null -> {
				val value = prop.intProps?.get(estate)
				val valueFrom = prop.intProps?.get(from)
				val valueTo = prop.intProps?.get(to)
				if (value != null && valueFrom != null && valueTo != null) {
					if (value >= valueFrom && value <= valueTo)
						return true
				}
			}

			prop.stringProps != null -> {
				val value = prop.stringProps?.get(estate)
				val valueFrom = prop.stringProps?.get(from)

				if (value != null && valueFrom != null) {
					return value.contains(valueFrom, ignoreCase = true)
				}
			}

			prop.dateProps != null -> {
				val value = prop.dateProps?.get(estate)
				val valueFrom = prop.dateProps?.get(from)
				val valueTo = prop.dateProps?.get(to)
				if (value != null && valueFrom != null && valueTo != null) {
					return (value.after(valueFrom) && value.before(valueTo))
				}
			}
		}
		return false
	}

	// ----------------------------
	// EstateList Method
	// ----------------------------
	fun updateEstateListFromDB() {
		thread {
			var list = EstateRepository.db?.estateDao()?.getAll()
			val filterSetting = filterSetting.value

			if (filterSetting != null && list != null) {
				if (filterSetting.enabled) {

					// Properties
					filterSetting.mapOfProps.keys.forEach { prop ->
						if (filterSetting.mapOfProps[prop] == true)
							list = list!!.filter {
								filterList(it, filterSetting.from, filterSetting.to, prop)
							}
					}

					// List
					if (filterSetting.status) {
						list = list?.filter {
							it.status == filterSetting.from.status
						}
					}
					if (filterSetting.type) {
						list = list?.filter {
							it.type == filterSetting.from.type
						}
					}
				}
			}
			list = list?.sortedBy { it.status }
			estateList.postValue(list)
		}
	}

	fun addEstate(estate: Estate) {
		thread {
			EstateRepository.db?.estateDao()?.insert(estate)
			setSelectedEstate(estate.uid)
			updateEstateListFromDB()
		}
	}

	fun addEstate(estateList: List<Estate>) {
		thread {
			EstateRepository.db?.estateDao()?.insert(*(estateList.toTypedArray()))
			updateEstateListFromDB()
		}
	}

	fun updateEstate(estate: Estate) {
		thread {
			EstateRepository.db?.estateDao()?.update(estate)
			updateEstateListFromDB()
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