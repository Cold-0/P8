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
	private val estateList: MutableLiveData<List<Estate>> = MutableLiveData()

	@Composable
	fun rememberEstateList(): State<List<Estate>?> {
		return estateList.observeAsState()
	}

	private val estateSelected: MutableLiveData<Pair<UUID, Date>> = MutableLiveData()

	@Composable
	fun rememberEstateSelected(): State<Pair<UUID, Date>?> {
		return estateSelected.observeAsState()
	}

	@Composable
	fun ObserveEstateSelected(lifecycleOwner: LifecycleOwner, onUpdate: (Pair<UUID, Date>) -> (Unit)) {
		estateSelected.observe(lifecycleOwner, onUpdate)
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
					&& it.timestamp == estateSelected.value?.second ?: false
		} ?: getFirstEstate()
	}

	private fun getFirstEstate(): Estate {
		estateList.value?.first()?.let {
			setSelectedEstate(it.getKeys())
			return it
		}
		return Estate()
	}

	// ----------------------------
	// EstateList Method
	// ----------------------------
	fun updateEstateListFromDB() {
		thread {
			estateList.postValue(Repository.db?.estateDao()?.getAll()?.sortedBy { it.status })
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