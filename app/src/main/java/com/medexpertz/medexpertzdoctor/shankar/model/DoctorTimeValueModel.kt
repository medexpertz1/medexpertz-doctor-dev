package com.medexpertz.medexpertzdoctor.shankar.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DoctorTimeValueModel(
        @SerializedName("time_slot_details_id")
        var time_slot_details_id: String? = null,
        @SerializedName("time")
        var time: String? = null,
        @SerializedName("status_code")
        var status_code: String? = null,
        @SerializedName("status_title")
        var status_title: String? = null,
        @SerializedName("time_slot")
        var time_slot: String? = null
        ): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(time_slot_details_id)
                parcel.writeString(time)
                parcel.writeString(status_code)
                parcel.writeString(status_title)
                parcel.writeString(time_slot)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<DoctorTimeValueModel> {
                override fun createFromParcel(parcel: Parcel): DoctorTimeValueModel {
                        return DoctorTimeValueModel(parcel)
                }

                override fun newArray(size: Int): Array<DoctorTimeValueModel?> {
                        return arrayOfNulls(size)
                }
        }
}