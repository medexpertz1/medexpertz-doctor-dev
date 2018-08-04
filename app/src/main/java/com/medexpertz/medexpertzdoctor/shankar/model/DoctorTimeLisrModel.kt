package com.medexpertz.medexpertzdoctor.shankar.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DoctorTimeLisrModel(

    @SerializedName("time")
    var time: String? = null,
    @SerializedName("values")
    var values: ArrayList<DoctorTimeValueModel>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            TODO("values")) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DoctorTimeLisrModel> {
        override fun createFromParcel(parcel: Parcel): DoctorTimeLisrModel {
            return DoctorTimeLisrModel(parcel)
        }

        override fun newArray(size: Int): Array<DoctorTimeLisrModel?> {
            return arrayOfNulls(size)
        }
    }
}