package com.ciro.montebravotest.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciro.montebravotest.R

class SectionsAcoesRecyclerAdapter(private var sections : List<String>) : RecyclerView.Adapter<SectionsAcoesRecyclerAdapter.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null
    var index_position : Int = 0;

    inner class ViewHolder( view: View) : RecyclerView.ViewHolder(view) {


        val button : TextView

        init {
           button = view.findViewById(R.id.button_section)
            itemView.setOnClickListener {
                sections.get(adapterPosition)?.let { it1 -> onItemClick?.invoke(it1) }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.section_acoes_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.button.text = sections.get(position)
        holder.button.setOnClickListener {
            index_position = position
            notifyDataSetChanged()
        }
        if(index_position==position){
            holder.button.setBackgroundResource(R.drawable.button_checked);
            holder.button.setTextColor(Color.WHITE)
        }
        else
        {
            holder.button.setBackgroundResource(R.drawable.button_press);
            holder.button.setTextColor(Color.parseColor("#808390"))
        }
    }
    override fun getItemCount(): Int {
      return sections.size
    }

}