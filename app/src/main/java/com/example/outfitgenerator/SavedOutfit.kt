package com.example.outfitgenerator

import com.google.firebase.database.Exclude
import java.util.*
var savedOutfitList = mutableListOf<Collection>()
data class SavedOutfit(var hatImage: String ?= null,
                       var shirtImage: String ?= null,
                       var pantsImage: String ?= null,
                       var shoesImage: String ?= null)
