package com.example.majika.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.activities.Pembayaran
import com.example.majika.adapter.KeranjangAdapter
import com.example.majika.database.KeranjangDAO
import com.example.majika.database.KeranjangModel
import com.example.majika.database.RoomDataBase
import com.example.majika.database.KeranjangViewModel

class Keranjang : Fragment() {
    private lateinit var adapter: KeranjangAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var bayarBtn : Button
    private var keranjangList : List<KeranjangModel>? = null
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
        // Inflate the layout for this fragment
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = RoomDataBase.getDatabase(requireContext())
        Log.d("DB CREATED", db.toString())
        keranjangDao = db.keranjangDAO()

        var keranjangModel = KeranjangModel("Nasi Goreng", "10000", "1")

        keranjangViewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)
        keranjangViewModel.addKeranjang(keranjangModel)
        Log.d("DB INSERT", db.toString())

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

}