package com.example.todo.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class database(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION)
{
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "Todo_db"
        private val TABLE_NAME = "Todo_table"
        private val KEY_ID = "id"
        private val KEY_NAME = "data"
        private val KEY_STATUS = "status"

    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        var table =
            "CREATE TABLE $TABLE_NAME($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,$KEY_NAME TEXT,$KEY_STATUS TEXT)"
        db?.execSQL(table)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //method to insert data
    fun adddata(datas:datalist):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()


        contentValues.put(KEY_NAME, datas.data)
        contentValues.put(KEY_STATUS,"false")

        // Inserting Row
        val success = db.insert(TABLE_NAME, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection

        return success
    }
    //method to read data
    fun viewdata():List<datalist>
    {

        val data_List:ArrayList<datalist> = ArrayList<datalist>()
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try
        {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException)
        {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var dat_txt:String
        var id:Int
        var status:String
        if (cursor.moveToFirst())
        {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                dat_txt = cursor.getString(cursor.getColumnIndex("data"))


                status = cursor.getString(cursor.getColumnIndex(KEY_STATUS)).toString()

                val datas= datalist(id,dat_txt,status)
                data_List.add(datas)


            } while (cursor.moveToNext())
        }
        return data_List
    }

    fun deletedata(position : Int):Int
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, position) // EmpModelClass id
        // Deleting Row
        val success = db.delete(TABLE_NAME, KEY_ID + "=" + position, null)
        //2nd argument is String containing nullColumnHack

        // Closing database connection
        db.close()
        return success
    }

    fun updatedata(datas: datalist):Int
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_NAME,datas.data)
        contentValues.put(KEY_STATUS,datas.status)
        val success = db.update(TABLE_NAME, contentValues, KEY_ID +"="+datas.id,null)
        db.close()
        return success

    }
}

