package com.example.outfitgenerator

import android.net.Uri

data class Clothing(var image_id: Uri, var image_title: String = "") {
    var id = Uri.PARCELABLE_WRITE_RETURN_VALUE
}