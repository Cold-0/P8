package com.cold0.realestatemanager.screens.home

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.repository.Repository
import com.cold0.realestatemanager.repository.database.EstateDatabase
import com.cold0.realestatemanager.screens.home.filter.FilterSetting
import com.cold0.realestatemanager.screens.home.filter.PropertyContainer
import java.util.*
import kotlin.concurrent.thread

class HomeViewModel : ViewModel() {
	// ----------------------------
	// Lazy Database Init
	// ----------------------------
	fun initDatabase(context: Context) {
		Repository.db = EstateDatabase.getDatabase(context)
	}

	// ----------------------------
	// LiveData
	// ----------------------------
	private val estateList: MutableLiveData<List<Estate>> = MutableLiveData(listOf())

	@Composable
	fun rememberEstateList(): State<List<Estate>?> {
		return estateList.observeAsState()
	}

	private val estateSelected: MutableLiveData<Pair<UUID, Date>> = MutableLiveData(Pair(UUID.randomUUID(), Date()))

	@Composable
	fun rememberEstateSelected(): State<Pair<UUID, Date>?> {
		return estateSelected.observeAsState()
	}

	@Composable
	fun ObserveEstateSelected(lifecycleOwner: LifecycleOwner, onUpdate: (Pair<UUID, Date>) -> (Unit)) {
		estateSelected.observe(lifecycleOwner, onUpdate)
	}

	private val filterSetting = MutableLiveData(FilterSetting.Disabled)

	@Composable
	fun rememberFilterSetting(): State<FilterSetting?> {
		return filterSetting.observeAsState()
	}

	fun setFilterSetting(fs: FilterSetting) {
		filterSetting.postValue(fs)
		updateEstateListFromDB()
	}

	// ----------------------------
	// Set/Get Selected Estate
	// ----------------------------
	fun setSelectedEstate(pair: Pair<UUID, Date>) {
		estateSelected.postValue(pair)
	}

	fun getSelectedEstate(): Estate {
		return estateList.value?.find {
			it.uid == estateSelected.value?.first ?: false
					&& it.added == estateSelected.value?.second ?: false
		} ?: getFirstEstate()
	}

	private fun getFirstEstate(): Estate {
		estateList.value?.first()?.let {
			setSelectedEstate(it.getKeys())
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

			}
			prop.dateProps != null -> {

			}
		}
		return false
	}

	// ----------------------------
	// EstateList Method
	// ----------------------------
	fun updateEstateListFromDB() {
		thread {
			var list = Repository.db?.estateDao()?.getAll()
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
				} else {
					list = list?.sortedBy { it.status }
				}
			}
			estateList.postValue(list)
		}
	}

	fun addEstate(estate: Estate) {
		thread {
			Repository.db?.estateDao()?.insert(estate)
			setSelectedEstate(estate.getKeys())
			updateEstateListFromDB()
		}
	}

	fun addEstate(estateList: List<Estate>) {
		thread {
			Repository.db?.estateDao()?.insert(*(estateList.toTypedArray()))
			updateEstateListFromDB()
		}
	}

	fun updateEstate(estate: Estate) {
		thread {
			Repository.db?.estateDao()?.update(estate)
			updateEstateListFromDB()
		}
	}


	// ----------------------------
	// Delete
	// ----------------------------
	fun deleteEstate(keys: Pair<UUID, Date>) {
		thread {
			Repository.db?.estateDao()?.deleteByUIDAndTimestamp(keys.first, keys.second)
			updateEstateListFromDB()
		}
	}

	fun deleteEstate(estate: Estate) {
		thread {
			Repository.db?.estateDao()?.delete(estate)
			updateEstateListFromDB()
		}
	}

	fun deleteEstate(estateList: List<Estate>) {
		thread {
			Repository.db?.estateDao()?.delete(*(estateList.toTypedArray()))
			updateEstateListFromDB()
		}
	}

	fun deleteAllEstate() {
		thread {
			Repository.db?.estateDao()?.deleteAll()
			updateEstateListFromDB()
		}
	}
}