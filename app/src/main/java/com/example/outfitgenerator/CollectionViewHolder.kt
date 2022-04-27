package com.example.outfitgenerator

import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.ClothingItemBinding


class CollectionViewHolder(private val clothingItemBinding: ClothingItemBinding): RecyclerView.ViewHolder(clothingItemBinding.root){
    fun bindCollection(collection: Collection){
        clothingItemBinding.clothingImage
        clothingItemBinding.clothingTitle.text = collection.title
        clothingItemBinding.clothingTag.text = collection.tag
    }

}
