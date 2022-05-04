package com.example.outfitgenerator

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper as SQLiteOpenHelper
import android.content.Context
import android.net.Uri
import android.provider.BaseColumns
import android.provider.ContactsContract
import androidx.core.database.getIntOrNull
import androidx.fragment.app.FragmentActivity

object DatabaseContract{
    object Hats: BaseColumns{
        const val TABLE_HATS = "Hats"
        const val HATS_ID_COL = "HatImageID"
        const val HATS_NAME_COL = "HatImageName"
    }
    object Shirts: BaseColumns {
        const val TABLE_SHIRTS = "Shirts"
        const val SHIRTS_ID_COL = "ShirtsImageID"
        const val SHIRTS_NAME_COL = "ShirtImageName"
    }
    object Pants: BaseColumns{
        const val TABLE_PANTS = "Pants"
        const val PANTS_ID_COL = "PantsImageID"
        const val PANTS_NAME_COL = "PantsImageName"
    }
    object Shoes: BaseColumns{
        const val TABLE_SHOES = "Shoes"
        const val SHOES_ID_COL = "ShoesImageID"
        const val SHOES_NAME_COL = "ShoesImageName"
    }
}

private const val CREATE_HATS ="CREATE TABLE ${DatabaseContract.Hats.TABLE_HATS}("+
        "${DatabaseContract.Hats.HATS_ID_COL} INTEGER PRIMARY KEY, " +
        "${DatabaseContract.Hats.HATS_NAME_COL} TEXT)"

private const val CREATE_SHIRTS ="CREATE TABLE ${DatabaseContract.Shirts.TABLE_SHIRTS}("+
        "${DatabaseContract.Shirts.SHIRTS_ID_COL} INTEGER PRIMARY KEY, " +
        "${DatabaseContract.Shirts.SHIRTS_NAME_COL} TEXT)"

private const val CREATE_PANTS ="CREATE TABLE ${DatabaseContract.Pants.TABLE_PANTS}("+
        "${DatabaseContract.Pants.PANTS_ID_COL} INTEGER PRIMARY KEY, " +
        "${DatabaseContract.Pants.PANTS_NAME_COL} TEXT)"

private const val CREATE_SHOES ="CREATE TABLE ${DatabaseContract.Shoes.TABLE_SHOES}("+
        "${DatabaseContract.Shoes.SHOES_ID_COL} INTEGER PRIMARY KEY, " +
        "${DatabaseContract.Shoes.SHOES_NAME_COL} TEXT)"

private const val DELETE_HATS="DROP TABLE IF EXISTS ${DatabaseContract.Hats.TABLE_HATS}"
private const val DELETE_SHIRTS="DROP TABLE IF EXISTS ${DatabaseContract.Shirts.TABLE_SHIRTS}"
private const val DELETE_PANTS="DROP TABLE IF EXISTS ${DatabaseContract.Pants.TABLE_PANTS}"
private const val DELETE_SHOES="DROP TABLE IF EXISTS ${DatabaseContract.Shoes.TABLE_SHOES}"


/*
val context = this (or applicationContext)
val db = DBHandler(context)

when you want to read data from the table, call db.readData()
when you wan to insert , call db.insertData(clothing)
*/

class DBHandler(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {  // ??
    override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_HATS)
            db.execSQL(CREATE_SHIRTS)
            db.execSQL(CREATE_PANTS)
            db.execSQL(CREATE_SHOES)

    }


    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(DELETE_HATS)
        db.execSQL(DELETE_SHIRTS)
        db.execSQL(DELETE_PANTS)
        db.execSQL(DELETE_SHOES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object{
        const val DATABASE_VERSION=1
        const val DATABASE_NAME="OutfitGenDB"
    }


    /*
    HAT DATA TABLE INSERT FUNCTION
    */
    fun insertHatData(clothing: Clothing): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply{
        put(DatabaseContract.Hats.HATS_ID_COL, clothing.id)
        put(DatabaseContract.Hats.HATS_NAME_COL, clothing.image_title)}
        return db.insert(DatabaseContract.Hats.TABLE_HATS, null, contentValues)
    }


    /*
    SHIRT DATA TABLE INSERT FUNCTION
    */
    fun insertShirtData(clothing: Clothing): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply{
        put(DatabaseContract.Shirts.SHIRTS_ID_COL, clothing.id)
        put(DatabaseContract.Shirts.SHIRTS_NAME_COL, clothing.image_title)}
        return db.insert(DatabaseContract.Shirts.TABLE_SHIRTS, null, contentValues)


    }


    /*
    PANTS DATA TABLE INSERT FUNCTION
    */
    fun insertPantsData(clothing: Clothing): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply{
        put(DatabaseContract.Pants.PANTS_ID_COL, clothing.id)
        put(DatabaseContract.Pants.PANTS_NAME_COL, clothing.image_title)}
        return db.insert(DatabaseContract.Pants.TABLE_PANTS, null, contentValues)
    }


    /*
    SHOES DATA TABLE INSERT FUNCTION
    */
    fun insertShoesData(clothing: Clothing): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply{
        put(DatabaseContract.Shoes.SHOES_ID_COL, clothing.id)
        put(DatabaseContract.Shoes.SHOES_NAME_COL, clothing.image_title)}
        return db.insert(DatabaseContract.Shoes.TABLE_SHOES, null, contentValues)
    }


    /*
    HAT DATA TABLE READ FUNCTION
    */
    @SuppressLint("Range")
    fun readHatData(): MutableList<Clothing>{
        val clothingList = mutableListOf<Clothing>()
        val db = this.readableDatabase
        val query = "SELECT * FROM ${DatabaseContract.Hats.TABLE_HATS}"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                var id = result.getString(result.getColumnIndex(DatabaseContract.Hats.HATS_ID_COL))
                var image_title = result.getString(result.getColumnIndex(DatabaseContract.Hats.HATS_NAME_COL))
                var clothing: Clothing = Clothing(id, image_title)
                clothingList.add(clothing)
            }
            while(result.moveToNext())
        }
        return clothingList
    }


    /*
    SHIRT DATA TABLE READ FUNCTION
    */
    @SuppressLint("Range")
    fun readShirtData(): MutableList<Clothing>{
        val clothingList = mutableListOf<Clothing>()
        val db = this.readableDatabase
        val query = "SELECT * FROM ${DatabaseContract.Shirts.TABLE_SHIRTS}"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                var id = result.getString(result.getColumnIndex(DatabaseContract.Shirts.SHIRTS_ID_COL))
                var image_title = result.getString(result.getColumnIndex(DatabaseContract.Shirts.SHIRTS_NAME_COL))
                var clothing: Clothing = Clothing(id, image_title)
                clothingList.add(clothing)
            }
            while(result.moveToNext())
        }
        return clothingList
    }


    /*
    PANTS DATA TABLE READ FUNCTION
    */
    @SuppressLint("Range")
    fun readPantsData(): MutableList<Clothing>{
        val clothingList = mutableListOf<Clothing>()
        val db = this.readableDatabase
        val query = "SELECT * FROM ${DatabaseContract.Pants.TABLE_PANTS}"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                var id = result.getString(result.getColumnIndex(DatabaseContract.Pants.PANTS_ID_COL))
                var image_title = result.getString(result.getColumnIndex(DatabaseContract.Pants.PANTS_NAME_COL))
                var clothing: Clothing = Clothing(id, image_title)
                clothingList.add(clothing)
            }
            while(result.moveToNext())
        }
        return clothingList
    }


    /*
    SHOES DATA TABLE READ FUNCTION
    */
    @SuppressLint("Range")
    fun readShoesData(): MutableList<Clothing>{
        val clothingList = mutableListOf<Clothing>()
        val db = this.readableDatabase
        val query = "SELECT * FROM ${DatabaseContract.Shoes.TABLE_SHOES}"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                var id = result.getString(result.getColumnIndex(DatabaseContract.Shoes.SHOES_ID_COL))
                var image_title = result.getString(result.getColumnIndex(DatabaseContract.Shoes.SHOES_NAME_COL))
                var clothing: Clothing = Clothing(id, image_title)
                clothingList.add(clothing)
            }
            while(result.moveToNext())
        }
        return clothingList
    }

}



