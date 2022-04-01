package com.example.outfitgenerator

import androidx.annotation.StringRes

data class Categorize(@StringRes val textReID: Int, val hat:Boolean? = null,
                        val shirt:Boolean? = null, val pants:Boolean? = null,
                        val shoes:Boolean? = null) {

}



// @StringRes val textReID will be the name of the article of clothing entered by the user,
// the user will choose which category the image belongs ina

