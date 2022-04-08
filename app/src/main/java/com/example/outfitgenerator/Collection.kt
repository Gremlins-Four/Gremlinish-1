package com.example.outfitgenerator

import java.util.*

data class Collection(val id: UUID = UUID.randomUUID(),
                        var piece: String = "",
                        val hat: Boolean? = null,
                        val shirt: Boolean? = null,
                        val pants: Boolean? = null,
                        val shoes: Boolean? = null) {



}

