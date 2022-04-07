package com.example.outfitgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), FirstFragment.Callbacks, PhotoFragment.Callbacks {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null){
            val fragment = FirstFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }


    }

    override fun startPhotoFragment() {
        val fragment1 = PhotoFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment1).addToBackStack(null).commit()

    }

    override fun startCollectionViewFragment() {
        val fragment2 = CollectionViewFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment2).addToBackStack(null).commit()

    }

    override fun startFirstFragment() {
        val fragment3 = FirstFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment3).addToBackStack(null).commit()

    }



}