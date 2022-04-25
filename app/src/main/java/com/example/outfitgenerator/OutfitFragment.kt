package com.example.outfitgenerator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class OutfitFragment: Fragment() {

    private lateinit var outfitButton: Button
    private lateinit var itemsButton: Button
    private lateinit var xButton: Button

    interface Callbacks {
        fun startPhotoFragment()
        fun startCollectionViewFragment()
        fun startFirstFragment()
    }

    private var callbacks: Callbacks? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_outfit, container, false)
        outfitButton=view.findViewById(R.id.outfit_outfits_button)
        itemsButton=view.findViewById(R.id.outfit_items_button)
        xButton=view.findViewById(R.id.outfit_x)

        outfitButton.setOnClickListener{
           //do nothing, you're already here
        }

        itemsButton.setOnClickListener {
            callbacks?.startCollectionViewFragment()
        }

        xButton.setOnClickListener{
            callbacks?.startFirstFragment()
        }

        return view

    }

    override fun onStart() {
        super.onStart()
        //left blank
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}
