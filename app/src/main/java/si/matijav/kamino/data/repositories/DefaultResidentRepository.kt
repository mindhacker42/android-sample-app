package si.matijav.kamino.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.Observable
import io.reactivex.Single
import si.matijav.kamino.data.Resident
import si.matijav.kamino.data.source.ApiService
import si.matijav.kamino.data.source.DefaultMemoryCache
import si.matijav.kamino.data.source.MemoryCache
import si.matijav.kamino.data.source.Result

class DefaultResidentRepository(
    private val apiService: ApiService
) : ResidentRepository {

    companion object {
        private val residentMemoryCache: MemoryCache<Resident> by lazy { DefaultMemoryCache<Resident>() }
    }

    override fun getResidents(residentIds: List<Int>): LiveData<Result<List<Resident>>> {
        val requests = residentIds
            .distinct() // Filter out duplicate ids so we don't do duplicate requests
            .map { residentId ->
                Observable.concat(getResidentFromCache(residentId), getResidentRemote(residentId))
                    .firstOrError()
            }

        val mergedResult = Single.merge(requests)
            .doOnNext({ resident ->
                residentMemoryCache.add(resident, resident.id)
            })
            .toList(Math.max(1, residentIds.size))
            .map { residents ->
                // Fill in filtered duplicated values
                return@map residentIds.mapNotNull { residentId ->
                    residents.find { resident -> resident.id == residentId }
                }
            }
            .map<Result<List<Resident>>> { resident ->
                Result.Success(resident)
            }
            .onErrorReturn { throwable: Throwable ->
                Result.Error(Exception(throwable))
            }
            .toFlowable()
        return LiveDataReactiveStreams.fromPublisher(mergedResult)
    }

    override fun getResident(residentId: Int): LiveData<Result<Resident>> {
        val request = apiService.api.getResident(residentId)
            .map<Result<Resident>> { resident -> Result.Success(resident) }
            .onErrorReturn { throwable: Throwable ->
                Result.Error(Exception(throwable))
            }
            .toFlowable()
        return LiveDataReactiveStreams.fromPublisher(request)
    }

    fun getResidentRemote(residentId: Int): Observable<Resident> {
        return apiService.api.getResident(residentId)
            .map { resident ->
                resident.id = residentId
                return@map resident
            }
            .toObservable()
    }

    fun getResidentFromCache(residentId: Int): Observable<Resident> {
        if (residentMemoryCache.get(residentId) == null) {
            return Observable.empty()
        }
        return Observable.just(residentMemoryCache.get(residentId))
    }
}