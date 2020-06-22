package com.aazu.pictorial

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aazu.pictorial.adapter.ImagesAdapter
import com.aazu.pictorial.databinding.ActivityMainBinding
import com.aazu.pictorial.utils.MediaItemDecoration
import com.aazu.pictorial.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_FOR_PERMISSION = 1001
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        initToolbar()

        binding.imageRecyclerview.addItemDecoration(MediaItemDecoration(this, 10, 2))

        viewModel.filesList.observe(this, Observer {
            Log.d("IMAGES COUNT", "${it?.size ?: 0}")
            if (it?.isNotEmpty() == true) {
                binding.imageRecyclerview.adapter = ImagesAdapter(this, it)
                binding.errorMessage.visibility = View.GONE
            } else {
                binding.errorMessage.text = getString(R.string.no_media_file_present_on_this_device)
                binding.errorMessage.visibility = View.VISIBLE
            }
        })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setTitle(R.string.app_name)
    }

    override fun onResume() {
        super.onResume()
        // Added in onResume as the images of device can be modified when app is in Background.
        checkForReadStoragePermission()
    }

    private fun checkForReadStoragePermission() {
        val finePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (finePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_FOR_PERMISSION
            )
        } else {
            getDataFromViewModel()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_FOR_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDataFromViewModel()
            } else {
                binding.errorMessage.text = getString(R.string.need_permission_to_show_media)
                binding.errorMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun getDataFromViewModel() {
        viewModel.getImages()
    }
}