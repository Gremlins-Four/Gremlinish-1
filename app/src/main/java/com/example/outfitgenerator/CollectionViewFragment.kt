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
class CollectionViewFragment: Fragment() {
    private lateinit var database2: FirebaseFirestore
    private lateinit var collectionRecyclerView: RecyclerView
    private lateinit var xButton: ImageButton
    private lateinit var itemButton: Button
    private lateinit var outfitButton: Button
    private lateinit var binding: FragmentCollectionviewBinding
    //Test function variable
    private var adapter: CollectionAdapter? = null

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
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

        // This produces a grid of clothing from the database.
        if(firstLoad == true) {
            firstLoad = false
            getData()

        }
            val CollectionViewFragment = this
            // This binds the data from the database to the clothing_item.xml
            binding.collectionRecyclerView.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = CollectionAdapter(collectionList)
            }

        return view
    } //End of onCreateView()

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
