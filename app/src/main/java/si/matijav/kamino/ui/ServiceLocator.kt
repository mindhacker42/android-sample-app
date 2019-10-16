package si.matijav.kamino.ui

import androidx.annotation.VisibleForTesting
import si.matijav.kamino.data.repositories.DefaultPlanetRepository
import si.matijav.kamino.data.repositories.DefaultResidentRepository
import si.matijav.kamino.data.repositories.PlanetRepository
import si.matijav.kamino.data.repositories.ResidentRepository
import si.matijav.kamino.data.source.ApiService

object ServiceLocator {

    @Volatile
    var planetRepository: PlanetRepository? = null
        @VisibleForTesting set

    @Volatile
    var residentRepository: ResidentRepository? = null
        @VisibleForTesting set

    fun providePlanetRepository(): PlanetRepository {
        synchronized(this) {
            return planetRepository ?: DefaultPlanetRepository(ApiService.instance)
        }
    }

    fun provideResidentRepository(): ResidentRepository {
        synchronized(this) {
            return residentRepository ?: DefaultResidentRepository(ApiService.instance)
        }
    }
}