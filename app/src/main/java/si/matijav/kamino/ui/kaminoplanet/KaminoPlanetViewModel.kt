package si.matijav.kamino.ui.kaminoplanet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import si.matijav.kamino.data.Planet
import si.matijav.kamino.data.repositories.DefaultPlanetRepository
import si.matijav.kamino.data.repositories.PlanetRepository
import si.matijav.kamino.data.source.ApiService
import si.matijav.kamino.data.source.Result

class KaminoPlanetViewModel : ViewModel() {

    private val kaminoPlanetId = 10

    // TODO: change to constructor injection
    private val planetRepository: PlanetRepository = DefaultPlanetRepository(ApiService.instance)

    val kaminoPlanet: LiveData<Planet> = initGetKaminoPlanet()

    private val _isKaminoPlanetLiked = MutableLiveData(false)
    val isKaminoPlanetLiked: LiveData<Boolean> = _isKaminoPlanetLiked

    private val _likeKaminoPlanet = initLikeKaminoPlanet()
    val likeKaminoPlanet = _likeKaminoPlanet

    private fun initLikeKaminoPlanet(): LiveData<String> {
        val liveLikePlanetResult = planetRepository.likePlanet(kaminoPlanetId)
        return Transformations.map(liveLikePlanetResult) { result ->
            if (result is Result.SuccessNoData) {
                val planetName = kaminoPlanet.value?.name
                _isKaminoPlanetLiked.value = true
                return@map "You liked $planetName."
            }
            return@map "Like failed."
        }
    }

    private fun initGetKaminoPlanet(): LiveData<Planet> {
        val livePlanetResult = planetRepository.getPlanet(kaminoPlanetId)
        return Transformations.map(livePlanetResult) { result ->
            if (result is Result.Success<*>) {
                return@map result.data as Planet
            }
            return@map null
        }
    }
}
