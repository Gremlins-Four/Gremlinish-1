package com.example.outfitgenerator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.firestore.FirebaseFirestore


val database = FirebaseFirestore.getInstance().document("sampleData/collection")
//private val TAG = "CollectionViewModel"
class CollectionViewModel: ViewModel() {
    val clothing = mutableListOf<Collection>()
    init {
        for (i in 0 until 100) {
            val clothingItem = Collection()
            clothingItem.piece = ""
            clothingItem.hat = database.collection("Hat").toString()
            clothingItem.shirt = database.collection("Shirt").toString()
            clothingItem.pants = database.collection("Pants").toString()
            clothingItem.shoes = database.collection("Shoes").toString()
            clothing += clothingItem
        }
    }

}