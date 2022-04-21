package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMapBinding
import com.example.myapplication.models.HappyPlaceModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    var binding: ActivityMapBinding? = null

    private var mHappyPlaceModel: HappyPlaceModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)

        setContentView(binding?.root)


        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            mHappyPlaceModel = intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS)
        }

        if (mHappyPlaceModel != null) {
            setSupportActionBar(binding?.toolbarMap)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding?.toolbarMap?.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.title = mHappyPlaceModel?.title

            val supportMapFragment: SupportMapFragment =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

            supportMapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        val position = LatLng(mHappyPlaceModel!!.latitude, mHappyPlaceModel!!.longitude)

        p0.addMarker(MarkerOptions().position(position).title(mHappyPlaceModel!!.location))

        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(position, 15f)

        p0.animateCamera(newLatLngZoom)
    }
}