package com.example.outfitgenerator

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
//import java.io.IOException
//import com.google.firebase.firestore.FirebaseFirestore



open class PhotoFragment: Fragment() {

    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    private val PICK_IMAGE_REQUEST = 22

    private val RESULT_LOAD_IMAGE = 1
    private lateinit var savebutton: Button
    private lateinit var titleField: EditText
    private lateinit var cancelbutton: ImageButton
    private lateinit var camerabutton: Button
    private lateinit var spinner: Spinner
    private lateinit var imageView: ImageView
    private lateinit var insertbutton: Button
    private lateinit var selectedImage: ImageView
    private lateinit var currentPhotoPath: String
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        storage = FirebaseStorage.getInstance()

        storageReference = FirebaseStorage.getInstance().reference

        selectedImage = view.findViewById(R.id.iv_image)

        insertbutton = view.findViewById(R.id.insert_button)

        titleField = view.findViewById(R.id.clothing_title)

        savebutton = view.findViewById(R.id.button10)
        camerabutton = view.findViewById(R.id.camera_button)

        cancelbutton = view.findViewById(R.id.cancel_button)
        // This button will allow user to return to main layout
        spinner=view.findViewById<Spinner>(R.id.spinner)


        //points to and references to firestore file/folderpath (not storage—where the photos are stored)
        val database = FirebaseFirestore.getInstance().document("sampleData/collection")


        /*
        starts camera intent/function which asks for permission, creates folderpath,
        and gives image unique name
         */
        camerabutton.setOnClickListener { askCameraPermissions() }

        insertbutton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, MainActivity.GALLERY_REQUEST_CODE)
        }

        //saves title to firestore, thats about it
        fun saveToDatabase() {

            val clothingTitle = titleField.text.toString()

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


          spinner=view.findViewById<Spinner>(R.id.spinner)


        spinner.adapter =
            ArrayAdapter.createFromResource(requireActivity(), R.array.dropdownmenu, android.R.layout.simple_spinner_item)
        spinner.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{

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

        savebutton.setOnClickListener {
            saveToDatabase()
        }

        return view
    }
    //after the result of either camera or choose from gallery activity, saves correlated photo to
    //firebase storage
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //if (requestCode == RESULT_LOAD_IMAGE && data != null) {
          //  val selectedImage: Uri? = data.data
            //iv_image.setImageURI(selectedImage)
        //}
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val f = File(currentPhotoPath)
                selectedImage.setImageURI(Uri.fromFile(f))
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f))
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val contentUri = Uri.fromFile(f)
                mediaScanIntent.data = contentUri
                requireActivity().sendBroadcast(mediaScanIntent)
                uploadImageToFirebase(f.name, contentUri)
            }
        }

        if (requestCode == MainActivity.GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val contentUri = data!!.data
                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri!!)
                Log.d("tag", "onActivityResult: Gallery Image Uri:  $imageFileName")
                selectedImage.setImageURI(contentUri)
                uploadImageToFirebase(imageFileName, contentUri)
            }
        }
    }

    fun getFileExt(contentUri: Uri): String? {
        val c: ContentResolver = requireActivity().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(c.getType(contentUri))
    }

    //creates filepath before camera intent is fired and gives each new photo unique file name
    @Throws(IOException::class)
    fun createImageFile(): File? {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        val storageDir =
            getExternalStoragePublicDirectory(DIRECTORY_PICTURES)
        //new camera intent/function fails here when creating the image file
        val toast2 = Toast.makeText(requireActivity(), "$storageDir", Toast.LENGTH_LONG)
        toast2.show()
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            requireActivity().cacheDir  /* directory */
        )
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.absolutePath

        return image
    }

    //the new camera intent/function. Requires a filepath in order for it to be fired, otherwise,
    // fatal exception
    fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent

        if (activity?.packageManager?.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)  != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                val toast2 = Toast.makeText(requireActivity(), "Photo filepath created", Toast.LENGTH_LONG)
                toast2.show()
                val photoURI = FileProvider.getUriForFile(
                    requireActivity(),
                    "com.example.outfitgenerator.fileprovider",
                    photoFile
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, MainActivity.CAMERA_REQUEST_CODE)

            }
        }
        else {
            val toast3 = Toast.makeText(requireActivity(), "No filepath created", Toast.LENGTH_LONG)
            toast3.show()
        }
    }

    //uploads the new picture from camera or selected photo from gallery to the firebase storage
    // in pictures/ folder with each photos' unique name
    //this is probably where we can upload the ID and tag info into the firestore so we can
    // retrieve the images later
    fun uploadImageToFirebase(name: String, contentUri: Uri) {
        val image = storageReference!!.child("pictures/$name")
        image.putFile(contentUri)
            .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot?> {
                override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot?) {
                    image.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(uri: Uri) {
                            Log.d("tag", "onSuccess: Uploaded Image URl is $uri")
                        }
                    })
                    Toast.makeText(requireActivity(), "Image Is Uploaded.", Toast.LENGTH_SHORT)
                        .show()
                }
            }).addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    Toast.makeText(requireActivity(), "Upload Failed.", Toast.LENGTH_SHORT).show()
                }
            })

    }

    //asks user to grant permission to use the camera
    fun askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                MainActivity.CAMERA_PERMISSION_CODE
            )
        } else {
            dispatchTakePictureIntent()      }
    }

    //fires camera intent if permission is granted, otherwise tells user camera needs permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MainActivity.CAMERA_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Camera Permission is Required to Use camera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

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



