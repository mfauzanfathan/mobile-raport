package com.example.sman108jakarta

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBGuruHandler (context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "db_guru"
        private val TABLE_GURU = "Guru"
        private val KEY_NIP = "nip"
        private val KEY_NAMA = "nama"
        private val KEY_MATPEL = "matpel"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_GURU_TABLE = ("CREATE TABLE " + TABLE_GURU + "("
                + KEY_NIP + " TEXT PRIMARY KEY," + KEY_NAMA + " TEXT,"
                + KEY_MATPEL + " TEXT" + ")")
        db?.execSQL(CREATE_GURU_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_GURU)
        onCreate(db)
    }

    fun addGuru(Guru: GuruModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIP, Guru.nip)
        contentValues.put(KEY_NAMA, Guru.nama)
        contentValues.put(KEY_MATPEL, Guru.matpel)

        val success = db.insert(TABLE_GURU, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewGuru(): List<GuruModel> {
        val GuruList: ArrayList<GuruModel> = ArrayList<GuruModel>()
        val selectQuery = "SELECT * FROM $TABLE_GURU"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var nip: String
        var nama: String
        var matpel: String
        if (cursor.moveToFirst()) {
            do {
                nip = cursor.getString(cursor.getColumnIndex("nip"))
                nama = cursor.getString(cursor.getColumnIndex("nama"))
                matpel = cursor.getString(cursor.getColumnIndex("matpel"))
                val Guru = GuruModel(nip = nip, nama = nama, matpel = matpel)
                GuruList.add(Guru)
            } while (cursor.moveToNext())
        }
        return GuruList
    }

    fun updateGuru(Guru: GuruModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIP, Guru.nip)
        contentValues.put(KEY_NAMA, Guru.nama)
        contentValues.put(KEY_MATPEL, Guru.matpel)

        val success = db.update(TABLE_GURU, contentValues, "nip=" + Guru.nip, null)
        db.close()
        return success
    }

    fun deleteGuru(Guru: GuruModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIP, Guru.nip)

        val success = db.delete(TABLE_GURU, "nip=" + Guru.nip, null)
        db.close()
        return success
    }
}