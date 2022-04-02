package com.example.outfitgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var uploadbutton: Button
    private lateinit var randombutton: Button
    private lateinit var collectionbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        uploadbutton = findViewById(R.id.button8)


        uploadbutton.setOnClickListener {
            val fragment = PhotoFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }

    }
}