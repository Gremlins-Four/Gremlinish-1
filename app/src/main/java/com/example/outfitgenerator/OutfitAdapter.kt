package com.example.outfitgenerator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.OutfitBinding

class OutfitAdapter (private val savedOutfits: List<SavedOutfit>)
    :RecyclerView.Adapter<OutfitViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val bind = OutfitBinding.inflate(layout, parent,false)
        return OutfitViewHolder(bind)
    }

    override fun onBindViewHolder(holder: OutfitViewHolder, position: Int) {
        holder.bindSavedOutfit(savedOutfits[position])
    }

    override fun getItemCount(): Int= savedOutfits.size}