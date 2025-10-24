package com.denis.practice19

import android.os.Parcel
import android.os.Parcelable
import androidx.core.flagging.Flags

data class User(val name: String, val age: Int, val gender: String): Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readString() ?: "Unknown name",
        parcel.readInt(),
        parcel.readString() ?: "Unknown gender"
    )

    override fun writeToParcel(parcel: Parcel, flags: Int){
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User = User(parcel)

        override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
    }
}