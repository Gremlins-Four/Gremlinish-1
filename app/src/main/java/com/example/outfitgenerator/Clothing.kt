package com.example.outfitgenerator

import android.net.Uri
import kotlin.random.Random

// Create a data class that stores the image ID and user input image title
data class Clothing(var image_id: String, var image_title: String = "") {
    var id = Random.nextInt(100000, 999999)
}