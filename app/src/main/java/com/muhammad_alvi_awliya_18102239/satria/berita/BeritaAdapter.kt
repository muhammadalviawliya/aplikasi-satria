package com.muhammad_alvi_awliya_18102239.satria.berita

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.muhammad_alvi_awliya_18102239.satria.R
import kotlinx.android.synthetic.main.adapter_berita.view.*
import kotlin.collections.ArrayList

class BeritaAdapter (var results: ArrayList<BeritaModel>, private val listener: OnAdapterListener):
    RecyclerView.Adapter<BeritaAdapter.ViewHolder>(), Filterable {

    var tableList: MutableList<BeritaModel> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_berita, parent, false)
    )

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.textView.text = result.judul
        Glide.with(holder.view)
                .load(result.gambar)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .centerCrop()
                .into(holder.view.imageView)
        holder.view.setOnClickListener { listener.onClick(result) }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(data: MutableList<BeritaModel>) {
        tableList = data.toMutableList() // makes a copy
        results = data as ArrayList<BeritaModel>
        notifyDataSetChanged()
    }


    interface OnAdapterListener {
        fun onClick(result: BeritaModel)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val queryString = charSequence.toString()

                val filterResults = FilterResults()
                filterResults.values =
                        if (queryString.isEmpty()) {
                            tableList
                        } else {
                            tableList.filter {
                                it.judul.contains(queryString, ignoreCase = true) || it.judul.contains(charSequence)
                            }
                        }
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                results = filterResults.values as ArrayList<BeritaModel>
                notifyDataSetChanged()
            }
        }
    }

}