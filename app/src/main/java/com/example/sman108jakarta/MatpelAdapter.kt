package com.example.sman108jakarta

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MatpelAdapter (private val context: Activity, private val kode: Array<String>, private val nama:
Array<String>, private val guru: Array<String>)
    : ArrayAdapter<String>(context, R.layout.matpel_custom_list, nama) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.matpel_custom_list, null, true)

        val vKode = rowView.findViewById(R.id.tvKode) as TextView
        val vNama = rowView.findViewById(R.id.tvNama) as TextView
        val vGuru = rowView.findViewById(R.id.tvGuru) as TextView

        vKode.text = "Kode: ${kode[position]}"
        vNama.text = "Nama: ${nama[position]}"
        vGuru.text = "Guru: ${guru[position]}"
        return rowView
    }
}