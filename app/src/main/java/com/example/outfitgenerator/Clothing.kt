package com.example.outfitgenerator

import android.net.Uri
import kotlin.random.Random

data class Clothing(var image_id: String, var image_title: String = "") {
    var id = Random.nextInt(100000, 999999)
}