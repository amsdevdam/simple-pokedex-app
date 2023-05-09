package com.example.simplepokedex.util

import android.graphics.Bitmap

interface ImageLoader {

    fun getImage(url: String) : Bitmap
}