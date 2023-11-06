package com.example.sman108jakarta

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_matpel.*
import kotlinx.android.synthetic.main.activity_siswa.*
import kotlinx.android.synthetic.main.activity_siswa.etNama
import kotlinx.android.synthetic.main.activity_siswa.listView

class MatpelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matpel)

        val databaseHandler: DBMatpelHandler = DBMatpelHandler(this)

        viewRecord(listView)

    }
    fun saveRecord(view: View){
        val kode = etKode.text.toString()
        val nama = etNama.text.toString()
        val guru = etGuru.text.toString()
        val databaseHandler: DBMatpelHandler= DBMatpelHandler(this)

        if(kode.trim()!=="" && nama.trim()!=""  && guru.trim()!=""){
            val status = databaseHandler.addMatpel(MatpelModel(kode, nama, guru))
            if (status > -1){
                Toast.makeText(applicationContext,"Data Disimpan", Toast.LENGTH_LONG).show()

                etKode.text.clear()
                etNama.text.clear()
                etGuru.text.clear()
            }
        }else {
            Toast.makeText(applicationContext,"Data tidak boleh Kosong", Toast.LENGTH_LONG).show()
        }

    }
    fun viewRecord(view: View){
        val databaseHandler: DBMatpelHandler= DBMatpelHandler(this)

        val matpel: List<MatpelModel> = databaseHandler.viewMatpel()
        val matpelArrayKode = Array<String>(matpel.size){"0"}
        val matpelArrayNama = Array<String>(matpel.size){"null"}
        val matpelArrayGuru = Array<String>(matpel.size){"null"}
        var index = 0
        for (e in matpel){
            matpelArrayKode[index] = e.kode
            matpelArrayNama[index] = e.nama
            matpelArrayGuru[index] = e.guru
            index++
        }

        val MatpelAdapter = MatpelAdapter(this,matpelArrayKode,matpelArrayNama,matpelArrayGuru)
        listView.adapter = MatpelAdapter
    }
    fun updateRecord(view: View){
        val dialogBuilder= AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.matpel_update_dialog, null)
        dialogBuilder.setView(dialogView)

        val vKode = dialogView.findViewById(R.id.updateKode) as EditText
        val vNama = dialogView.findViewById(R.id.updateNama) as EditText
        val vGuru = dialogView.findViewById(R.id.updateGuru) as EditText

        dialogBuilder.setTitle("Update Data")
        dialogBuilder.setMessage("Inputkan Data yang akan di-update")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateKode = vKode.text.toString()
            val updateNama = vNama.text.toString()
            val updateGuru = vGuru.text.toString()

            val databaseHandler: DBMatpelHandler= DBMatpelHandler(this)
            if (updateKode.trim()!="" && updateNama.trim()!="" && updateGuru.trim()!=""){

                val status = databaseHandler.updateMatpel(MatpelModel(updateKode, updateNama, updateGuru))
                if (status > -1){
                    Toast.makeText(applicationContext, "Data di-update", Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(applicationContext,"Data tidak boleh kosong", Toast.LENGTH_LONG).show()
            }
            viewRecord(view)
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

        })
        val b = dialogBuilder.create()
        b.show()
    }
    fun deleteRecord(view: View){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.matpel_delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val vKode = dialogView.findViewById(R.id.deleteKode) as EditText
        dialogBuilder.setTitle("Hapus Data")
        dialogBuilder.setMessage("Input Kode")
        dialogBuilder.setPositiveButton("Hapus", DialogInterface.OnClickListener { _, _ ->

            val deleteId = vKode.text.toString()

            val databaseHandler: DBMatpelHandler= DBMatpelHandler(this)
            if (deleteId.trim()!=""){

                val status = databaseHandler.deleteMatpel(MatpelModel(vKode.text.toString(),"",""))
                if (status > -1){
                    Toast.makeText(applicationContext, "Data di-hapus", Toast.LENGTH_LONG).show()
                }
            }else {
                Toast.makeText(applicationContext, "Data tidak boleh kosong", Toast.LENGTH_LONG).show()
            }
            viewRecord(view)
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->

        })
        val b = dialogBuilder.create()
        b.show()
    }
}