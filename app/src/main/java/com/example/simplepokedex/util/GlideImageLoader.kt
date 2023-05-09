package com.example.simplepokedex.util

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class GlideImageLoader @Inject constructor(private val context: Context) : ImageLoader {
    override fun getImage(url: String): Bitmap {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontTransform()

        val futureTarget = Glide.with(context).asBitmap()
            .apply(requestOptions)
            .load(url)
            .submit()

        return futureTarget.get()
    }
}