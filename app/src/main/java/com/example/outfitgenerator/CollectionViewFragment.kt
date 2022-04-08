package com.example.outfitgenerator

import android.content.Context
import android.os.Bundle
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

class CollectionViewFragment: Fragment() {
    private lateinit var xButton: Button

    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
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
        val view = inflater.inflate(R.layout.fragment_collectionview, container, false)
        xButton=view.findViewById(R.id.xbutton)

        xButton.setOnClickListener {
            callbacks?.startFirstFragment()
            // Return to main layout
        }


        return view

    }

    //add a listener to the Edit Text View Widget we just created
    override fun onStart() {
        super.onStart()
        //left blank
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    val titleWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            sequence: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
            //leave it blank bec. I don't need to override anything rn
        }

        override fun onTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {
            //leave it blank
            //clothing.title = sequence.toString()
        }

        override fun afterTextChanged(sequence: Editable?) {
            //leave blank
        }

    }
}
