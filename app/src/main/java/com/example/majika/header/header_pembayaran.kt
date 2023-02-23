package com.example.majika.header

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.majika.MainActivity
import com.example.majika.R

class header_pembayaran : Fragment() {

    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header_pembayaran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // back button
        backButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val i = Intent(activity, MainActivity::class.java)
            i.putExtra("fragment", "keranjang")
            startActivity(i)
        }
    }
}