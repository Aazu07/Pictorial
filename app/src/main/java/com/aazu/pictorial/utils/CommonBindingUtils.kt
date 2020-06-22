package com.aazu.pictorial.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CommonBindingUtils {

    @JvmStatic
    @BindingAdapter("imageUri")
    fun setImageFromPath(imageview: ImageView, imageURI: String) {
        Glide.with(imageview.context).load(Uri.parse(imageURI))
            .into(imageview)
//            .apply(RequestOptions().override(100, 100))

    }
}