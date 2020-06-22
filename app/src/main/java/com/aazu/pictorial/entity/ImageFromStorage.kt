package com.aazu.pictorial.entity

import android.net.Uri

data class ImageFromStorage(
    var id: String,
    var uri: Uri,
    var width: Int,
    var height: Int
)