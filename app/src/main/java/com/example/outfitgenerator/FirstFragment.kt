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


class FirstFragment: Fragment() {
    private lateinit var uploadbutton: Button
    private lateinit var randombutton: Button
    private lateinit var collectionbutton: Button

    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun startPhotoFragment()
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



        uploadbutton = view.findViewById(R.id.button8)
        collectionbutton = view.findViewById(R.id.button6)




        collectionbutton.setOnClickListener {
            callbacks?.startCollectionViewFragment()
        }

        uploadbutton.setOnClickListener {
            callbacks?.startPhotoFragment()
        }
        return view

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}