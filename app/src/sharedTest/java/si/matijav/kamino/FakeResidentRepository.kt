package si.matijav.kamino

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import si.matijav.kamino.data.Resident
import si.matijav.kamino.data.repositories.ResidentRepository
import si.matijav.kamino.data.source.Result
import java.lang.Exception

class FakeResidentRepository(val residents: List<Resident>) :
    ResidentRepository {

    var shouldReturnError: Boolean = false

    override fun getResidents(residentIds: List<Int>): LiveData<Result<List<Resident>>> {
        if (shouldReturnError) {
            return MutableLiveData(Result.Error(Exception("Residents error")))
        }
        return MutableLiveData(Result.Success(residents))
    }
}