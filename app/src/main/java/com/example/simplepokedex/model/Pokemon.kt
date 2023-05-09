package com.example.simplepokedex.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val name : String,
    val types : List<String>,
    val description : String,
    val image : Bitmap
) : Parcelable
