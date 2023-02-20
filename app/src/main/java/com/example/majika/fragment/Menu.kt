package com.example.majika.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.adapter.CabangAdapter
import com.example.majika.adapter.MenuAdapter
import com.example.majika.model.CabangRestoranModel
import com.example.majika.retrofit.Retrofit
import com.example.majika.retrofit.data.DataMenu
import com.example.majika.retrofit.endpoint.MenuEndpoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class Menu : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private lateinit var adapter: MenuAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : SearchView
    private var menuList : ArrayList<DataMenu> = arrayListOf()
    private var recyclerViewState: Parcelable? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        // SearchView
        searchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("searchWord : ", newText.toString())
                adapter?.filter?.filter(newText)
                return false
            }
        })

        //Spinner
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val categories = listOf<String>("All", "Food", "Drink")
        spinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("spinner : ", categories[position])
                adapter?.filterCategory(categories[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        //RecylerView
        recyclerView = view.findViewById(R.id.menuRV)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        //Restore state
        menuList = arrayListOf()
        if (savedInstanceState != null ){
            recyclerViewState = savedInstanceState.getParcelable("recyclerViewState")
            recyclerView?.layoutManager?.onRestoreInstanceState(recyclerViewState)

//            menuList = savedInstanceState.getParcelableArrayList("menuItems") ?: arrayListOf()
//            adapter = MenuRvAdapter(menuList)
//            recyclerView.adapter = adapter
        } else {
            lifecycleScope.launch {
                fetchMenuData()
            }
        }



        // Return View
        return view
    }

    // SAVING AND RESTORING STATE
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("recyclerViewState", recyclerView?.layoutManager?.onSaveInstanceState())
//        outState.putParcelableArrayList("menuItems", menuList)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("recyclerViewState")
        }
//            val items = savedInstanceState.getParcelableArrayList<MenuItem>("menuItems")
//            if (items != null) {
//                menuList = items
//                adapter = MenuRvAdapter(menuList)
//                recyclerView.adapter = adapter
//            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // handle counter button click

    }

    private suspend fun fetchMenuData() {
////        val menuAPI = Retrofit.getInstance().create(MenuEndpoint::class.java)
//        try {
//            val response = menuAPI.getMenu().execute()
//            if (response.isSuccessful) {
//                val menu = response.body()?.data
//                menuList = menu!!
//                adapter = MenuAdapter(menu!!)
//                recyclerView?.adapter = adapter
//            } else {
//                Log.d("error", response.errorBody()?.string() ?: "Unknown error")
//            }
//        } catch (e: IOException) {
//            Log.d("error", e.message.toString())
//        }
        val menuAPI = Retrofit.getInstance().create(MenuEndpoint::class.java)
        val operation = GlobalScope.async(Dispatchers.Default) {
            val response = menuAPI.getMenu()
            if (response != null) {
                // Checking the result
                Log.d("GetData", response.body().toString())
                val dataMenu = response!!.body()!!.data
                menuList = dataMenu
            }
        }
        operation.await()

        adapter = MenuAdapter(menuList)
        recyclerView.adapter = adapter


    }




}