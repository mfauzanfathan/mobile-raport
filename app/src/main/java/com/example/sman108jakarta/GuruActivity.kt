package com.example.sman108jakarta

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_guru.*
import kotlinx.android.synthetic.main.activity_siswa.*
import kotlinx.android.synthetic.main.activity_siswa.etNama
import kotlinx.android.synthetic.main.activity_siswa.listView

class GuruActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guru)

        val databaseHandler: DBGuruHandler = DBGuruHandler(this)

        viewRecord(listView)

    }
    fun saveRecord(view: View){
        val nip = etNip.text.toString()
        val nama = etNama.text.toString()
        val matpel = etMatpel.text.toString()
        val databaseHandler: DBGuruHandler= DBGuruHandler(this)

        if(nip.trim()!=="" && nama.trim()!=""  && matpel.trim()!=""){
            val status = databaseHandler.addGuru(GuruModel(nip, nama, matpel))
            if (status > -1){
                Toast.makeText(applicationContext,"Data Disimpan", Toast.LENGTH_LONG).show()

                etNip.text.clear()
                etNama.text.clear()
                etMatpel.text.clear()
            }
        }else {
            Toast.makeText(applicationContext,"Data tidak boleh Kosong", Toast.LENGTH_LONG).show()
        }
    }
    fun viewRecord(view: View){
        val databaseHandler: DBGuruHandler= DBGuruHandler(this)

        val guru: List<GuruModel> = databaseHandler.viewGuru()
        val siswaArrayNip = Array<String>(guru.size){"0"}
        val siswaArrayNama = Array<String>(guru.size){"null"}
        val siswaArrayMatpel = Array<String>(guru.size){"null"}
        var index = 0
        for (e in guru){
            siswaArrayNip[index] = e.nip
            siswaArrayNama[index] = e.nama
            siswaArrayMatpel[index] = e.matpel
            index++
        }

        val GuruAdapter = GuruAdapter(this,siswaArrayNip,siswaArrayNama,siswaArrayMatpel)
        listView.adapter = GuruAdapter
    }
    fun updateRecord(view: View){
        val dialogBuilder= AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.guru_update_dialog, null)
        dialogBuilder.setView(dialogView)

        val vNip = dialogView.findViewById(R.id.updateNip) as EditText
        val vNama = dialogView.findViewById(R.id.updateNama) as EditText
        val vMatpel = dialogView.findViewById(R.id.updateMatpel) as EditText

        dialogBuilder.setTitle("Update Data")
        dialogBuilder.setMessage("Inputkan Data yang akan di-update")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateNip = vNip.text.toString()
            val updateNama = vNama.text.toString()
            val updateMatpel = vMatpel.text.toString()

            val databaseHandler: DBGuruHandler= DBGuruHandler(this)
            if (updateNip.trim()!="" && updateNama.trim()!="" && updateMatpel.trim()!=""){

                val status = databaseHandler.updateGuru(GuruModel(updateNip, updateNama, updateMatpel))
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
        val dialogView = inflater.inflate(R.layout.guru_delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val vNip = dialogView.findViewById(R.id.deleteNip) as EditText
        dialogBuilder.setTitle("Hapus Data")
        dialogBuilder.setMessage("Input NIP")
        dialogBuilder.setPositiveButton("Hapus", DialogInterface.OnClickListener { _, _ ->

            val deleteId = vNip.text.toString()

            val databaseHandler: DBGuruHandler= DBGuruHandler(this)
            if (deleteId.trim()!=""){

                val status = databaseHandler.deleteGuru(GuruModel(vNip.text.toString(),"",""))
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