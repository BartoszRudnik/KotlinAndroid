package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.database.DatabaseHandler
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.HappyPlaceModel

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        binding?.fabAddHappyPlace?.setOnClickListener{
            val intent = Intent(this, AddHappyPlaceActivity::class.java)

            startActivity(intent)
        }

        getHappyPlacesListFromLocalDB()
    }

    private fun getHappyPlacesListFromLocalDB(){
        val dbHandler = DatabaseHandler(this)
        val getHappyPlaceList : ArrayList<HappyPlaceModel> = dbHandler.getHappyPlacesList()
    }
}