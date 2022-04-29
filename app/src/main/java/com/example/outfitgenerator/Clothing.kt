package com.example.outfitgenerator

class Clothing(id: Int?, clothingTitle: String) {
    var image_id = 0
    var image_title: String? = null


    fun Clothing() {}
    fun Clothing(id: Int, title: String?) {
        image_id = id
        image_title = title

    }
}