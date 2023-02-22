package com.example.majika.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.majika.R
import com.example.majika.activities.Pembayaran
import com.example.majika.adapter.KeranjangAdapter
import com.example.majika.database.KeranjangDAO
import com.example.majika.database.KeranjangModel
import com.example.majika.database.KeranjangViewModel
import com.example.majika.database.RoomDataBase
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class Keranjang : Fragment() {
    private lateinit var adapter: KeranjangAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var bayarBtn : Button
    private lateinit var totalTextView : TextView
    private var keranjangList : List<KeranjangModel> = ArrayList()
    private lateinit var db : RoomDataBase
    private lateinit var keranjangDao : KeranjangDAO
    private lateinit var keranjangViewModel : KeranjangViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_keranjang, container, false)
        // fetch data

        // bayar button
        bayarBtn = view.findViewById(R.id.bayarBtn)
        bayarBtn.setOnClickListener {
            val i = Intent(requireActivity(), Pembayaran::class.java)
            startActivity(i)
        }
        totalTextView = view.findViewById(R.id.total)
        // Inflate the layout for this fragment
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = RoomDataBase.getDatabase(requireContext())
        Log.d("DB CREATED", db.toString())
        keranjangDao = db.keranjangDAO()

        keranjangViewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)

        recyclerView = view.findViewById(R.id.rvKeranjang)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        keranjangViewModel.getAllKeranjang().observe(viewLifecycleOwner, {
            keranjangList = it
            adapter = KeranjangAdapter(keranjangList, this, keranjangViewModel)
            recyclerView.adapter = adapter
        })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    public fun updateTotal() {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        keranjangViewModel.getAllKeranjang().observe(viewLifecycleOwner, {
            var total = 0;
            keranjangList = it
            for (i in it) {
                total += i.harga.toInt()
            }
            this.totalTextView.text = "Rp" + formatter.format(total.toInt())
        })
    }
}