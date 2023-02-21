package com.example.majika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.majika.databinding.ActivityMainBinding
import com.example.majika.fragment.CabangRestoran
import com.example.majika.fragment.Keranjang
import com.example.majika.fragment.Menu
import com.example.majika.fragment.Twibbon

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = getIntent();
        val extraData = intent.getStringExtra("fragment");
        if (extraData == "menu") {
            changeFragment(Menu())
        } else {
            changeFragment(Twibbon())
        }

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.twibbon -> changeFragment(Twibbon())
                R.id.cabang -> changeFragment(CabangRestoran())
                R.id.menu -> changeFragment(Menu())
                R.id.keranjang -> changeFragment(Keranjang())
                else -> {
                }
            }
            true
        }
    }
    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }
}