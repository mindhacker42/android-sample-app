package si.matijav.kamino.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.google.gson.JsonObject
import io.reactivex.Observable
import si.matijav.kamino.data.Planet
import si.matijav.kamino.data.source.ApiService
import si.matijav.kamino.data.source.DefaultMemoryCache
import si.matijav.kamino.data.source.MemoryCache
import si.matijav.kamino.data.source.Result

class DefaultPlanetRepository(
    private val apiService: ApiService
) : PlanetRepository {

    companion object {
        private val planetMemoryCache: MemoryCache<Planet> by lazy { DefaultMemoryCache<Planet>() }
    }

    override fun getPlanet(planetId: Int): LiveData<Result<Planet>> {
        val request = Observable.concat(getPlanetFromCache(planetId), getPlanetRemote(planetId))
            .firstOrError()
            .doOnSuccess({ planet ->
                planetMemoryCache.add(planet, planetId)
            })
            .map<Result<Planet>> { planet: Planet -> Result.Success(planet) }
            .onErrorReturn { throwable: Throwable ->
                Result.Error(Exception(throwable))
            }
            .toFlowable()
        return LiveDataReactiveStreams.fromPublisher(request)
    }

    override fun likePlanet(planetId: Int): LiveData<Result<Nothing>> {
        val body = JsonObject()
        body.addProperty("planet_id", 10)

        val request = apiService.api.likePlanet(planetId, body = body)
            .toSingle<Result<Nothing>>({ Result.SuccessNoData() })
            .onErrorReturn { throwable: Throwable -> Result.Error(Exception(throwable)) }
            .toFlowable()
        return LiveDataReactiveStreams.fromPublisher(request)
    }

    fun getPlanetRemote(planetId: Int): Observable<Planet> {
        return apiService.api.getPlanet(planetId)
            .toObservable()
    }

    fun getPlanetFromCache(planetId: Int): Observable<Planet> {
        if (planetMemoryCache.get(planetId) == null) {
            return Observable.empty()
        }
        return Observable.just(planetMemoryCache.get(planetId))
    }
}