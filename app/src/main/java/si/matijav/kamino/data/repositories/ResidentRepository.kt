package si.matijav.kamino.data.repositories

import androidx.lifecycle.LiveData
import si.matijav.kamino.data.Resident
import si.matijav.kamino.data.source.Result

interface ResidentRepository {
    fun getResident(residentId: Int): LiveData<Result<Resident>>

    fun getResidents(residentIds: List<Int>): LiveData<Result<List<Resident>>>
}