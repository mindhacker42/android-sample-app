package si.matijav.kamino.ui.residents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import si.matijav.kamino.data.Resident
import si.matijav.kamino.data.repositories.DefaultResidentRepository
import si.matijav.kamino.data.repositories.ResidentRepository
import si.matijav.kamino.data.source.ApiService
import si.matijav.kamino.data.source.Result

class ResidentsViewModel : ViewModel() {

    // TODO: change to constructor injection
    private val residentRepository: ResidentRepository =
        DefaultResidentRepository(ApiService.instance)

    private var residentIds = emptyList<Int>()
    lateinit var residents: LiveData<List<Resident>>

    private val _selectedResident: MutableLiveData<Resident> = LiveEvent<Resident>()
    val selectedResident: LiveData<Resident> = _selectedResident

    private val _dataLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    fun onResidentSelected(resident: Resident) {
        _selectedResident.value = resident
    }

    fun setResidentIds(residentIds: List<Int>) {
        _dataLoading.value = true
        this.residentIds = residentIds
        residents = initGetResidentsPlanet()
    }

    private fun initGetResidentsPlanet(): LiveData<List<Resident>> {
        val liveResidentsResult = residentRepository.getResidents(residentIds)
        return Transformations.map(liveResidentsResult) { result ->
            _dataLoading.value = false
            if (result is Result.Success<List<*>>) {
                return@map result.data as List<Resident>
            }
            return@map null
        }
    }
}
