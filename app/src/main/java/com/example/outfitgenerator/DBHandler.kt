package com.example.outfitgenerator

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper as SQLiteOpenHelper
import android.content.Context
import android.net.Uri
import androidx.core.database.getIntOrNull
import androidx.fragment.app.FragmentActivity


private const val DATABASE_NAME = "OutfitGenDB"
private const val DATABASE_VERSION = 1

private const val TABLE_HATS = "Hats"
private const val HATS_ID_COL = "HatImageID"
private const val HATS_NAME_COL = "HatImageName"

private const val TABLE_SHIRTS = "Shirts"
private const val SHIRTS_ID_COL = "ShirtsImageID"
private const val SHIRTS_NAME_COL = "ShirtImageName"

private const val TABLE_PANTS = "Pants"
private const val PANTS_ID_COL = "PantsImageID"
private const val PANTS_NAME_COL = "PantsImageName"

private const val TABLE_SHOES = "Shoes"
private const val SHOES_ID_COL = "ShoesImageID"
private const val SHOES_NAME_COL = "ShoesImageName"


/*
val context = this (or applicationContext)
val db = DBHandler(context)

when you want to read data from the table, call db.readData()
when you wan to insert , call db.insertData(clothing)
*/

class DBHandler(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {  // ??
    override fun onCreate(db: SQLiteDatabase?) {

        val query1 =("CREATE TABLE " + TABLE_HATS + " ("
                + HATS_ID_COL + "INTEGER PRIMARY KEY, "
                + HATS_NAME_COL + "TEXT)")

        val query2 =("CREATE TABLE " + TABLE_SHIRTS + " ("
                + SHIRTS_ID_COL + "INTEGER PRIMARY KEY, "
                + SHIRTS_NAME_COL + "TEXT)")

        val query3 =("CREATE TABLE " + TABLE_PANTS + " ("
                + PANTS_ID_COL + "INTEGER PRIMARY KEY, "
                + PANTS_NAME_COL + "TEXT)")

        val query4 =("CREATE TABLE " + TABLE_SHOES + " ("
                + SHOES_ID_COL + "INTEGER PRIMARY KEY, "
                + SHOES_NAME_COL + "TEXT)")


        if (db != null) {
            db.execSQL(query1)
            db.execSQL(query2)
            db.execSQL(query3)
            db.execSQL(query4)
        }
    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_HATS")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_SHIRTS")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_PANTS")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_SHOES")
        }
        onCreate(db)
    }


    /*
    HAT DATA TABLE INSERT FUNCTION
    */
    fun insertHatData(clothing: Clothing): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(HATS_ID_COL, clothing.id)
        contentValues.put(HATS_NAME_COL, clothing.image_title)
        return db.insert(TABLE_HATS, null, contentValues)
    }


    /*
    SHIRT DATA TABLE INSERT FUNCTION
    */
    fun insertShirtData(clothing: Clothing): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SHIRTS_ID_COL, clothing.id)
        contentValues.put(SHIRTS_NAME_COL, clothing.image_title)
        return db.insert(TABLE_SHIRTS, null, contentValues)
    }


    /*
    PANTS DATA TABLE INSERT FUNCTION
    */
    fun insertPantsData(clothing: Clothing): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(PANTS_ID_COL, clothing.id)
        contentValues.put(PANTS_NAME_COL, clothing.image_title)
        return db.insert(TABLE_PANTS, null, contentValues)
    }


    /*
    SHOES DATA TABLE INSERT FUNCTION
    */
    fun insertShoesData(clothing: Clothing): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SHOES_ID_COL, clothing.id)
        contentValues.put(SHOES_NAME_COL, clothing.image_title)
        return db.insert(TABLE_SHOES, null, contentValues)
    }


    /*
    HAT DATA TABLE READ FUNCTION
    */
    @SuppressLint("Range")
    fun readHatData(): MutableList<Clothing>{
        val clothingList = mutableListOf<Clothing>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_HATS"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                val clothing = Clothing(Uri.EMPTY)  // ??
                clothing.id = result.getInt(result.getColumnIndex(HATS_ID_COL))
                clothing.image_title = result.getString(result.getColumnIndex(HATS_NAME_COL))
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
        val query = "SELECT * FROM $TABLE_SHIRTS"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                val clothing = Clothing(Uri.EMPTY)  // ??
                clothing.id = result.getInt(result.getColumnIndex(SHIRTS_ID_COL))
                clothing.image_title = result.getString(result.getColumnIndex(SHIRTS_NAME_COL))
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
        val query = "SELECT * FROM $TABLE_PANTS"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                val clothing = Clothing(Uri.EMPTY)  // ??
                clothing.id = result.getInt(result.getColumnIndex(PANTS_ID_COL))
                clothing.image_title = result.getString(result.getColumnIndex(PANTS_NAME_COL))
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
        val query = "SELECT * FROM $TABLE_SHOES"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do{
                val clothing = Clothing(Uri.EMPTY)  // ??
                clothing.id = result.getInt(result.getColumnIndex(SHOES_ID_COL))
                clothing.image_title = result.getString(result.getColumnIndex(SHOES_NAME_COL))
                clothingList.add(clothing)
            }
            while(result.moveToNext())
        }
        return clothingList
    }

}



