package com.example.majika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.majika.activities.CabangRestoran
import com.example.majika.activities.Twibbon

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mbutton = findViewById<Button>(R.id.button_to_list_cabang_restoran)
        mbutton.setOnClickListener{
            val i = Intent(this, CabangRestoran::class.java)
            startActivity(i)
        }

        val nbutton = findViewById<Button>(R.id.button_to_list_twibbon)
        nbutton.setOnClickListener{
            val i = Intent(this, Twibbon::class.java)
            startActivity(i)
        }
    }
}