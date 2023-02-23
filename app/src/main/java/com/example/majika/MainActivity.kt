package com.example.majika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.majika.database.KeranjangViewModel
import com.example.majika.database.ViewModelFactory
import com.example.majika.databinding.ActivityMainBinding
import com.example.majika.fragment.CabangRestoran
import com.example.majika.fragment.Keranjang
import com.example.majika.fragment.Menu
import com.example.majika.fragment.Twibbon
import com.example.majika.header.header_cabang_restoran
import com.example.majika.header.header_keranjang
import com.example.majika.header.header_menu
import com.example.majika.header.header_twibbon

var id_header: Int = 0
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: KeranjangViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = ViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(KeranjangViewModel::class.java)

        val intent = getIntent();
        val extraData = intent.getStringExtra("fragment");
        if (extraData == "keranjang") {
            binding.bottomNavigation.selectedItemId = R.id.keranjang
            changeFragment(Keranjang(), header_keranjang())
        } else  {
            binding.bottomNavigation.selectedItemId = R.id.menu
            changeFragment(Menu(), header_menu())
        }

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.twibbon -> {
                    id_header = 0
                    changeFragment(Twibbon(), header_twibbon())}
                R.id.cabang -> {
                    id_header = 1
                    changeFragment(CabangRestoran(), header_cabang_restoran())}
                R.id.menu -> {
                    id_header = 2
                    changeFragment(Menu(), header_menu())}
                R.id.keranjang -> {
                    id_header=3
                    changeFragment(Keranjang(), header_keranjang())}
                else -> {
                }
            }
            true
        }
    }
    private fun changeFragment(navigation: Fragment, header: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, navigation)
        fragmentTransaction.replace(R.id.layout_header, header)
        fragmentTransaction.commit()
    }
}