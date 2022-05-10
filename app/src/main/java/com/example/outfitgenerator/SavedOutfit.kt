package com.example.outfitgenerator

import com.google.firebase.database.Exclude
import java.util.*
var savedOutfitList = mutableListOf<SavedOutfit>()
data class SavedOutfit(var hatImage: Int ?= null,
                       var shirtImage: Int ?= null,
                       var pantsImage: Int ?= null,
                       var shoesImage: Int ?= null,
                       val id: Int? = savedOutfitList.size )

