package com.example.outfitgenerator

import java.util.*

data class Collection(val id: UUID = UUID.randomUUID(),
                        var piece: String = "",
                        var hat: String = "",
                        var shirt: String = "",
                        var pants: String = "",
                        var shoes: String = "") {



}

