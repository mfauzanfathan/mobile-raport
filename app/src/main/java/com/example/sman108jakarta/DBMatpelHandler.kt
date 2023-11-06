package com.example.sman108jakarta

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBMatpelHandler (context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "db_matpel"
        private val TABLE_MATPEL = "Matpel"
        private val KEY_KODE = "kode"
        private val KEY_NAMA = "nama"
        private val KEY_GURU = "guru"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_MATPEL_TABLE = ("CREATE TABLE " + TABLE_MATPEL + "("
                + KEY_KODE + " TEXT PRIMARY KEY," + KEY_NAMA + " TEXT,"
                + KEY_GURU + " TEXT" + ")")
        db?.execSQL(CREATE_MATPEL_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_MATPEL)
        onCreate(db)
    }

    fun addMatpel(Matpel: MatpelModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_KODE, Matpel.kode)
        contentValues.put(KEY_NAMA, Matpel.nama)
        contentValues.put(KEY_GURU, Matpel.guru)

        val success = db.insert(TABLE_MATPEL, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewMatpel(): List<MatpelModel> {
        val MatpelList: ArrayList<MatpelModel> = ArrayList<MatpelModel>()
        val selectQuery = "SELECT * FROM $TABLE_MATPEL"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var kode: String
        var nama: String
        var guru: String
        if (cursor.moveToFirst()) {
            do {
                kode= cursor.getString(cursor.getColumnIndex("kode"))
                nama = cursor.getString(cursor.getColumnIndex("nama"))
                guru = cursor.getString(cursor.getColumnIndex("guru"))
                val Matpel = MatpelModel(kode = kode, nama = nama, guru = guru )
                MatpelList.add(Matpel)
            } while (cursor.moveToNext())
        }
        return MatpelList
    }
    fun updateMatpel(Matpel: MatpelModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_KODE, Matpel.kode)
        contentValues.put(KEY_NAMA, Matpel.nama)
        contentValues.put(KEY_GURU, Matpel.guru)

        val success = db.update(TABLE_MATPEL, contentValues, "kode="+Matpel.kode, null)
        db.close()
        return success
    }

    fun deleteMatpel(Matpel: MatpelModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_KODE, Matpel.kode)

        val success = db.delete(TABLE_MATPEL,"kode="+Matpel.kode,null)
        db.close()
        return success
    }
}