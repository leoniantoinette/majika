package com.example.majika.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.model.CabangRestoranModel
import com.example.majika.R

class CabangAdapter(private val cabangList: ArrayList<CabangRestoranModel>) :
    RecyclerView.Adapter<CabangAdapter.CabangRestoranViewHolder>() {

    class CabangRestoranViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.textView1)
        val popular_food : TextView = itemView.findViewById(R.id.textView3)
        val address: TextView = itemView.findViewById(R.id.textView2)
        val contact_person : TextView = itemView.findViewById(R.id.textView4)
        val phoneNumber : TextView = itemView.findViewById(R.id.textView5)
        val buttonMaps : ImageButton = itemView.findViewById(R.id.mapButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CabangRestoranViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_cabang_restoran, parent, false)
        return CabangRestoranViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cabangList.size
    }

    override fun onBindViewHolder(holder: CabangRestoranViewHolder, position: Int) {
        val data = cabangList[position]
        holder.name.text = data.name
        holder.popular_food.text = data.popular_food
        holder.address.text = data.address
        holder.contact_person.text = data.contact_person
        holder.phoneNumber.text = data.phone_number
        holder.buttonMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:" + data.longitude +","+data.latitude+"?q="+data.address)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(holder.itemView.context, mapIntent, null)
        }
    }


}

