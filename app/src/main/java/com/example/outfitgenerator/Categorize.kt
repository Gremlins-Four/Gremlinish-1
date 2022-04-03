package com.example.outfitgenerator

import androidx.annotation.StringRes
import java.util.*

data class Categorize(val id: UUID = UUID.randomUUID(), var name: String = "", var hat:Boolean? = null,
                        var shirt:Boolean? = null, var pants:Boolean? = null,
                        var shoes:Boolean? = null) {

}



// Using categorize for the Image Capture fragment

