package com.example.outfitgenerator

import android.content.Context
import android.content.Intent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.EditText
import java.util.*
import kotlin.random.Random




class FirstFragment: Fragment() {
    private lateinit var randombutton: Button
    private lateinit var collectionbutton: Button
    private lateinit var saveoutfitbutton: Button
    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun startCollectionViewFragment()
    }
    private var callbacks: Callbacks? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        //XML view find
        collectionbutton = view.findViewById(R.id.closet_button)
        randombutton = view.findViewById(R.id.random_button)
        saveoutfitbutton = view.findViewById(R.id.save_outfit_button)
        saveoutfitbutton.isEnabled = false

        //Buttons
        //Outfit Generator
        val hatTextView: TextView = view.findViewById(R.id.temp_hat_text)
        val shirtTextView: TextView = view.findViewById(R.id.temp_shirt_text)
        val pantsTextView: TextView = view.findViewById(R.id.temp_pants_text)
        val shoesTextView: TextView = view.findViewById(R.id.temp_shoes_text)
        randombutton.setOnClickListener {
            var listHat = listOf("Yellow Hat", "Blue Hat", "Red Hat", "Green Hat", "Baldus Hat")
            var randHat: Int = Random.nextInt(0, 5)
            var listShirt = listOf("Yellow Shirt", "Blue Shirt", "Red Shirt", "Green Shirt", "Baldus Shirt")
            var randShirt: Int = Random.nextInt(0, 5)
            var listPants = listOf("Yellow Pants", "Blue Pants", "Red Pants", "Green Pants", "Baldus Pants")
            var randPants: Int = Random.nextInt(0, 5)
            var listShoes = listOf("Yellow Shoes", "Blue Shoes", "Red Shoes", "Green Shoes", "Baldus Shoes")
            var randShoes: Int = Random.nextInt(0, 5)
            hatTextView.text = listHat[randHat]
            shirtTextView.text = listShirt[randShirt]
            pantsTextView.text = listPants[randPants]
            shoesTextView.text = listShoes[randShoes]

            saveoutfitbutton.isEnabled = true
        }

        collectionbutton.setOnClickListener {
            callbacks?.startCollectionViewFragment()
        }
        saveoutfitbutton.setOnClickListener {

        }
        return view

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}