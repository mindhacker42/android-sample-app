package si.matijav.kamino.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Planet(
    val name: String,
    @SerializedName("rotation_period") val rotationPeriod: Int,
    @SerializedName("orbital_period") val orbitalPeriod: Int,
    val diameter: Int,
    val climate: String,
    val gravity: String,
    val terrain: String,
    @SerializedName("surface_water") val surfaceWater: Int,
    val population: Int,
    val residents: List<String>,
    val created: String,
    val edited: String,
    @SerializedName("image_url") val imageUrl: String,
    val likes: Int
) {
    val residentIds: List<Int>
        get() {
            return residents.mapNotNull { extractResidentIds(it) }
        }

    fun extractResidentIds(residentUrl: String): Int? {
        val apiPath = "http://private-84e428-starwars2.apiary-mock.com/residents/"
        if (!residentUrl.startsWith(apiPath)) {
            return null
        }
        return residentUrl.replace(apiPath, "").toInt()
    }
}