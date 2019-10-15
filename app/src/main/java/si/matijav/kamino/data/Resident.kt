package si.matijav.kamino.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

data class Resident(
    var id: Int,
    val name: String,
    val height: Int,
    val mass: String,
    @SerializedName("hair_color") val hairColor: String,
    @SerializedName("skin_color") val skinColor: String,
    @SerializedName("eye_color") val eyeColor: String,
    @SerializedName("birth_year") val birthYear: String,
    val gender: String,
    val homeworld: String,
    val created: String,
    val edited: String,
    @SerializedName("image_url") val imageUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(height)
        parcel.writeString(mass)
        parcel.writeString(hairColor)
        parcel.writeString(skinColor)
        parcel.writeString(eyeColor)
        parcel.writeString(birthYear)
        parcel.writeString(gender)
        parcel.writeString(homeworld)
        parcel.writeString(created)
        parcel.writeString(edited)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Resident> {
        override fun createFromParcel(parcel: Parcel): Resident {
            return Resident(parcel)
        }

        override fun newArray(size: Int): Array<Resident?> {
            return arrayOfNulls(size)
        }
    }

}