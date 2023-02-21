package com.example.majika.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.database.KeranjangModel
import com.example.majika.retrofit.data.DataKeranjang

class KeranjangAdapter (private val keranjangList: List<KeranjangModel>) : RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder>() {

    class KeranjangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.namaMakanan)
        val price : TextView = itemView.findViewById(R.id.price)

        val count : TextView = itemView.findViewById(R.id.quantity)
        val incButton : Button = itemView.findViewById(R.id.incBtn)
        val decButton : Button = itemView.findViewById(R.id.decBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.keranjang_item, parent, false)
        return KeranjangViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KeranjangViewHolder, position: Int) {
        val data = keranjangList[position]
        holder.name.text = data.nama
        holder.price.text = data.harga.toString()

        // click listener for add button
        holder.incButton.setOnClickListener(View.OnClickListener {
            keranjangList[position].jumlah = keranjangList[position].jumlah?.plus(1)!!
            holder.count.text = keranjangList[position].jumlah.toString()
        })

        // click listener for minus button
//        holder.decButton.setOnClickListener(View.OnClickListener {
//            if (keranjangList[position].count > 0) {
//                keranjangList[position].count = keranjangList[position].count?.minus(1)!!
//                holder.count.text = keranjangList[position].count.toString()
//            }
//        })
    }

    override fun getItemCount(): Int {
        return keranjangList.size
    }
}