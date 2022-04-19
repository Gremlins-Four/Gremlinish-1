package com.example.outfitgenerator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

private val TAG = "CollectionViewFragment"
class CollectionViewModel: ViewModel() {
    val clothing = mutableListOf<Collection>()
    init {
        for (i in 0 until 100) {
            val clothingItem = Collection()
            //clothingItem.piece
            //clothingItem.hat
            //clothingItem.shirt
            //clothingItem.pants
            //clothingItem.shoes
            clothing += clothingItem
        }
    }
}