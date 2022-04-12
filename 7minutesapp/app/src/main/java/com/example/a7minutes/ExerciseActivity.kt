package com.example.a7minutes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minutes.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExcerciseBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}