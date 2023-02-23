package com.example.majika.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.model.KeranjangModel
import com.example.majika.database.KeranjangViewModel
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MenuAdapter (private val keranjangList: ArrayList<KeranjangModel>, private val keranjangViewModel: KeranjangViewModel) : RecyclerView.Adapter<MenuAdapter.Holder>(), Filterable{

    private lateinit var listFilter : ArrayList<KeranjangModel>

    init {
        listFilter = keranjangList   // copy list to listFilter
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.menu_item,parent, false)
        return Holder(layout)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val formatter = NumberFormat.getInstance(Locale.getDefault())
        holder.name.text = listFilter.get(position).nama
        holder.price.text = "Rp" + formatter.format(listFilter.get(position).harga.toInt())
        holder.sold.text = formatter.format(listFilter.get(position).terjual) + " Sold"
        holder.description.text = listFilter.get(position).deskripsi
        holder.count.text = listFilter.get(position).jumlah.toString()

        // click listener for add button
        holder.incButton.setOnClickListener(View.OnClickListener {
            listFilter.get(position).jumlah = listFilter.get(position).jumlah.plus(1)
            holder.count.text = listFilter.get(position).jumlah.toString()
            keranjangViewModel.updateJumlahKeranjang(listFilter.get(position).id, listFilter.get(position).jumlah)
            Log.d("INC : ", listFilter.get(position).toString())

        })

        // click listener for minus button
        holder.decButton.setOnClickListener(View.OnClickListener {
            if (listFilter.get(position).jumlah > 0) {
                listFilter.get(position).jumlah = listFilter.get(position).jumlah.minus(1)
                holder.count.text = listFilter.get(position).jumlah.toString()
                keranjangViewModel.updateJumlahKeranjang(listFilter.get(position).id, listFilter.get(position).jumlah)
                Log.d("DEC : ", listFilter.get(position).toString())
            }
        })

    }

    override fun getItemCount(): Int {
        return listFilter.size
    }

    fun filterCategory(category : String ){
        val resultList = ArrayList<KeranjangModel>()
        for (row in keranjangList) {
            // if category is All, show all
            if (category == "All") {
                resultList.add(row)
            }
            else if (row.tipe.lowercase(Locale.ROOT).contains(category.lowercase(Locale.ROOT))) {
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
                    listFilter = keranjangList
                } else {
                    val resultList = ArrayList<KeranjangModel>()
                    for (row in keranjangList) {
                        if (row.nama.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
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
                listFilter = results?.values as ArrayList<KeranjangModel>
                notifyDataSetChanged()
            }
        }
    }

}