package com.example.majika.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.majika.R

class CabangRestoran : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cabang_restoran)

        val cr = findViewById<TextView>(R.id.cr)
        cr.setText("")
    }
}