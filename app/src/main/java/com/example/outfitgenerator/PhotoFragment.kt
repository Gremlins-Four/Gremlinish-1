package com.example.outfitgenerator

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import android.widget.Spinner
import android.widget.AdapterView
import android.widget.ArrayAdapter
//import com.google.firebase.firestore.FirebaseFirestore



open class PhotoFragment: Fragment() {
    private lateinit var savebutton: Button
    private lateinit var titleField: EditText
    private lateinit var cancelbutton: Button
    private lateinit var camerabutton: Button
    private lateinit var spinner: Spinner
    private lateinit var item: String
    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun startFirstFragment()
        fun cameraTime()
        fun saveClothes()
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
        camerabutton = view.findViewById(R.id.camera_button)
        cancelbutton = view.findViewById(R.id.cancel_button)
        // This button will allow user to return to main layout
        spinner=view.findViewById<Spinner>(R.id.spinner)
        // Dropdown menu to select tag.

        //var item: String


        spinner?.adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.dropdownmenu, android.R.layout.simple_spinner_item) as SpinnerAdapter
        spinner?.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                item = parent?.getItemAtPosition(position).toString()
            }
        }


        val database = FirebaseFirestore.getInstance().document("sampleData/collection")


        fun saveToDatabase() {
            var clothingTitle = titleField.getText().toString()
            var tagItem = item
            val newClothing = hashMapOf(
                "tag" to tagItem,
                "title" to clothingTitle
            )

            //val toast1 = Toast.makeText(this,"Saving", Toast.LENGTH_LONG)
            //toast1.show()

            database.collection("Wardrobe")
                .add(newClothing)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "ClothingSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding clothing", e)
                }
            //val toast2 = Toast.makeText(this, "Done", Toast.LENGTH_LONG)
            //toast2.show()
        }


        cancelbutton.setOnClickListener {
            callbacks?.startFirstFragment()
            // Return to main layout
        }
        camerabutton.setOnClickListener{
            callbacks?.cameraTime()
        }

        savebutton.setOnClickListener {
            saveToDatabase()
        }



        return view

    }




    //add a listener to the Edit Text View Widget we just created
   // override fun onStart() {
     //   super.onStart()
        //left blank
  //  }
   // override fun onDetach() {
     //   super.onDetach()
      //  callbacks = null
   // }


    //}

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
}



