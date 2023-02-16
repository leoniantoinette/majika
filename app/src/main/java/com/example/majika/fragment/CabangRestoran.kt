package com.example.majika.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.*
import com.example.majika.adapter.CabangAdapter
import com.example.majika.model.CabangRestoranModel
import com.example.majika.retrofit.Retrofit
import com.example.majika.retrofit.endpoint.CabangEndpoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val var1 = "param1"
private const val var2 = "param2"
class CabangRestoran : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var cabangResoranList: ArrayList<CabangRestoranModel>
    lateinit var adapter: CabangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cabang_restoran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        cabangResoranList = arrayListOf<CabangRestoranModel>()
        val cabangAPI = Retrofit.getInstance().create(CabangEndpoint::class.java)
        lifecycleScope.launch {
            val operation = GlobalScope.async(Dispatchers.Default) {
                val cabangData = cabangAPI.getBranch()
                if (cabangData != null) {
                    // Checking the result
                    Log.d("GetData", cabangData.body().toString())
                    val dataCabang = cabangData!!.body()!!.data
                    for (i in dataCabang){
                        val name = i.name
                        val popular_food = i.popular_food
                        val address = i.address
                        val contact_person = i.contact_person
                        val phone_number = i.phone_number
                        val longitude = i.longitude
                        val latitude = i.latitude
                        cabangResoranList.add(
                            CabangRestoranModel(
                                name,
                                popular_food,
                                address,
                                contact_person,
                                phone_number,
                                longitude,
                                latitude
                            )
                        )
                    }
                    cabangResoranList.sortBy { it.name }
                }
            }
            operation.await()
            val layoutManager = LinearLayoutManager(context)
            recyclerView = view.findViewById(R.id.Daftar_CabangRestoran)
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            adapter = CabangAdapter(cabangResoranList)
            recyclerView.adapter = adapter
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CabangRestoran().apply {
                arguments = Bundle().apply {
                    putString(var1, param1)
                    putString(var2, param2)
                }
            }
    }
}