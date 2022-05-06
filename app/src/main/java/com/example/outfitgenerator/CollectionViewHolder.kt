package com.example.outfitgenerator

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.outfitgenerator.databinding.ClothingItemBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException


class CollectionViewHolder(private val clothingItemBinding: ClothingItemBinding): RecyclerView.ViewHolder(clothingItemBinding.root){
    fun bindCollection(collection: Collection){
        // This binds the data to the clothing_item.xml
        clothingItemBinding.clothingImage.setImageBitmap(downloadPhoto(collection.title.toString()))
        clothingItemBinding.clothingTitle.text = collection.title
        clothingItemBinding.clothingTag.text = collection.tag
    }
    fun downloadPhoto(photoTitle: String): Bitmap? {

        var mStorageReference = FirebaseStorage.getInstance().reference.child("pictures/$photoTitle")
        var Bitmap: Bitmap? = null

        try {
            val localFile = File.createTempFile("newPhoto","jpg")
            mStorageReference.getFile(localFile)
                .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    Bitmap = bitmap
                })
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return Bitmap
    }

}
