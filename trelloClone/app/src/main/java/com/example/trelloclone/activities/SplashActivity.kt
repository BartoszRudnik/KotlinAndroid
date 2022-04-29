package com.example.trelloclone.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.trelloclone.databinding.ActivitySplashBinding
import com.example.trelloclone.firebase.FireStoreClass

class SplashActivity : AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val typeFace: Typeface = Typeface.createFromAsset(assets, "carbon bl.ttf")

        binding?.tvAppName?.typeface = typeFace

        val currentUserId = FireStoreClass().getCurrentUserId()

        Handler().postDelayed({
            startActivity(
                Intent(
                    this,
                    if (currentUserId.isEmpty()) {
                        IntroActivity::class.java
                    } else {
                        MainActivity::class.java
                    }
                )
            )
            finish()
        }, 2500)
    }
}