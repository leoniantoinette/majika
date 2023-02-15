package com.example.majika.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.adapter.CabangAdapter
import com.example.majika.model.Data
import com.example.majika.retrofit.ApiService
import kotlinx.android.synthetic.main.activity_cabang_restoran.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CabangRestoran : AppCompatActivity() {
    private val TAG: String = "MainActivity"
//    private var tasklist: MutableList<Data.Result> = ArrayList()
    private lateinit var cabangAdapter: CabangAdapter
    private var ascending = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cabang_restoran)
        supportActionBar!!.title = "Cabang Restoran"
        setupRecyclerView()
        getDataFromApi()

    }

    private fun setupRecyclerView() {

        cabangAdapter = CabangAdapter(arrayListOf(), object : CabangAdapter.OnAdapterListener {
            override fun onClick(data: Data.Result) {
                val gmmIntentUri = Uri.parse("geo:" + data.longitude +","+data.latitude+"?q="+data.address)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        })



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        Collections.sort(tasklist, Comparator() {
//                lhs, rhs -> lhs.name.compareTo(rhs.name)})
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cabangAdapter

        }



    }

    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data()
            .enqueue(object : Callback<Data> {
                override fun onFailure(call: Call<Data>, t: Throwable) {
                    printLog( t.toString() )
                    showLoading(false)
                }
                override fun onResponse(
                    call: Call<Data>,
                    response: Response<Data>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        showResult( response.body()!! )
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    private fun showResult(results: Data) {
        for (result in results.data) printLog( "title: ${result.name}" )
        cabangAdapter.setData( results.data )
    }
}
