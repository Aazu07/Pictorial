package com.aazu.pictorial.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object CommonBindingUtils {

    @JvmStatic
    @BindingAdapter(value = ["imageUri", "width", "height"], requireAll = true)
    fun setImageFromPath(imageview: ImageView, imageURI: String?, width: Int, height: Int) {
        if (imageURI != null) {
            Glide.with(imageview.context).load(Uri.parse(imageURI))
                .apply(RequestOptions().override(width, height))
                .into(imageview)

        }

    }
}