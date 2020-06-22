package com.aazu.pictorial.adapter

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aazu.pictorial.MainActivity
import com.aazu.pictorial.R
import com.aazu.pictorial.databinding.ItemImageRecyclerviewBinding
import com.aazu.pictorial.entity.ImageFromStorage


class ImagesAdapter(private val context: Context, private val list: ArrayList<ImageFromStorage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var screenWidth = 0

    init {
        val displayMetrics = DisplayMetrics()
        (context as MainActivity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val paddingAdded = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            30f,
            context.resources.displayMetrics
        ).toInt()
        screenWidth = width - paddingAdded
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemImageRecyclerviewBinding>(
            LayoutInflater.from(context),
            R.layout.item_image_recyclerview, parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ImageViewHolder).onBind()
    }

    inner class ImageViewHolder(private val itemBinding: ItemImageRecyclerviewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind() {
            val size: Size
            if (list[adapterPosition].width != 0 && list[adapterPosition].height != 0) {
                size = getSize(list[adapterPosition])
            } else {
                size = Size((screenWidth / 2), 0)
            }
            itemBinding.path = list[adapterPosition].uri.toString()
            itemBinding.width = size.width
            itemBinding.height = size.height
        }

        //        Unable to use this approach as webP images have 0 width and height
        private fun getSize(imageFromStorage: ImageFromStorage): Size {
            Log.d(
                "ORIGINAL",
                "original width ${imageFromStorage.width} height ${imageFromStorage.height}"
            )
            val aspectRatio = imageFromStorage.width.toFloat() / imageFromStorage.height.toFloat()
            Log.d("ASPECTRATIO", "aspectratio $aspectRatio")
            val newWidth = screenWidth / 2
            val newHeight = newWidth.toFloat() / aspectRatio
            Log.d("SIZES", "Width $newWidth and height $newHeight")
            return Size(newWidth, newHeight.toInt())
        }
    }

}
