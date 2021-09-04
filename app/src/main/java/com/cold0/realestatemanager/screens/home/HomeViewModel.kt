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

	fun setSelectedEstate(uuid: UUID, timestamp: Date) {
		estateSelected.postValue(Pair(uuid, timestamp))
	}

	fun setSelectedEstate(estate: Estate) {
		estateSelected.postValue(Pair(estate.uid, estate.timestamp))
	}

	fun getSelectedEstate(): Estate {
		return estateList.value?.find {
			it.uid == estateSelected.value?.first ?: false
					&& it.timestamp == estateSelected.value?.second ?: false
		} ?: Estate()
	}

	// ----------------------------
	// EstateList Method
	// ----------------------------
	fun updateEstateListFromDB() {
		thread {
			estateList.postValue(Repository.db?.estateDao()?.getAll())
		}
	}


	fun addEstate(estate: Estate) {
		thread {
			Repository.db?.estateDao()?.insert(estate)
			setSelectedEstate(estate.uid, estate.timestamp)
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
	fun deleteEstate(index: UUID, timestamp: Date) {
		thread {
			Repository.db?.estateDao()?.deleteByUIDAndTimestamp(index, timestamp)
			updateEstateListFromDB()
		}
	}

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