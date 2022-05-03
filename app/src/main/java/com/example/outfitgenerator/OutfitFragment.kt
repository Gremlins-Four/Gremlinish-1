package com.example.outfitgenerator

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.FragmentCollectionviewBinding
import com.example.outfitgenerator.databinding.FragmentOutfitBinding
import com.google.firebase.firestore.*
import kotlin.collections.Collection

class OutfitFragment: Fragment() {

    private lateinit var outfitButton: Button
    private lateinit var itemsButton: Button
    private lateinit var xButton: ImageButton
    private lateinit var binding: FragmentOutfitBinding
    private lateinit var outfitRecyclerView: RecyclerView
    private lateinit var database2: FirebaseFirestore

    interface Callbacks {
        fun startPhotoFragment()
        fun startCollectionViewFragment()
        fun startFirstFragment()
    }

    private var callbacks: Callbacks? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOutfitBinding.inflate(layoutInflater)
        val view = binding.root
        outfitButton=view.findViewById(R.id.outfit_outfits_button)
        itemsButton=view.findViewById(R.id.outfit_items_button)
        xButton=view.findViewById(R.id.outfit_x)
        outfitRecyclerView=view.findViewById((R.id.outfitRecyclerView))

        outfitButton.setOnClickListener{
           //do nothing, you're already here
        }

        itemsButton.setOnClickListener {
            callbacks?.startCollectionViewFragment()
        }

        xButton.setOnClickListener{
            callbacks?.startFirstFragment()
        }
        getData()
        val OutfitFragment = this
        binding.outfitRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = OutfitAdapter(savedOutfitList)
        }

        return view

    }
    //This function pulls data from the database for the CollectionView.
    private fun getData(){
        database2 = FirebaseFirestore.getInstance()
        database2.collection("sampleData").addSnapshotListener(object:
            EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?){
                if (error != null){
                    Log.e("Firebase Error", error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        savedOutfitList.add(dc.document.toObject(SavedOutfit::class.java))
                    }
                }
            }
        })

    } //End of getData()

    override fun onStart() {
        super.onStart()
        //left blank
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}
