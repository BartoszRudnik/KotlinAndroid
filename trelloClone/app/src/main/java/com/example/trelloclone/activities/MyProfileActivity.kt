package com.example.trelloclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.trelloclone.R
import com.example.trelloclone.databinding.ActivityMyProfileBinding
import com.example.trelloclone.firebase.FireStoreClass
import com.example.trelloclone.models.User

class MyProfileActivity : BaseActivity() {
    private var binding: ActivityMyProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyProfileBinding.inflate(layoutInflater)

        setContentView(binding?.root)
        setupActionBar()

        FireStoreClass().signInUser(this)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding?.toolbarMyProfileActivity)

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile)
        }

        binding?.toolbarMyProfileActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun setupUI(user: User) {
        binding?.ivUserImage?.let {
            Glide.with(this@MyProfileActivity).load(user.image).centerCrop()
                .placeholder(R.drawable.ic_user_place_holder).into(it)
        }

        binding?.etName?.setText(user.name)
        binding?.etEmail?.setText(user.email)
        binding?.etMobile?.setText(user.mobile.toString())
    }
}