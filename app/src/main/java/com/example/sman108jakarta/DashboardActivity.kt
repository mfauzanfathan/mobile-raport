package com.example.sman108jakarta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val btnSiswa = findViewById<Button>(R.id.btnSiswa)
        val btnGuru = findViewById<Button>(R.id.btnGuru)
        val btnMatpel = findViewById<Button>(R.id.btnMatpel)
        val btnNilai = findViewById<Button>(R.id.btnNilai)

        btnSiswa.setOnClickListener {
            Intent(this, SiswaActivity::class.java).also {
                startActivity(it)
            }
        }
        btnGuru.setOnClickListener {
            Intent(this, GuruActivity::class.java).also {
                startActivity(it)
            }
        }
        btnMatpel.setOnClickListener {
            Intent(this, MatpelActivity::class.java).also {
                startActivity(it)
            }
        }
        btnNilai.setOnClickListener {
            Intent(this, NilaiActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}