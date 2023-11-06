package com.example.sman108jakarta

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class NilaiAdapter (private val context: Activity, private val nis: Array<String>, private val uts:
Array<String>, private val uas: Array<String>)
    : ArrayAdapter<String>(context, R.layout.nilai_custom_list, nis) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.nilai_custom_list, null, true)

        val vNis = rowView.findViewById(R.id.tvNis) as TextView
        val vUts = rowView.findViewById(R.id.tvUts) as TextView
        val vUas = rowView.findViewById(R.id.tvUas) as TextView

        vNis.text = "NIS: ${nis[position]}"
        vUts.text = "UTS: ${uts[position]}"
        vUas.text = "UAS: ${uas[position]}"
        return rowView
    }
}