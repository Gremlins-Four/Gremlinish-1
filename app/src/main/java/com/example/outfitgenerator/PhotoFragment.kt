package com.example.outfitgenerator

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

open class PhotoFragment: Fragment() {
    private lateinit var savebutton: Button
    private lateinit var titleField: EditText
    private lateinit var cancelbutton: Button
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
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_photo, container, false)


        titleField = view.findViewById(R.id.clothing_title)

        savebutton = view.findViewById(R.id.button10)

        cancelbutton = view.findViewById(R.id.cancel_button)
        // This button will allow user to return to main layout


        cancelbutton.setOnClickListener {
            callbacks?.startFirstFragment()
            // Return to main layout
        }
        return view

    }



    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_hat ->
                    if (checked) {
                        // Object is classified as a hat
                    }
                R.id.radio_shirt ->
                    if (checked) {
                        // Object is classified as a shirt
                    }
                R.id.radio_pants ->
                    if (checked) {
                        // Object is classified as pants
                    }
                R.id.radio_shoes ->
                    if (checked) {
                        // Object is classified as shoes
                    }
            }
        }
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


    }

    val titleWatcher = object: TextWatcher {
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



