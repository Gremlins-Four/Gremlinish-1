package com.example.outfitgenerator

import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.OutfitBinding

class OutfitViewHolder(
    private val outfitBinding: OutfitBinding
) : RecyclerView.ViewHolder(outfitBinding.root)
{
    fun bindSavedOutfit(savedOutfit: SavedOutfit)
    {
        outfitBinding.hatImage
        outfitBinding.shirtImage
        outfitBinding.pantsImage
        outfitBinding.shoesImage
    }
}