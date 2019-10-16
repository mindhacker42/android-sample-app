package si.matijav.kamino

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import si.matijav.kamino.data.Planet
import si.matijav.kamino.data.repositories.PlanetRepository
import si.matijav.kamino.data.source.Result

class FakePlanetRepository(val planet: Planet) : PlanetRepository {

    override fun getPlanet(planetId: Int): LiveData<Result<Planet>> {
        if (planetId != 10) {
            return MutableLiveData(Result.Error(Exception("Planet id must be 10")))
        }
        return MutableLiveData(Result.Success(planet))
    }

    override fun likePlanet(planetId: Int): LiveData<Result<Nothing>> {
        return MutableLiveData(Result.SuccessNoData())
    }
}