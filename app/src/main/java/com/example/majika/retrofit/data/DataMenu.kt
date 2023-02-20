package com.example.majika.retrofit.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DataMenu (
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("price") val price: Double,
    @SerializedName("sold") val sold: Int,
    @SerializedName("type") val type: String,
    @SerializedName("count") var count: Int,
    )  : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(name)
        p0.writeString(description)
        p0.writeString(currency)
        p0.writeDouble(price)
        p0.writeInt(sold)
        p0.writeString(type)
        p0.writeInt(count)
    }

    companion object CREATOR : Parcelable.Creator<DataMenu> {
        override fun createFromParcel(parcel: Parcel): DataMenu {
            return DataMenu(parcel)
        }

        override fun newArray(size: Int): Array<DataMenu?> {
            return arrayOfNulls(size)
        }
    }
}