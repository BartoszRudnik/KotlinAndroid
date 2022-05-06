package com.example.trelloclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trelloclone.R
import com.example.trelloclone.databinding.ActivityCardDetailsBinding

class CardDetailsActivity : AppCompatActivity() {
    private var binding: ActivityCardDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardDetailsBinding.inflate(layoutInflater)

        setContentView(binding?.root)
    }
}