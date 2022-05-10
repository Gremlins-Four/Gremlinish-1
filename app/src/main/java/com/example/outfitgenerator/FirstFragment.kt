package com.example.outfitgenerator

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
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
    private lateinit var uriHat: String
    private lateinit var uriShirt: String
    private lateinit var uriPants: String
    private lateinit var uriShoes: String
    var outfitList = listOf(null)


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

        // Enable buttons
        collectionbutton = view.findViewById(R.id.closet_button)
        randombutton = view.findViewById(R.id.random_button)
        saveoutfitbutton = view.findViewById(R.id.save_outfit_button)
        saveoutfitbutton.isEnabled = false

        // Set ImageView containers
        val hatImageView: ImageView = view.findViewById(R.id.temp_hat_text)
        val shirtImageView: ImageView = view.findViewById(R.id.temp_shirt_text)
        val pantsImageView: ImageView = view.findViewById(R.id.temp_pants_text)
        val shoesImageView: ImageView = view.findViewById(R.id.temp_shoes_text)

        // Create value for database
        val dbHelper = DBHandler(requireActivity())

        // List of dummy data images for hats
        val hatList= listOf(
            "buckethat",
            "helmet",
            "twinshat",
            "winterhat",
            "viking",
            "cheesehead",
        )
        // List of dummy data images for shirts
        val shirtList= listOf(
            "dressshirt",
            "graphictee",
            "tanktop",
            "sweatshirt",
            "brotank",
            "pirate"
        )
        // List of dummy data images for pants
        val pantsList= listOf(
            "khakis",
            "pjpants",
            "redshirt",
            "jorts",
            "whitejeans",
            "hammerpants"
        )
        // List of dummy data images for shoes
        val shoesList=listOf(
            "boots",
            "converse",
            "crocs",
            "highheels",
            "newb",
            "dadsandals"
        )

        // Function to generate a random number within the hats list
        // to later display that specified image (using dummy data)
        fun randomizeHats(): Int {
            val hatContext: Context = hatImageView.getContext()
            var randomHats = Random.nextInt(0,5)
            val hats = hatList [randomHats]
            val hatsId =
                requireContext().resources.getIdentifier(hats, "drawable", requireContext().packageName)
//            var hats = dbHelper?.readHatData()
//            var sizeHat = hats!!.size
//            var randomHats =  Random.nextInt(0, sizeHat)
//            if (randomHats != null) {
//                return hats?.get(randomHats)?.let { downloadPhoto(it.image_title) }
//            }
//            return null
            return hatsId
        }

        // Function to generate a random number within the shirts list
        // to later display that specified image (using dummy data)
        fun randomizeShirt(): Int{
            val shirtContext: Context = shirtImageView.getContext()
            var randomShirts = Random.nextInt(0,5)
            val shirts = shirtList [randomShirts]
            val shirtsId =
                requireContext().resources.getIdentifier(shirts, "drawable", requireContext().packageName)
//            var shirts = dbHelper?.readShirtData()
//            var sizeShirt = shirts!!.size
//            var randomShirt = Random.nextInt(0, sizeShirt)
//            if (randomShirt != null) {
//                return shirts?.get(randomShirt)?.let { downloadPhoto(it.image_title) }
//           }
//           return null
            return shirtsId

        }

        // Function to generate a random number within the pants list
        // to later display that specified image (using dummy data)
        fun randomizePants(): Int{
            val pantsContext: Context = pantsImageView.getContext()
            var randomPants = Random.nextInt(0,5)
            val pants = pantsList [randomPants]
            val pantsId =
                requireContext().resources.getIdentifier(pants, "drawable", requireContext().packageName)
//            var pants = dbHelper?.readPantsData()
//            var sizePants = pants!!.size
//            var randomPants = Random.nextInt(0, sizePants)
//            if (randomPants != null) {
//               return pants?.get(randomPants)?.let { downloadPhoto(it.image_title) }
//            }
//            return null
            return pantsId
        }

        // Function to generate a random number within the shoes list
        // to later display that specified image (using dummy data)
        fun randomizeShoes(): Int{
            val shoesContext: Context = shoesImageView.getContext()
            var randomShoes = Random.nextInt(0,5)
            val shoes = shoesList [randomShoes]
            val shoesId =
                requireContext().resources.getIdentifier(shoes, "drawable", requireContext().packageName)
//            var shoes = dbHelper?.readShoesData()
//            var sizeShoes = shoes!!.size
//            var randomShoes = Random.nextInt(0, sizeShoes)
//            if (randomShoes != null) {
//                return shoes?.get(randomShoes)?.let { downloadPhoto(it.image_title) }
//            }
//            return null
            return shoesId
          
        }


        // Set an onClickListener that uses the randomize function for each category
        // of clothing and displays the chosen image
        randombutton.setOnClickListener {
            val hat = randomizeHats()
            val shirt = randomizeShirt()
            val pant = randomizePants()
            val shoe = randomizeShoes()

            hatImageView.setImageResource(hat)
            shirtImageView.setImageResource(shirt)
            pantsImageView.setImageResource(pant)
            shoesImageView.setImageResource(shoe)
            saveoutfitbutton.isEnabled = true
        }


        // Use callbacks to navigate to the Collection View Fragment
        collectionbutton.setOnClickListener { // This button opens the CollectionViewFragment.
            callbacks?.startCollectionViewFragment()
        }


        saveoutfitbutton.setOnClickListener {
            //This button takes the images in the imageviews and sends them as one object(Outfit) to the database.
            //saveOutfitToDatabase(uriHat, uriShirt, uriPants, uriShoes)
            var saveClothing = listOf(hatImageView, shirtImageView, pantsImageView, shoesImageView)
            outfitList.plus(saveClothing)
        }

        return view

    } //End onCreateView

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    val database = FirebaseFirestore.getInstance()

    fun saveOutfitToDatabase(uriHat: String, uriShirt: String, uriPants: String, uriShoes: String){
        val newOutfit = hashMapOf("HatImage" to uriHat, "ShirtImage" to uriShirt, "PantsImage" to uriPants, "ShoesImage" to uriShoes)
        database.collection("outfits")
            .add(newOutfit)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "OutfitSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding outfit", e)
            }
        val toast3 = Toast.makeText(requireActivity(), "Saved", Toast.LENGTH_LONG)
        toast3.show()
    }


    // Retrieve the photo from the database
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