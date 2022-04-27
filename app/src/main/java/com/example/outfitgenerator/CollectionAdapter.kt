package com.example.outfitgenerator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.ClothingItemBinding

class CollectionAdapter(private val collection: List<Collection>): RecyclerView.Adapter<CollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val bind = ClothingItemBinding.inflate(layout, parent, false)
        return CollectionViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int){
        holder.bindCollection(collection[position])
    }

    override fun getItemCount(): Int = collection.size
}