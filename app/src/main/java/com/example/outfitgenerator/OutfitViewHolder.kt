package com.example.outfitgenerator

import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.OutfitBinding

class OutfitViewHolder(
    private val outfitBinding: OutfitBinding
) : RecyclerView.ViewHolder(outfitBinding.root)
{
    fun bindSavedOutfit(savedOutfit: SavedOutfit)
    {
        //binds image resources to the outfit for card view
        outfitBinding.hatImage.setImageResource(savedOutfit.hatImage as Int)
        outfitBinding.shirtImage.setImageResource(savedOutfit.shirtImage as Int)
        outfitBinding.pantsImage.setImageResource(savedOutfit.pantsImage as Int)
        outfitBinding.shoesImage.setImageResource(savedOutfit.shoesImage as Int)
    }
}