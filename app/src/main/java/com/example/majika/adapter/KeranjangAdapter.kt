package com.example.majika.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.model.KeranjangModel
import com.example.majika.database.KeranjangViewModel
import com.example.majika.fragment.Keranjang
import java.text.NumberFormat
import java.util.*

class KeranjangAdapter (private val keranjangList: List<KeranjangModel>, private val fragment: Keranjang, private val keranjangViewModel: KeranjangViewModel) : RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder>() {

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
        val formatter = NumberFormat.getInstance(Locale.getDefault())

        val data = keranjangList[position]
        holder.name.text = data.nama

        holder.price.text = "Rp" + formatter.format(data.harga.toInt())
        holder.count.text = data.jumlah.toString()
        fragment.updateTotal()

        // click listener for add button
        holder.incButton.setOnClickListener(View.OnClickListener {
            keranjangList[position].jumlah = keranjangList[position].jumlah?.plus(1)!!
            holder.count.text = keranjangList[position].jumlah.toString()
            Log.d("ID MODEL", keranjangList[position].toString())
            keranjangViewModel.updateJumlahKeranjang(keranjangList[position].id, keranjangList[position].jumlah)
            fragment.updateTotal()
        })

        // click listener for minus button
        holder.decButton.setOnClickListener(View.OnClickListener {
            if (keranjangList[position].jumlah > 0) {
                keranjangList[position].jumlah = keranjangList[position].jumlah?.minus(1)!!
                keranjangViewModel.updateJumlahKeranjang(keranjangList[position].id, keranjangList[position].jumlah)
                if (keranjangList[position].jumlah == 0) {
                    notifyItemRemoved(position);
                } else {
                    holder.count.text = keranjangList[position].jumlah.toString()
                    fragment.updateTotal()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return keranjangList.size
    }
}
