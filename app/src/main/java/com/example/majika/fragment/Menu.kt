package com.example.majika.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.adapter.MenuAdapter
import com.example.majika.database.KeranjangDAO
import com.example.majika.database.KeranjangViewModel
import com.example.majika.database.RoomDataBase
import com.example.majika.retrofit.Retrofit
import com.example.majika.retrofit.data.DataMenu
import com.example.majika.retrofit.endpoint.MenuEndpoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class Menu : Fragment() {


    private  var adapter: MenuAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : SearchView
    private var menuList : ArrayList<DataMenu> = arrayListOf()
    private var recyclerViewState: Parcelable? = null
    private val RECYCLER_VIEW_STATE_KEY = "recycler_view_state"

    private lateinit var db : RoomDataBase
    private lateinit var keranjangDao : KeranjangDAO
    private lateinit var keranjangViewModel : KeranjangViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_KEY)
        }

        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        return view
    }

    // SAVING AND RESTORING STATE
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        recyclerViewState = recyclerView?.layoutManager?.onSaveInstanceState()
        outState.putParcelable(RECYCLER_VIEW_STATE_KEY, recyclerViewState)
        outState.putParcelable("recyclerViewState", recyclerView.layoutManager?.onSaveInstanceState())
//        outState.putParcelableArrayList("menuItems", menuList)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("recyclerViewState")
        }
            val items = savedInstanceState?.getParcelableArrayList<DataMenu>("menuItems")
            if (items != null) {
                menuList = items
                adapter = MenuAdapter(menuList, keranjangViewModel)
                recyclerView.adapter = adapter
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // room database
        db = RoomDataBase.getDatabase(requireContext())
        Log.d("DB CREATED", db.toString())
        keranjangDao = db.keranjangDAO()
        keranjangViewModel = ViewModelProvider(this).get(KeranjangViewModel::class.java)

        // searchView
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

        //RecyclerView
        recyclerView = view.findViewById(R.id.menuRV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //Restore state
        menuList = arrayListOf()
        if (savedInstanceState != null ){
            recyclerViewState = savedInstanceState.getParcelable("recyclerViewState")
            recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_KEY)
            recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)

//            menuList = savedInstanceState.getParcelableArrayList("menuItems") ?: arrayListOf()
//            adapter = MenuRvAdapter(menuList)
//            recyclerView.adapter = adapter
        } else {
            lifecycleScope.launch {
                fetchMenuData()
            }
        }

    }

    private suspend fun fetchMenuData() {
        val menuAPI = Retrofit.getInstance().create(MenuEndpoint::class.java)
        val operation = GlobalScope.async(Dispatchers.Default) {
            val response = menuAPI.getMenu()
            if (response != null) {
                // Checking the result
                Log.d("GetData", response.body().toString())
                val dataMenu = response.body()!!.data
                menuList = dataMenu
            }
        }
        operation.await()

        adapter = MenuAdapter(menuList, keranjangViewModel)
        recyclerView.adapter = adapter
    }


}