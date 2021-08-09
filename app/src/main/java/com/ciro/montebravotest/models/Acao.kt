package com.ciro.montebravotest.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Acao(
    val id: Int = 0,
    val ref: String? = "",
    val symbol: String? = "",
    val curent_price: Double = 0.0,
    val target_price: Double = 0.0,
    val potential_price: Double = 0.0,
    val recomendation: String? = "",
    val date: String? = "",
    val origin: String? = "",
    val link: String? = "",
    val is_source_favorite_recomendation: Boolean = false,
    val symbol_image_url: String? = "",
    var chartData: List<AcaoChartData>? = null,
    val company_name: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        TODO("chartData"),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(ref)
        parcel.writeString(symbol)
        parcel.writeDouble(curent_price)
        parcel.writeDouble(target_price)
        parcel.writeDouble(potential_price)
        parcel.writeString(recomendation)
        parcel.writeString(date)
        parcel.writeString(origin)
        parcel.writeString(link)
        parcel.writeByte(if (is_source_favorite_recomendation) 1 else 0)
        parcel.writeString(symbol_image_url)
        parcel.writeString(company_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Acao> {
        override fun createFromParcel(parcel: Parcel): Acao {
            return Acao(parcel)
        }

        override fun newArray(size: Int): Array<Acao?> {
            return arrayOfNulls(size)
        }
    }
}
