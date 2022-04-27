package com.example.outfitgenerator

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
import com.example.outfitgenerator.databinding.ActivityMainBinding
import com.example.outfitgenerator.databinding.FragmentCollectionviewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener

private const val TAG = "CollectionViewFragment"

private lateinit var uploadbutton: FloatingActionButton

class CollectionViewFragment: Fragment() {
    private lateinit var bind: ActivityMainBinding
    val database = Firebase.firestore
    private lateinit var collectionRecyclerView: RecyclerView
    private var adapter: CollectionAdapter? = null
    //private lateinit var dataRef: DatabaseReference
    //private lateinit var clothingArray: ArrayList<Collection>
    private lateinit var xButton: ImageButton
    private lateinit var itemButton: Button
    private lateinit var outfitButton: Button
    private lateinit var binding: FragmentCollectionviewBinding
    private var firstLoad = true




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
    }
    //End of Callbacks Code


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCollectionviewBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        xButton = view.findViewById(R.id.xbutton)
        uploadbutton = view.findViewById(R.id.upload_button)
        itemButton=view.findViewById(R.id.items_items_button)
        outfitButton=view.findViewById(R.id.items_outfit_button)

        uploadbutton.setOnClickListener {
            callbacks?.startPhotoFragment()
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

        /*
        if(firstLoad) {
            popCollection()
            firstLoad = false
        }
        */

        val CollectionViewFragment = this
        binding.collectionRecyclerView.apply{
            layoutManager = GridLayoutManager(context, 3)
            adapter = CollectionAdapter(collectionList)
        }
        //collectionRecyclerView =
         //   view.findViewById(R.id.collection_recycler_view) as RecyclerView
        //collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        //collectionRecyclerView.adapter = CollectionAdapter(collectionList)


        //updateUI()



        return view

    }

        /*
    private fun popCollection(){
        val item1 = Collection(4, "Blue Shirt", "Hat")
        collectionList.add(item1)
    }
    */

    //private fun updateUI() {
     //   val clothingItems = collectionViewModel.clothing
       // adapter = CollectionAdapter(clothingItems)
    //    collectionRecyclerView.adapter = adapter
    //}



    //add a listener to the Edit Text View Widget we just created
    override fun onStart() {
        super.onStart()
        //left blank
    }

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
