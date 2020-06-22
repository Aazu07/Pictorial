package com.aazu.pictorial.viewmodel

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aazu.pictorial.entity.ImageFromStorage
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    val filesList = MutableLiveData<ArrayList<ImageFromStorage>>()
    private var job = Job()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        job = Job()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job + coroutineExceptionHandler

    fun getImages() {
        launch {
            val pathList = ArrayList<ImageFromStorage>()

            val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaColumns._ID,
                MediaColumns.WIDTH,
                MediaColumns.HEIGHT
            )

            getApplication<Application>().contentResolver.query(
                uri,
                projection,
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    pathList.add(
                        ImageFromStorage(
                            cursor.getString(0),
                            ContentUris.withAppendedId(uri, cursor.getString(0).toLong()),
                            cursor.getInt(1),
                            cursor.getInt(2)
                        )
                    )
                }
            }
            filesList.postValue(pathList)
        }
    }

}
