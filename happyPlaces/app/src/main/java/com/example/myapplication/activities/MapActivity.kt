package com.example.myapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    var binding: ActivityMapBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarMap)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.toolbarMap?.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}