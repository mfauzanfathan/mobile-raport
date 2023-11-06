package com.example.sman108jakarta

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_siswa.*

class SiswaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_siswa)


        val databaseHandler: DBSiswaHandler = DBSiswaHandler(this)

        viewRecord(listView)
    }
    fun saveRecord(view: View){
        val nis = etNis.text.toString()
        val nama = etNama.text.toString()
        val alamat = etAlamat.text.toString()
        val databaseHandler: DBSiswaHandler= DBSiswaHandler(this)

        if(nis.trim()!=="" && nama.trim()!=""  && alamat.trim()!=""){
            val status = databaseHandler.addSiswa(SiswaModel(nis, nama, alamat))
            if (status > -1){
                Toast.makeText(applicationContext,"Data Disimpan", Toast.LENGTH_LONG).show()

                etNis.text.clear()
                etNama.text.clear()
                etAlamat.text.clear()
            }
        }else {
            Toast.makeText(applicationContext,"Data tidak boleh Kosong", Toast.LENGTH_LONG).show()
        }
    }

    fun viewRecord(view: View){
        val databaseHandler: DBSiswaHandler= DBSiswaHandler(this)

        val siswa: List<SiswaModel> = databaseHandler.viewSiswa()
        val siswaArrayNis = Array<String>(siswa.size){"0"}
        val siswaArrayNama = Array<String>(siswa.size){"null"}
        val siswaArrayAlamat = Array<String>(siswa.size){"null"}
        var index = 0
        for (e in siswa){
            siswaArrayNis[index] = e.nis
            siswaArrayNama[index] = e.nama
            siswaArrayAlamat[index] = e.alamat
            index++
        }

        val SiswaAdapter = SiswaAdapter(this,siswaArrayNis,siswaArrayNama,siswaArrayAlamat)
        listView.adapter = SiswaAdapter
    }

    fun updateRecord(view: View){
        val dialogBuilder= AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.siswa_update_dialog, null)
        dialogBuilder.setView(dialogView)

        val vNis = dialogView.findViewById(R.id.updateNis) as EditText
        val vNama = dialogView.findViewById(R.id.updateNama) as EditText
        val vAlamat = dialogView.findViewById(R.id.updateAlamat) as EditText

        dialogBuilder.setTitle("Update Data")
        dialogBuilder.setMessage("Inputkan Data yang akan di-update")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateNis = vNis.text.toString()
            val updateNama = vNama.text.toString()
            val updateAlamat = vAlamat.text.toString()

            val databaseHandler: DBSiswaHandler= DBSiswaHandler(this)
            if (updateNis.trim()!="" && updateNama.trim()!="" && updateAlamat.trim()!=""){

                val status = databaseHandler.updateSiswa(SiswaModel(updateNis, updateNama, updateAlamat))
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
        val dialogView = inflater.inflate(R.layout.siswa_delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val vNis = dialogView.findViewById(R.id.deleteNis) as EditText
        dialogBuilder.setTitle("Hapus Data")
        dialogBuilder.setMessage("Input NIS")
        dialogBuilder.setPositiveButton("Hapus", DialogInterface.OnClickListener { _, _ ->

            val deleteId = vNis.text.toString()

            val databaseHandler: DBSiswaHandler= DBSiswaHandler(this)
            if (deleteId.trim()!=""){

                val status = databaseHandler.deleteSiswa(SiswaModel(vNis.text.toString(),"",""))
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