package com.example.outfitgenerator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.scope.requireScopeActivity
import java.io.File
import java.io.IOException
import kotlin.random.Random


class FirstFragment: Fragment() {
   // private lateinit var uploadbutton: Button
    private lateinit var randombutton: Button
    private lateinit var collectionbutton: Button
    private lateinit var saveoutfitbutton: Button


    //val context = this


    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        //fun startPhotoFragment()
        fun startCollectionViewFragment()
    }

    private var callbacks: Callbacks? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
        requireContext()
        requireActivity()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        //uploadbutton = view.findViewById(R.id.upload_button)
        collectionbutton = view.findViewById(R.id.closet_button)
        randombutton = view.findViewById(R.id.random_button)
        saveoutfitbutton = view.findViewById(R.id.save_outfit_button)
        saveoutfitbutton.isEnabled = false

        // Set ImageView containers
        val hatImageView: ImageView = view.findViewById(R.id.temp_hat_text)
        val shirtImageView: ImageView = view.findViewById(R.id.temp_shirt_text)
        val pantsImageView: ImageView = view.findViewById(R.id.temp_pants_text)
        val shoesImageView: ImageView = view.findViewById(R.id.temp_shoes_text)

        val dbHelper = DBHandler(requireActivity())
        //val db = dbHelper.readableDatabase
        val hello = 2

        fun randomizeHats(): Bitmap? {
            var hats = dbHelper?.readHatData()
            var sizeHat = hats?.size
            var randomHats: Int? = sizeHat?.let { Random.nextInt(0, it) }
            if (randomHats != null) {
                return downloadPhoto(hats?.get(randomHats).image_title.toString())
            }
            return null
        }

        fun randomizeShirt(): Bitmap?{
            var shirts = dbHelper?.readShirtData()
            var sizeShirt = shirts?.size
            var randomShirt: Int? = sizeShirt?.let { Random.nextInt(0, it) }
            if (randomShirt != null) {
                return downloadPhoto(shirts?.get(randomShirt).image_title.toString())
           }
           return null
        }

        fun randomizePants(): Bitmap?{
            var pants = dbHelper?.readPantsData()
            var sizePants = pants?.size
            var randomPants: Int? = sizePants?.let{ Random.nextInt(0, it) }
            if (randomPants != null) {
               return downloadPhoto(pants?.get(randomPants).image_title.toString())
            }
            return null
        }

        fun randomizeShoes(): Bitmap?{
            var shoes = dbHelper?.readShoesData()
            var sizeShoes = shoes?.size
            var randomShoes: Int? = sizeShoes?.let{ Random.nextInt(0, it) }
            if (randomShoes != null) {
                val imageName = shoes?.get(randomShoes).image_title.toString()
                return downloadPhoto(imageName)
            }
            return null
        }


        //ImageView containers
        //Outfit Generator
        randombutton.setOnClickListener {
            val hat = randomizeHats()
            val shirt = randomizeShirt()
            val pant = randomizePants()
            val shoe = randomizeShoes()

            hatImageView.setImageBitmap(hat)
            shirtImageView.setImageBitmap(shirt)
            pantsImageView.setImageBitmap(pant)
            shoesImageView.setImageBitmap(shoe)
            saveoutfitbutton.isEnabled = true
        }


        collectionbutton.setOnClickListener {
            callbacks?.startCollectionViewFragment()
        }
        saveoutfitbutton.setOnClickListener {
            //saveOutfitToDatabase()
        }
        //uploadbutton.setOnClickListener {
        //    callbacks?.startPhotoFragment()
        //}
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
            mStorageReference.getFile(localFile).addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                    Toast.makeText(requireActivity(), "Picture Retrieved", Toast.LENGTH_SHORT)
                        .show()
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Bitmap = bitmap
                }).addOnFailureListener(OnFailureListener {
                    Toast.makeText(
                        requireActivity(),
                        "Error Occurred",
                        Toast.LENGTH_SHORT
                    ).show()
                })
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Bitmap
    }
}