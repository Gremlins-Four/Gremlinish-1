package com.example.outfitgenerator

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest


import android.content.pm.PackageManager.PERMISSION_GRANTED

import android.net.Uri
import android.os.Environment
import android.os.PersistableBundle
import android.widget.*
import androidx.fragment.app.FragmentActivity
// import com.example.outfitgenerator.databinding.ActivityMainBinding

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Array.newInstance

private const val TAG = "MainActivity"

class MainActivity : FragmentActivity(), FirstFragment.Callbacks, PhotoFragment.Callbacks, CollectionViewFragment.Callbacks, OutfitFragment.Callbacks {
    val CLOTHING_KEY = "clothes"
    val TAG = "ClothingArticle"
    private lateinit var iv_image: ImageView
    //private lateinit var binding: ActivityMainBinding


    companion object{
        const val CAMERA_PERMISSION_CODE = 1
        const val CAMERA_REQUEST_CODE = 2
        const val GALLERY_REQUEST_CODE = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // iv_image = findViewById(R.id.iv_image)
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

    override fun startOutfitFragment() {
        val fragment4 = OutfitFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment4).addToBackStack(null).commit()
    }



    override fun cameraTime(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PERMISSION_GRANTED ){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }

    }















    override fun saveClothes() {
        val clothesView = findViewById<View>(R.id.clothing_title) as EditText
        val clothesText = clothesView.text.toString()
        if (clothesText.isEmpty()) {
            return
        }
        val dataToSave: MutableMap<String, Any> = HashMap()
        dataToSave[CLOTHING_KEY] = clothesText
        mDocRef.set(dataToSave).addOnSuccessListener {
            Log.d(
                TAG,
                "Clothing has been saved"
            )
        }.addOnFailureListener { e -> Log.w(TAG, "Clothing was not saved!", e) }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            } else{
                Toast.makeText(this, "Access Denied", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CAMERA_PERMISSION_CODE && resultCode == RESULT_OK) {//CAMERA_REQUEST_CODE) {
                val thumbNail: Bitmap = data!!.extras!!.get("data") as Bitmap
                iv_image.setImageBitmap(thumbNail)
            }

        }
    }



    private val mDocRef = FirebaseFirestore.getInstance().document("sampleData/collection")

    fun saveClothes(view: View?) {
        val clothesView = view?.findViewById<View>(R.id.clothing_title) as EditText
        val clothesText = clothesView.text.toString()
        if (clothesText.isEmpty()) {
            return
        }
        val dataToSave: MutableMap<String, Any> = HashMap()
        dataToSave[CLOTHING_KEY] = clothesText
        mDocRef.set(dataToSave).addOnSuccessListener {
            Log.d(
                TAG,
                "Clothing has been saved"
            )
        }.addOnFailureListener { e -> Log.w(TAG, "Clothing was not saved!", e) }
    }

}

