package com.example.sman108jakarta

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SiswaAdapter (private val context: Activity, private val nis: Array<String>, private val nama:
Array<String>, private val alamat: Array<String>)
    : ArrayAdapter<String>(context, R.layout.siswa_custom_list, nama) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.siswa_custom_list, null, true)

        val vNim = rowView.findViewById(R.id.tvNis) as TextView
        val vNama = rowView.findViewById(R.id.tvNama) as TextView
        val vAlamat = rowView.findViewById(R.id.tvAlamat) as TextView

        vNim.text = "NIS: ${nis[position]}"
        vNama.text = "Nama: ${nama[position]}"
        vAlamat.text = "Alamat: ${alamat[position]}"
        return rowView
    }
}