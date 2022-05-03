package com.example.trelloclone.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.trelloclone.R
import com.example.trelloclone.databinding.ActivityCreateBoardBinding
import com.example.trelloclone.utils.Constants

class CreateBoardActivity : AppCompatActivity() {
    private var binding: ActivityCreateBoardBinding? = null
    private var mSelectedUserImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateBoardBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarCreateBoardActivity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile)
        }

        binding?.toolbarCreateBoardActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.ivBoardImage?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Constants.showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE_REQUEST_CODE && data!!.data != null) {
            mSelectedUserImage = data.data
        }

        Glide.with(this@CreateBoardActivity).load(mSelectedUserImage).centerCrop()
            .placeholder(R.drawable.ic_board_place_holder).into(binding?.ivBoardImage!!)
    }
}