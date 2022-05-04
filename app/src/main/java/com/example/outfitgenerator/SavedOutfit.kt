package com.example.outfitgenerator

import com.google.firebase.database.Exclude
import java.util.*
var savedOutfitList = mutableListOf<SavedOutfit>()
data class SavedOutfit(var hatImage: String ?= null,
                       var shirtImage: String ?= null,
                       var pantsImage: String ?= null,
                       var shoesImage: String ?= null,
                       val id: Int? = savedOutfitList.size )

