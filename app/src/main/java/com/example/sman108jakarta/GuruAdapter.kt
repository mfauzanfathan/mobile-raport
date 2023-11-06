package com.example.sman108jakarta

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class GuruAdapter (private val context: Activity, private val nip: Array<String>, private val nama:
Array<String>, private val matpel: Array<String>)
    : ArrayAdapter<String>(context, R.layout.guru_custom_list, nama) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.guru_custom_list, null, true)

        val vNip = rowView.findViewById(R.id.tvNip) as TextView
        val vNama = rowView.findViewById(R.id.tvNama) as TextView
        val vMatpel = rowView.findViewById(R.id.tvMatpel) as TextView

        vNip.text = "NIP: ${nip[position]}"
        vNama.text = "Nama: ${nama[position]}"
        vMatpel.text = "Matpel: ${matpel[position]}"
        return rowView
    }
}