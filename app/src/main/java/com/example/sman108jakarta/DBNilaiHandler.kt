package com.example.sman108jakarta

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBNilaiHandler (context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "db_nilai"
        private val TABLE_NILAI = "Nilai"
        private val KEY_NIS = "nis"
        private val KEY_UTS = "uts"
        private val KEY_UAS = "uas"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_NILAI_TABLE = ("CREATE TABLE " + TABLE_NILAI + "("
                + KEY_NIS + " TEXT PRIMARY KEY," + KEY_UTS + " TEXT,"
                + KEY_UAS + " TEXT" + ")")
        db?.execSQL(CREATE_NILAI_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NILAI)
        onCreate(db)
    }

    fun addNilai(Nilai: NilaiModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIS, Nilai.nis)
        contentValues.put(KEY_UTS, Nilai.uts)
        contentValues.put(KEY_UAS, Nilai.uas)

        val success = db.insert(TABLE_NILAI, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewNilai(): List<NilaiModel> {
        val NilaiList: ArrayList<NilaiModel> = ArrayList<NilaiModel>()
        val selectQuery = "SELECT * FROM $TABLE_NILAI"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var nis: String
        var uts: String
        var uas: String
        if (cursor.moveToFirst()) {
            do {
                nis = cursor.getString(cursor.getColumnIndex("nis"))
                uts = cursor.getString(cursor.getColumnIndex("uts"))
                uas = cursor.getString(cursor.getColumnIndex("uas"))
                val Nilai = NilaiModel(nis = nis, uts = uts, uas = uas)
                NilaiList.add(Nilai)
            } while (cursor.moveToNext())
        }
        return NilaiList
    }

    fun updateNilai(Nilai: NilaiModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIS, Nilai.nis)
        contentValues.put(KEY_UTS, Nilai.uts)
        contentValues.put(KEY_UAS, Nilai.uas)

        val success = db.update(TABLE_NILAI, contentValues, "nis=" + Nilai.nis, null)
        db.close()
        return success
    }

    fun deleteNilai(Nilai: NilaiModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NIS, Nilai.nis)

        val success = db.delete(TABLE_NILAI, "nis=" + Nilai.nis, null)
        db.close()
        return success
    }
}