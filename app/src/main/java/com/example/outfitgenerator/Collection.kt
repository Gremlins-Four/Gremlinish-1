package com.example.outfitgenerator

import com.google.firebase.database.Exclude
import java.util.*
//val id: UUID = UUID.randomUUID()
var collectionList = mutableListOf<Collection>()
data class Collection(var title: String ?= null,
                        var tag: String ?= null)





