package com.example.majika.adapter

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.retrofit.data.DataListMenu
import com.example.majika.retrofit.data.DataMenu
import kotlinx.android.synthetic.main.menu_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class MenuAdapter ( val list:ArrayList<DataMenu>) : RecyclerView.Adapter<MenuAdapter.Holder>(), Filterable{

    private var listFilter = ArrayList<DataMenu>()

    init {
        listFilter = list   // copy list to listFilter
    }

    class Holder(val view : View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.namaMakanan)
        val price = view.findViewById<TextView>(R.id.price)
        val sold = view.findViewById<TextView>(R.id.sold)
        val description = view.findViewById<TextView>(R.id.description)

        val count = view.findViewById<TextView>(R.id.quantity)
        val incButton = view.findViewById<Button>(R.id.incBtn)
        val decButton = view.findViewById<Button>(R.id.decBtn)

    }

    private fun setMenuList(list: ArrayList<DataMenu>) {
        this.listFilter = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.menu_item,parent, false)
        return Holder(layout)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.namaMakanan.text = listFilter?.get(position)?.name
        holder.view.price.text = listFilter?.get(position)?.price.toString()
        holder.view.sold.text = listFilter?.get(position)?.sold.toString()
        holder.view.description.text = listFilter?.get(position)?.description

        // click listener for add button
        holder.incButton.setOnClickListener(View.OnClickListener {
            listFilter?.get(position)?.count = listFilter?.get(position)?.count?.plus(1)!!
            holder.count.text = listFilter?.get(position)?.count.toString()
        })

        // click listener for minus button
        holder.decButton.setOnClickListener(View.OnClickListener {
            if (listFilter?.get(position)?.count!! > 0) {
                listFilter?.get(position)?.count = listFilter?.get(position)?.count?.minus(1)!!
                holder.count.text = listFilter?.get(position)?.count.toString()
            }
        })

    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    fun filterCategory(category : String ){
        val resultList = ArrayList<DataMenu>()
        for (row in list) {
            // if category is All, show all
            if (category == "All") {
                resultList.add(row)
            }
            else if (row.type.lowercase(Locale.ROOT).contains(category.lowercase(Locale.ROOT))) {
                resultList.add(row)
            }
        }
        listFilter = resultList
        notifyDataSetChanged()

    }

    // filter by MenuItem.name
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    listFilter = list
                } else {
                    val resultList = ArrayList<DataMenu>()
                    for (row in list) {
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    listFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listFilter
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFilter = results?.values as ArrayList<DataMenu>
                notifyDataSetChanged()
            }
        }
    }

    fun incrementCount(position: Int){
        listFilter[position].count++
        notifyDataSetChanged()
    }

    fun decrementCount(position: Int){
        if (listFilter[position].count > 0){
            listFilter[position].count--
        }
        notifyDataSetChanged()
    }

    fun getCount(position: Int): Int{
        return listFilter[position].count
    }

}