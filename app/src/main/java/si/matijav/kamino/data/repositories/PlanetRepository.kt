package si.matijav.kamino.data.repositories

import androidx.lifecycle.LiveData
import si.matijav.kamino.data.Planet
import si.matijav.kamino.data.source.Result

interface PlanetRepository {
    fun getPlanet(planetId: Int): LiveData<Result<Planet>>
    fun likePlanet(planetId: Int): LiveData<Result<Nothing>>
}