package com.example.outfitgenerator

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.FragmentCollectionviewBinding
import com.firebase.ui.auth.data.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.database.Query
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener

private const val TAG = "CollectionViewFragment"

private lateinit var uploadbutton: FloatingActionButton

private var firstLoad = true //Fix for data duplication error
// This function presents the collection of clothing items.
class CollectionViewFragment: Fragment() {
    private lateinit var database2: FirebaseFirestore
    private lateinit var collectionRecyclerView: RecyclerView
    private lateinit var xButton: ImageButton
    private lateinit var itemButton: Button
    private lateinit var outfitButton: Button
    private lateinit var binding: FragmentCollectionviewBinding
    //Test function variable
    private var adapter: CollectionAdapter? = null
    private var counter: Int = 0

    /**
     * Required interface for hosting activities
     */
    //Callbacks Code
    interface Callbacks {
        fun startFirstFragment()
        fun startPhotoFragment()
        fun startOutfitFragment()
    }
    private var callbacks: Callbacks? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    } //End of Callbacks Code



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? { //
        binding = FragmentCollectionviewBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        // XML view find
        xButton = view.findViewById(R.id.xbutton)
        uploadbutton = view.findViewById(R.id.upload_button)
        itemButton=view.findViewById(R.id.items_items_button)
        outfitButton=view.findViewById(R.id.items_outfit_button)
        // Click Listeners
        uploadbutton.setOnClickListener {
            callbacks?.startPhotoFragment()
            // Go to photo upload layout
        }
        xButton.setOnClickListener {
            callbacks?.startFirstFragment()
            // Return to main layout
        }
        itemButton.setOnClickListener {
            //do nothing, you're already here
        }
        outfitButton.setOnClickListener {
            callbacks?.startOutfitFragment()
        }


        if(firstLoad == true) { // Ensures data is only pulled once(NO DUPLICATION!)

            firstLoad = false
            getData()

        }
        dummyData()
            val CollectionViewFragment = this
            // This binds the data from the database to the clothing_item.xml
            binding.collectionRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 4) // This produces a grid of clothing from the database.
                adapter = CollectionAdapter(collectionList)
            }

        return view
    } //End of onCreateView()


    private fun dummyData(){ // Creates dummy data images to be entered with the title and tag pulled from the database.
        for(position in collectionList.indices) {
            if (collectionList[position].title == "blue hat") {
                collectionList[position].imageC = R.drawable.boots
                counter++
            } else if (collectionList[position].title == "Graphic Tee") {
                collectionList[position].imageC = R.drawable.graphictee
                counter++
            } else if (collectionList[position].title == "Boots") {
                collectionList[position].imageC = R.drawable.boots
                counter++
            } else if (collectionList[position].title == "Bro Tank") {
                collectionList[position].imageC = R.drawable.brotank
                counter++
            } else if (collectionList[position].title == "Bucket Hat") {
                collectionList[position].imageC = R.drawable.buckethat
                counter++
            } else if (collectionList[position].title == "Cheese Head") {
                collectionList[position].imageC = R.drawable.cheesehead
                counter++
            } else if (collectionList[position].title == "Converse") {
                collectionList[position].imageC = R.drawable.converse
                counter++
            } else if (collectionList[position].title == "Crocs") {
                collectionList[position].imageC = R.drawable.crocs
                counter++
            } else if (collectionList[position].title == "Dad Sandals") {
                collectionList[position].imageC = R.drawable.dadsandals
                counter++
            } else if (collectionList[position].title == "Dress Shirt") {
                collectionList[position].imageC = R.drawable.dressshirt
                counter++
            } else if (collectionList[position].title == "Hammer Pants") {
                collectionList[position].imageC = R.drawable.hammerpants
                counter++
            } else if (collectionList[position].title == "Helmet") {
                collectionList[position].imageC = R.drawable.helmet
                counter++
            } else if (collectionList[position].title == "High Heels") {
                collectionList[position].imageC = R.drawable.highheels
                counter++
            } else if (collectionList[position].title == "Jorts") {
                collectionList[position].imageC = R.drawable.jorts
                counter++
            } else if (collectionList[position].title == "Khakis") {
                collectionList[position].imageC = R.drawable.khakis
                counter++
            } else if (collectionList[position].title == "New Balance") {
                collectionList[position].imageC = R.drawable.newb
                counter++
            } else if (collectionList[position].title == "Pirate Shirt") {
                collectionList[position].imageC = R.drawable.pirate
                counter++
            } else if (collectionList[position].title == "Pajama Pants") {
                collectionList[position].imageC = R.drawable.pjpants
                counter++
            } else if (collectionList[position].title == "Red Shirt") {
                collectionList[position].imageC = R.drawable.redshirt
                counter++
            } else if (collectionList[position].title == "Sweatshirt") {
                collectionList[position].imageC = R.drawable.sweatshirt
            } else if (collectionList[position].title == "Tanktop") {
                collectionList[position].imageC = R.drawable.tanktop
            } else if (collectionList[position].title == "Twins Baseball Hat") {
                collectionList[position].imageC = R.drawable.twinshat
            } else if (collectionList[position].title == "Viking") {
                collectionList[position].imageC = R.drawable.viking
            } else if (collectionList[position].title == "White Jeans") {
                collectionList[position].imageC = R.drawable.whitejeans
            } else if (collectionList[position].title == "Winter Hat") {
                collectionList[position].imageC = R.drawable.winterhat
            } else if (collectionList[position].title == "Louis shirt") {
                collectionList[position].imageC = R.drawable.louisshirt
            } else if (collectionList[position].title == "Sandy pants") {
                collectionList[position].imageC = R.drawable.sandypants
            } else if (collectionList[position].title == "Blue hat") {
                collectionList[position].imageC = R.drawable.bluehat
            }else {
                collectionList[position].imageC = R.drawable.buckethat
            }
        }
    }
    //This function pulls data from the database for the CollectionView.
    private fun getData(){
        database2 = FirebaseFirestore.getInstance()
        database2.collection("sampleData").addSnapshotListener(object:
            EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?){
                if (error != null){
                    Log.e("Firebase Error", error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        collectionList.add(dc.document.toObject(Collection::class.java))
                    }
                }
            }
        })

    } //End of getData()

    //add a listener to the Edit Text View Widget we just created
    override fun onStart() {
        super.onStart()
        //left blank
    }
    //Callback Code
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

    //companion object {
    //    fun newInstance(): CollectionViewFragment {
    //        return CollectionViewFragment()
     //   }

    //}
}


