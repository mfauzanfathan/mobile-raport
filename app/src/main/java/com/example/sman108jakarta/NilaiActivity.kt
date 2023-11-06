package com.example.sman108jakarta

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_nilai.*
import kotlinx.android.synthetic.main.activity_siswa.*
import kotlinx.android.synthetic.main.activity_siswa.etNis
import kotlinx.android.synthetic.main.activity_siswa.listView

class NilaiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nilai)

        val databaseHandler: DBNilaiHandler = DBNilaiHandler(this)

        viewRecord(listView)
    }
    fun saveRecord(view: View){
        val nis = etNis.text.toString()
        val uts = etUts.text.toString()
        val uas = etUas.text.toString()
        val databaseHandler: DBNilaiHandler= DBNilaiHandler(this)

        if(nis.trim()!=="" && uts.trim()!=""  && uas.trim()!=""){
            val status = databaseHandler.addNilai(NilaiModel(nis, uts, uas))
            if (status > -1){
                Toast.makeText(applicationContext,"Data Disimpan", Toast.LENGTH_LONG).show()

                etNis.text.clear()
                etUts.text.clear()
                etUas.text.clear()
            }
        }else {
            Toast.makeText(applicationContext,"Data tidak boleh Kosong", Toast.LENGTH_LONG).show()
        }
    }
    fun viewRecord(view: View){
        val databaseHandler: DBNilaiHandler= DBNilaiHandler(this)

        val nilai: List<NilaiModel> = databaseHandler.viewNilai()
        val nilaiArrayNis = Array<String>(nilai.size){"0"}
        val nilaiArrayUts = Array<String>(nilai.size){"null"}
        val nilaiArrayUas = Array<String>(nilai.size){"null"}
        var index = 0
        for (e in nilai){
            nilaiArrayNis[index] = e.nis
            nilaiArrayUts[index] = e.uts
            nilaiArrayUas[index] = e.uas
            index++
        }

        val Nilaidapter = NilaiAdapter(this,nilaiArrayNis,nilaiArrayUts,nilaiArrayUas)
        listView.adapter = Nilaidapter
    }
    fun updateRecord(view: View){
        val dialogBuilder= AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.nilai_update_dialog, null)
        dialogBuilder.setView(dialogView)

        val vNis = dialogView.findViewById(R.id.updateNis) as EditText
        val vUts = dialogView.findViewById(R.id.updateUts) as EditText
        val vUas = dialogView.findViewById(R.id.updateUas) as EditText

        dialogBuilder.setTitle("Update Data")
        dialogBuilder.setMessage("Inputkan Data yang akan di-update")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateNis = vNis.text.toString()
            val updateUts = vUts.text.toString()
            val updateUas = vUas.text.toString()

            val databaseHandler: DBNilaiHandler= DBNilaiHandler(this)
            if (updateNis.trim()!="" && updateUts.trim()!="" && updateUas.trim()!=""){

                val status = databaseHandler.updateNilai(NilaiModel(updateNis, updateUts, updateUas))
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
        val dialogView = inflater.inflate(R.layout.nilai_delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val vNis = dialogView.findViewById(R.id.deleteNis) as EditText
        dialogBuilder.setTitle("Hapus Data")
        dialogBuilder.setMessage("Input NIS")
        dialogBuilder.setPositiveButton("Hapus", DialogInterface.OnClickListener { _, _ ->

            val deleteId = vNis.text.toString()

            val databaseHandler: DBNilaiHandler= DBNilaiHandler(this)
            if (deleteId.trim()!=""){

                val status = databaseHandler.deleteNilai(NilaiModel(vNis.text.toString(),"",""))
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