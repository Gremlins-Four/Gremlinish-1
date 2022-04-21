package com.example.outfitgenerator

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore


//import com.google.firebase.firestore.FirebaseFirestore



open class PhotoFragment: Fragment() {

    private val RESULT_LOAD_IMAGE = 1
    private lateinit var iv_image: ImageView
    private lateinit var savebutton: Button
    private lateinit var titleField: EditText
    private lateinit var cancelbutton: Button
    private lateinit var camerabutton: Button
    private lateinit var spinner: Spinner
    private lateinit var insertbutton: Button
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

        iv_image = view.findViewById(R.id.iv_image)

        insertbutton = view.findViewById(R.id.insert_button)

        titleField = view.findViewById(R.id.clothing_title)

        savebutton = view.findViewById(R.id.button10)
        camerabutton = view.findViewById(R.id.camera_button)

        cancelbutton = view.findViewById(R.id.cancel_button)
        // This button will allow user to return to main layout


        val database = FirebaseFirestore.getInstance().document("sampleData/collection")


        fun saveToDatabase() {

            var clothingTitle = titleField.getText().toString()

            val newClothing = hashMapOf(
                "title" to clothingTitle
            )

            // val newImage = hashMapof
            // or Bitmap???


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









          spinner=view.findViewById<Spinner>(R.id.spinner)


        spinner?.adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.dropdownmenu, android.R.layout.simple_spinner_item) as SpinnerAdapter
        spinner?.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position).toString()
            }
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

        insertbutton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE)

        }



        fun onActivityResult(requestCode: Int, data: Intent?) {
            if (requestCode == RESULT_LOAD_IMAGE && data != null) {
                val selectedImage: Uri? = data.data

                iv_image.setImageURI(selectedImage)

                // selectedImage.toString()

            }

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



