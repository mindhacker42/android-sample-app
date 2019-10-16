package si.matijav.kamino.ui.residents

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import si.matijav.kamino.data.Resident
import si.matijav.kamino.data.repositories.ResidentRepository
import si.matijav.kamino.data.source.Result

class ResidentsViewModel(private val residentRepository: ResidentRepository) : ViewModel() {

    private var residentIds = emptyList<Int>()
    lateinit var residents: LiveData<List<Resident>>

    private val _selectedResident: MutableLiveData<Resident> = LiveEvent<Resident>()
    val selectedResident: LiveData<Resident> = _selectedResident

    private val _dataLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    fun selectResident(resident: Resident) {
        _selectedResident.value = resident
    }

    fun setResidentIds(residentIds: List<Int>) {
        _dataLoading.value = true
        this.residentIds = residentIds
        residents = initGetResidents()
    }

    private fun initGetResidents(): LiveData<List<Resident>> {
        val liveResidentsResult = residentRepository.getResidents(residentIds)
        return Transformations.map(liveResidentsResult) { result ->
            _dataLoading.value = false
            if (result is Result.Success<List<*>>) {
                return@map result.data as List<Resident>
            }
            return@map emptyList<Resident>()
        }
    }

    class Factory(private val residentRepository: ResidentRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ResidentsViewModel(residentRepository) as T
        }
    }
}
