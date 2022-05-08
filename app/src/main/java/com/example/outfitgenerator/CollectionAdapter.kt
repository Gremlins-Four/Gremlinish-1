package com.example.outfitgenerator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.ClothingItemBinding

class CollectionAdapter(private val collection: List<Collection>): RecyclerView.Adapter<CollectionViewHolder>() { // This pulls in the collection and the RecyclerView.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder { // This creates the view that will bind with the items in the collection.
        val layout = LayoutInflater.from(parent.context)
        val bind = ClothingItemBinding.inflate(layout, parent, false)
        return CollectionViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int){ // This function binds each individual item in the collection to the clothing_item.xml
        holder.bindCollection(collection[position])
    }

    override fun getItemCount(): Int = collection.size // Returns the number of items in the collection.
}