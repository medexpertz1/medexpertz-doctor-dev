package com.medexpertz.medexpertzdoctor.prescription

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.gson.annotations.SerializedName

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 02 May 2018 at 11:41 AM
 */
data class LabTest(
        @SerializedName("labtest_id")
        val labtestId: Int?,
        @SerializedName("test_name", alternate = ["labtest_name"])
        val testName: String,
        var comments: String? = "",
        var orderAppointmentId: Int
) : SearchSuggestion {

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(labtestId ?: 0)
        parcel.writeString(testName)
        parcel.writeString(comments)
        parcel.writeInt(orderAppointmentId)
    }

    override fun getBody() = testName

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LabTest> {
        override fun createFromParcel(parcel: Parcel): LabTest {
            return LabTest(parcel)
        }

        override fun newArray(size: Int): Array<LabTest?> {
            return arrayOfNulls(size)
        }
    }
}