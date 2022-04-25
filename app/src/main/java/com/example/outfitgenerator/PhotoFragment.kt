package com.example.outfitgenerator

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.content.Intent
import android.graphics.Bitmap
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
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import com.google.firebase.storage.StorageReference
//import java.io.IOException
//import com.google.firebase.firestore.FirebaseFirestore



open class PhotoFragment: Fragment() {

    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    private val PICK_IMAGE_REQUEST = 22

    private val RESULT_LOAD_IMAGE = 1
    private lateinit var iv_image: ImageView
    private lateinit var savebutton: Button
    private lateinit var titleField: EditText
    private lateinit var cancelbutton: ImageButton
    private lateinit var camerabutton: Button
    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView
    private lateinit var insertbutton: Button
    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun startFirstFragment()
        fun cameraTime()
        fun saveClothes()
        fun startCollectionViewFragment()
    }
    private var callbacks: Callbacks? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        var mStorageReference = FirebaseStorage.getInstance().reference.child("picture/sunset.JPG")

        try {
            val localFile = File.createTempFile("sunset", "jpg")
            mStorageReference.getFile(localFile)
                .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                    Toast.makeText(requireActivity(), "Picture Retrieved", Toast.LENGTH_SHORT)
                        .show()
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    imageView = view?.findViewById(R.id.iv_image)!!
                    imageView.setImageBitmap(bitmap)
                }).addOnFailureListener(OnFailureListener {
                    Toast.makeText(
                        requireActivity(),
                        "Error Ocurred",
                        Toast.LENGTH_SHORT
                    ).show()
                })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.getReference()

        iv_image = view.findViewById(R.id.iv_image)

        insertbutton = view.findViewById(R.id.insert_button)

        titleField = view.findViewById(R.id.clothing_title)

        savebutton = view.findViewById(R.id.button10)
        camerabutton = view.findViewById(R.id.camera_button)

        cancelbutton = view.findViewById(R.id.cancel_button)
        // This button will allow user to return to main layout
        spinner=view.findViewById<Spinner>(R.id.spinner)


        val database = FirebaseFirestore.getInstance().document("sampleData/collection")


        fun saveToDatabase() {

            var clothingTitle = titleField.getText().toString()

            val newClothing = hashMapOf(
                "title" to clothingTitle
            )


            val toast1 = Toast.makeText(requireActivity(),"Saving", Toast.LENGTH_SHORT)
            toast1.show()
            // val newImage = hashMapof
            // or Bitmap???


            database.collection("Wardrobe")
                .add(newClothing)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "ClothingSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding clothing", e)
                }
            val toast2 = Toast.makeText(requireActivity(), "Saved", Toast.LENGTH_LONG)
            toast2.show()
        }


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
            callbacks?.startCollectionViewFragment()
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
            SelectImage()

        }

        fun onActivityResult(requestCode: Int, data: Intent?) {
            if (requestCode == RESULT_LOAD_IMAGE && data != null) {
                val selectedImage: Uri? = data.data
                iv_image.setImageURI(selectedImage)
            }
            }

        return view

    }


    private fun SelectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Image from here..."),
            PICK_IMAGE_REQUEST)
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



