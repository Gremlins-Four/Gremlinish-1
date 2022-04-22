package com.example.outfitgenerator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore


val database = FirebaseFirestore.getInstance().document("sampleData/collection")
//private val TAG = "CollectionViewModel"
class CollectionViewModel: ViewModel() {
    private lateinit var dataRef: DatabaseReference
    //dataRef = FirebaseDatabase.getInstance().getReference("collection")
    val clothing = mutableListOf<Collection>()
    init {
        for (i in 0 until 100) {
            val clothingItem = Collection()
            clothingItem.title = ""
            clothingItem.tag = ""
                //database.collection("tag")

            clothing += clothingItem
        }
    }

}