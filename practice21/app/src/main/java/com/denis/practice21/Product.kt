package com.denis.practice21

import android.os.Parcelable


data class Product(
    var name: String, var description: String,
    var cost: Int, var isOnStock: Boolean,
    var imgResource: Int) { }