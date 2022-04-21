package com.example.myapplication.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAddHappyPlaceBinding
import com.example.myapplication.databinding.ActivityHappyPlaceDetailBinding
import com.example.myapplication.models.HappyPlaceModel

class HappyPlaceDetailActivity : AppCompatActivity() {
    private var binding: ActivityHappyPlaceDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHappyPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarPlaceDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.toolbarPlaceDetail?.setNavigationOnClickListener {
            onBackPressed()
        }

        var happyPlaceDetailModel: HappyPlaceModel? = null
        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            happyPlaceDetailModel =
                intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS)!!
        }

        if (happyPlaceDetailModel != null) {
            supportActionBar?.title = happyPlaceDetailModel.title
            binding?.ivPlaceImage?.setImageURI(Uri.parse(happyPlaceDetailModel.image))
            binding?.tvDescription?.text = happyPlaceDetailModel.description
            binding?.tvLocation?.text = happyPlaceDetailModel.location
        }

    }
}