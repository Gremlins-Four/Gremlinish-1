package com.example.outfitgenerator

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.random.Random




class FirstFragment: Fragment() {
    private lateinit var randombutton: Button
    private lateinit var collectionbutton: Button
    private lateinit var saveoutfitbutton: Button
    private lateinit var hatView: ImageView
    private lateinit var shirtView: ImageView
    private lateinit var pantsView: ImageView
    private lateinit var shoesView: ImageView
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
        randombutton.setOnClickListener {
            //var listHat = listOf("Yellow Hat", "Blue Hat", "Red Hat", "Green Hat", "Baldus Hat")
            //var randHat: Int = Random.nextInt(0, 5)
            //var listShirt = listOf("Yellow Shirt", "Blue Shirt", "Red Shirt", "Green Shirt", "Baldus Shirt")
            //var randShirt: Int = Random.nextInt(0, 5)
            //var listPants = listOf("Yellow Pants", "Blue Pants", "Red Pants", "Green Pants", "Baldus Pants")
            //var randPants: Int = Random.nextInt(0, 5)
            //var listShoes = listOf("Yellow Shoes", "Blue Shoes", "Red Shoes", "Green Shoes", "Baldus Shoes")
            //var randShoes: Int = Random.nextInt(0, 5)
            //hatTextView.text = listHat[randHat]
            //shirtTextView.text = listShirt[randShirt]
            //pantsTextView.text = listPants[randPants]
            //shoesTextView.text = listShoes[randShoes]

            saveoutfitbutton.isEnabled = true
        }

        collectionbutton.setOnClickListener {
            callbacks?.startCollectionViewFragment()
        }
        saveoutfitbutton.setOnClickListener {
            //saveOutfitToDatabase()
        }
        return view

    } //End onCreateView

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    fun saveOutfitToDatabase(){
        //val newOutfit = hashMapOf("HatImage" to, "ShirtImage" to, "PantsImage to, "ShoesImage" to)
    }

    fun downloadPhoto(photoTitle: String): Bitmap? {

        var mStorageReference = FirebaseStorage.getInstance().reference.child("pictures/$photoTitle")
        var Bitmap: Bitmap? = null

        try {
            val localFile = File.createTempFile("newPhoto","jpg")
            mStorageReference.getFile(localFile)
                .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                    Toast.makeText(requireActivity(), "Picture Retrieved", Toast.LENGTH_SHORT)
                        .show()
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Bitmap = bitmap
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

        return Bitmap
    }
}