package com.example.covid_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19.Boletim
import kotlinx.android.synthetic.main.layout_list_item.view.*

class ListAdapter(private val items: List<Boletim>, private val clickListener: (Boletim) -> Unit): RecyclerView.Adapter<ListAdapter.VH>()   {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var vh = VH(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)) {
            clickListener(items[it])
        }
        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item = items[position]
        holder.txtDate.text = item.data
        holder.txtConfirmed.text = item.confirmados.toString()
        holder.txtSuspect.text = item.suspeitos.toString()
        holder.txtCured.text = item.curados.toString()
    }

    override fun getItemCount(): Int {
        return items.size;
    }

    class VH(itemView: View, clickAtPosition: (Int) -> Unit): RecyclerView.ViewHolder(itemView) {
        val txtDate: TextView = itemView.txtDate
        val txtSuspect: TextView = itemView.txtSuspect
        val txtConfirmed: TextView = itemView.txtConfirmed
        val txtCured: TextView = itemView.txtCured

        init {
            itemView.setOnClickListener{
                clickAtPosition(adapterPosition)
            }
        }
    }
}