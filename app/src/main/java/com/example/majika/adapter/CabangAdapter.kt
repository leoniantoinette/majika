package com.example.majika.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.majika.R
import com.example.majika.model.Data
import kotlinx.android.synthetic.main.adapter_cabang.view.*
import kotlin.collections.ArrayList

class CabangAdapter (var results: ArrayList<Data.Result>, val listener: OnAdapterListener):
    RecyclerView.Adapter<CabangAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.adapter_cabang,
            parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = results[position]
        holder.view.textView.text = data.name

        holder.view.setOnClickListener { listener.onClick( data) }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: List<Data.Result>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(data: Data.Result)
    }
}