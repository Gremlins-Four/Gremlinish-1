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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CollectionViewFragment"

class CollectionViewFragment: Fragment() {
    private lateinit var collectionRecyclerView: RecyclerView
    private var adapter: CollectionAdapter? = null


    private val collectionViewModel: CollectionViewModel by lazy {
        ViewModelProviders.of(this).get(CollectionViewModel::class.java)
    }

    private lateinit var xButton: Button

    /**
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun startFirstFragment()
    }

    private var callbacks: Callbacks? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "# of clothing items: ${collectionViewModel.clothing.size}")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_collectionview, container, false)
        xButton = view.findViewById(R.id.xbutton)

        xButton.setOnClickListener {
            callbacks?.startFirstFragment()
            // Return to main layout
        }

        collectionRecyclerView =
            view.findViewById(R.id.collection_recycler_view) as RecyclerView
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI()



        return view

    }

    private fun updateUI() {
        val clothingItems = collectionViewModel.clothing
        adapter = CollectionAdapter(clothingItems)
        collectionRecyclerView.adapter = adapter
    }


    private inner class CollectionHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        val clothingTitleTextView: TextView = itemView.findViewById(R.id.clothing_title)
        val clothingTagTextView: TextView = itemView.findViewById(R.id.clothing_tag)
        val clothingImageView: ImageView = itemView.findViewById(R.id.clothing_image)
    }

    private inner class CollectionAdapter(var clothing: List<Collection>)
        : RecyclerView.Adapter<CollectionHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : CollectionHolder {
            val view = layoutInflater.inflate(R.layout.clothing_item, parent, false)
            return CollectionHolder(view)
        }
        override fun getItemCount() = clothing.size
        override fun onBindViewHolder(holder: CollectionHolder, position: Int) {
            val clothing2 = clothing[position]
            holder.apply {
                //This function sets up the individual texts and image for each item to be displayed.
                clothingTitleTextView.text = "Clothing Title"
                //clothingTitleTextView.text = clothing2.piece
                if(clothing2.hat == "Hat"){
                    clothingTagTextView.text = "Hat"
                }
                else if(clothing2.shirt == "Shirt"){
                    clothingTagTextView.text = "Shirt"
                }
                else if(clothing2.pants == "Pants"){
                    clothingTagTextView.text = "Pants"
                }
                else{
                    clothingTagTextView.text = "Shoes"
                }


                //ADD IMAGEVIEW
            }
        }
    }


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
