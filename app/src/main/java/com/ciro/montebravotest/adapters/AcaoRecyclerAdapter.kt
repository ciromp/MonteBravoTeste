package com.ciro.montebravotest.adapters

import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ciro.montebravotest.R
import com.ciro.montebravotest.adapters.AcaoRecyclerAdapter.*
import com.ciro.montebravotest.models.Acao
import com.squareup.picasso.Picasso

class AcaoRecyclerAdapter(private var dataSet: List<Acao>?) : RecyclerView.Adapter<ViewHolder>(), Filterable {

    var acaoFilterList : List<Acao>? = dataSet
    var onItemClick: ((Acao) -> Unit)? = null

    inner class ViewHolder( view: View) : RecyclerView.ViewHolder(view) {


        val symbol_txtView : TextView
        val company_name_txtView : TextView
        val br_txtView : TextView
        val price_txtView : TextView
        val variation_txtView : TextView
        val recomendation_txtView : TextView
        val symbol_image : ImageView

        init {
            symbol_txtView = view.findViewById(R.id.symbol_txtView);
            company_name_txtView = view.findViewById(R.id.company_name_txtView);
            br_txtView = view.findViewById(R.id.br_txtView);
            price_txtView = view.findViewById(R.id.price_txtView);
            variation_txtView = view.findViewById(R.id.variation_txtView);
            recomendation_txtView = view.findViewById(R.id.recomendation_txtView);
            symbol_image = view.findViewById(R.id.symbol_image)
            itemView.setOnClickListener {
                acaoFilterList?.get(adapterPosition)?.let { it1 -> onItemClick?.invoke(it1) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.acao_model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.symbol_txtView.text = acaoFilterList?.get(position)?.symbol
        holder.company_name_txtView.text = acaoFilterList?.get(position)?.company_name
        holder.br_txtView.text = "BR"
        holder.price_txtView.text = acaoFilterList?.get(position)?.curent_price.toString().replace(".",",")
        holder.variation_txtView.text = acaoFilterList?.get(position)?.symbol
        if(acaoFilterList?.get(position)?.recomendation.equals("buy")){
            holder.recomendation_txtView.text =  "Compra"
            holder.recomendation_txtView.setBackgroundResource(R.drawable.rounded_corner_green_tag)
        }else if (acaoFilterList?.get(position)?.recomendation.equals("sell")){
            holder.recomendation_txtView.text =  "Venda"
            holder.recomendation_txtView.setBackgroundResource(R.drawable.rounded_corner_red_tag)
        }else
        {
            holder.recomendation_txtView.text =  "Neutro"
            holder.recomendation_txtView.setBackgroundResource(R.drawable.rounded_corner_gray_tag)
        }
        Picasso.get().load(acaoFilterList?.get(position)?.symbol_image_url).into(holder.symbol_image)
    }

    override fun getItemCount(): Int {
        if(acaoFilterList.isNullOrEmpty())
            return 0
        else
            return acaoFilterList!!.size
    }

    fun setResult(newData : List<Acao>){
        this.acaoFilterList = newData
        this.dataSet = newData
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val stringToSearch = constraint.toString()
                if (stringToSearch.isEmpty())
                    acaoFilterList = dataSet
                else {
                    val results = ArrayList<Acao>()
                    for (row in dataSet!!) {
                        if (row.company_name?.lowercase()
                                ?.contains(stringToSearch) == true || row.ref?.lowercase()
                                ?.contains(stringToSearch) == true || row.symbol?.lowercase()
                                ?.contains(stringToSearch) == true || row.recomendation?.lowercase()
                                ?.contains(stringToSearch) == true || row.curent_price.toString()
                                ?.replace(".", ",").lowercase().contains(stringToSearch)
                        ) {
                            results.add(row)
                        }
                    }
                    acaoFilterList = results
                }
                Log.d("lisss",acaoFilterList.toString())
                val filterResults = FilterResults()
                filterResults.values = acaoFilterList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                acaoFilterList = results?.values as MutableList<Acao>
                notifyDataSetChanged()
            }

        }
    }
}