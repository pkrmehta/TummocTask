package com.pkdev.tummoctask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.pkdev.tummoctask.ui.MapActivity
import com.pkdev.tummoctask.databinding.ItemMapBottomBinding

class MapAdapter(private val list: ArrayList<String>, private val context: Context,  val activity: FragmentActivity): RecyclerView.Adapter<MapAdapter.MapViewHolder>() {


    class MapViewHolder(val binding: ItemMapBottomBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        return MapViewHolder(ItemMapBottomBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        val item = list[position]
        when(item){
            "source" -> {
                holder.binding.sourceContainer.visibility = View.VISIBLE
                holder.binding.transitContainer.visibility = View.GONE
                holder.binding.destinationContainer.visibility = View.GONE
            }
            "transit" -> {
                holder.binding.sourceContainer.visibility = View.GONE
                holder.binding.transitContainer.visibility = View.VISIBLE
                holder.binding.destinationContainer.visibility = View.GONE
            }
            "destination" ->{
                holder.binding.sourceContainer.visibility = View.GONE
                holder.binding.transitContainer.visibility = View.GONE
                holder.binding.destinationContainer.visibility = View.VISIBLE
            }
        }

        holder.binding.root.setOnClickListener(){
            (activity as MapActivity).onMapItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}