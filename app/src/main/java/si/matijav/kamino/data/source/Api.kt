package si.matijav.kamino.data.source

import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import si.matijav.kamino.data.Planet
import si.matijav.kamino.data.Resident

interface Api {

    @GET("/planets/{planetId}")
    fun getPlanet(@Path("planetId") planetId: Int): Single<Planet>

    @POST("/planets/{planetId}/like")
    fun likePlanet(@Path("planetId") planetId: Int,
                   @Body body: JsonObject): Completable

    @GET("/residents/{residentId}")
    fun getResident(@Path("residentId") residentId: Int): Single<Resident>
}