package com.example.outfitgenerator

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

class FirstFragment: Fragment() {
    private lateinit var uploadbutton: Button
    private lateinit var randombutton: Button
    private lateinit var collectionbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        return view
        uploadbutton = view.findViewById(R.id.button8)


        //uploadbutton.setOnClickListener {
        //    val fragment = PhotoFragment()
        //    supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        //}

    }
}