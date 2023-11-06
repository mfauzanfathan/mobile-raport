package com.example.sman108jakarta

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBSiswaHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "db_siswa"
        private val TABLE_SISWA = "Siswa"
        private val KEY_NIS = "nis"
        private val KEY_NAMA = "nama"
        private val KEY_ALAMAT = "alamat"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_SISWA_TABLE = ("CREATE TABLE " + TABLE_SISWA + "("
                + KEY_NIS + " TEXT PRIMARY KEY," + KEY_NAMA + " TEXT,"
                + KEY_ALAMAT + " TEXT" + ")")
        db?.execSQL(CREATE_SISWA_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_SISWA)
        onCreate(db)
    }

    fun addSiswa(Siswa: SiswaModel): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIS, Siswa.nis)
        contentValues.put(KEY_NAMA, Siswa.nama)
        contentValues.put(KEY_ALAMAT, Siswa.alamat)

        val success = db.insert(TABLE_SISWA, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewSiswa(): List<SiswaModel> {
        val SiswaList: ArrayList<SiswaModel> = ArrayList<SiswaModel>()
        val selectQuery = "SELECT * FROM $TABLE_SISWA"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var nis: String
        var nama: String
        var alamat: String
        if (cursor.moveToFirst()) {
            do {
                nis= cursor.getString(cursor.getColumnIndex("nis"))
                nama = cursor.getString(cursor.getColumnIndex("nama"))
                alamat = cursor.getString(cursor.getColumnIndex("alamat"))
                val Siswa = SiswaModel(nis = nis, nama = nama, alamat = alamat )
                SiswaList.add(Siswa)
            } while (cursor.moveToNext())
        }
        return SiswaList
    }
    fun updateSiswa(Siswa: SiswaModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIS, Siswa.nis)
        contentValues.put(KEY_NAMA, Siswa.nama)
        contentValues.put(KEY_ALAMAT, Siswa.alamat)

        val success = db.update(TABLE_SISWA, contentValues, "nis="+Siswa.nis, null)
        db.close()
        return success
    }

    fun deleteSiswa(Siswa: SiswaModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIS, Siswa.nis)

        val success = db.delete(TABLE_SISWA,"nis="+Siswa.nis,null)
        db.close()
        return success
    }
}