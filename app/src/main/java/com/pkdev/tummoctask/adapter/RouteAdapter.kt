package com.pkdev.tummoctask.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.Guideline
import androidx.recyclerview.widget.RecyclerView
import com.pkdev.tummoctask.ui.MapActivity
import com.pkdev.tummoctask.R
import com.pkdev.tummoctask.databinding.ItemRouteBinding
import com.pkdev.tummoctask.model.RouteResponseItem
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit

class RouteAdapter(private val list: ArrayList<RouteResponseItem>, private val context: Context): RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    class RouteViewHolder(val binding: ItemRouteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        return RouteViewHolder(ItemRouteBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        val item = list[position]
        if(item.routes.size > 4){
            holder.binding.root.visibility = View.GONE
            return
        }

        setGuideLine(item, holder.binding)

        holder.binding.root.setOnClickListener(View.OnClickListener {
            Handler().postDelayed({
                context.startActivity(Intent(context, MapActivity::class.java))
            },100)

        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setGuideLine(item: RouteResponseItem, binding: ItemRouteBinding) {
        val totalTime = getDurationInLong(item.totalDuration)

        binding.estimatedPrice.text = String.format(context.getString(R.string.prefix_rupee,item.totalFare.toString()))
        binding.totalDistance.text = String.format(context.getString(R.string.dist_kms), String.format("%.2f", item.totalDistance))
        binding.totalTime.text = "~ ${getDurationInText(totalTime)}"
        var time: Long = 0
        for(i in item.routes.indices){
            val routeItem = item.routes[i]

            val routeTime = getDurationInLong(routeItem.duration)
            time += routeTime
            val perc: Float = time.toFloat()/totalTime
            getGuideLine(i,binding).setGuidelinePercent(perc)

            getRouteContainer(i, binding).visibility = View.VISIBLE

            when(routeItem.medium){
                "Walk" -> {
                    getColorBarView(i,binding).setBackgroundColor(context.getColor(R.color.color_dark_blue))
                    if(perc < 0.15f){
                        getIconBarView(i,binding).visibility = View.GONE
                    }
                    else
                        getIconBarView(i,binding).visibility = View.VISIBLE
                    getIconBarImageView(i,binding).setImageResource(R.drawable.walk)

                    getRouteDescIcon(i,binding).setImageResource(R.drawable.walk)
                    getRouteDescText(i,binding).apply {
                        text =  getDurationInText(routeTime)
                        setTextColor(context.getColor(R.color.text_color))
                    }

                }
                "Bus" -> {
                    getColorBarView(i,binding).setBackgroundColor(context.getColor(R.color.color_yellow))
                    getIconBarImageView(i,binding).setImageResource(R.drawable.bus)
                    if(perc < 0.15f){
                        getIconBarView(i,binding).visibility = View.GONE
                    }
                    else
                        getIconBarView(i,binding).visibility = View.VISIBLE
                    getRouteDescIcon(i,binding).setImageResource(R.drawable.bus)
                    getRouteDescText(i,binding).apply {
                        text = routeItem.busRouteDetails.routeNumber
                        setTextColor(context.getColor(R.color.color_orange))
                    }
                }
                "Metro" -> {
                    getColorBarView(i,binding).setBackgroundColor(context.getColor(R.color.color_metro))
                    getIconBarImageView(i,binding).setImageResource(R.drawable.metro)
                    if(perc < 0.15f){
                        getIconBarView(i,binding).visibility = View.GONE
                    }
                    else
                        getIconBarView(i,binding).visibility = View.VISIBLE
                    getRouteDescIcon(i,binding).setImageResource(R.drawable.metro)
                    getRouteDescText(i,binding).text = routeItem.duration
                }
            }
        }
    }

    private fun getDurationInText(milliseconds: Long): String {
        val hr = TimeUnit.MILLISECONDS.toHours(milliseconds)
        val min = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        val sec = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        if(hr>0){
            if(min>0){
                return String.format(context.getString(R.string.time_hr_min), hr, min)
            }
            return String.format(context.getString(R.string.time_hr), hr)
        }
        if(min > 0){
            return String.format(context.getString(R.string.time_min), min)
        }
        if(sec > 0)
            return String.format(context.getString(R.string.time_sec), sec)
        return String.format(context.getString(R.string.time_sec), sec)
    }

    private fun getColorBarView(i: Int, binding: ItemRouteBinding): View{
        when(i){
            0 -> return binding.colorBar1
            1 -> return binding.colorBar2
            2 -> return binding.colorBar3
            3 -> return binding.colorBar4
        }
        return binding.colorBar1
    }

    private fun getIconBarImageView(i: Int, binding: ItemRouteBinding): ImageView{
        when(i){
            0 -> return binding.routeIcon1
            1 -> return binding.routeIcon2
            2 -> return binding.routeIcon3
            3 -> return binding.routeIcon4
        }
        return binding.routeIcon1
    }

    private fun getIconBarView(i: Int, binding: ItemRouteBinding): RelativeLayout{
        when(i){
            0 -> return binding.sourceIcon1
            1 -> return binding.sourceIcon2
            2 -> return binding.sourceIcon3
            3 -> return binding.sourceIcon4
        }
        return binding.sourceIcon1
    }

    private fun getGuideLine(i: Int, binding: ItemRouteBinding): Guideline{
        when(i){
            0 -> return binding.guideline1
            1 -> return binding.guideline2
            2 -> return binding.guideline3
            3 -> return binding.guideline4
        }
        return binding.guideline1
    }

    private fun getRouteDescIcon(i: Int, binding: ItemRouteBinding): ImageView{
        when(i){
            0 -> return binding.routeDescIcon0
            1 -> return binding.routeDescIcon1
            2 -> return binding.routeDescIcon2
            3 -> return binding.routeDescIcon3
        }
        return binding.routeDescIcon0
    }

    private fun getRouteDescText(i: Int, binding: ItemRouteBinding): AppCompatTextView{
        when(i){
            0 -> return binding.routeDescText0
            1 -> return binding.routeDescText1
            2 -> return binding.routeDescText2
            3 -> return binding.routeDescText3
        }
        return binding.routeDescText0
    }

    private fun getRouteContainer(i: Int, binding: ItemRouteBinding): LinearLayout{
        when(i){
            0 -> return binding.sourceDescContainer1
            1 -> return binding.sourceDescContainer2
            2 -> return binding.sourceDescContainer3
            3 -> return binding.sourceDescContainer4
        }
        return binding.sourceDescContainer1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDurationInLong(time: String): Long{
        val localTime: LocalTime = LocalTime.parse(time)
        val millis: Int = localTime.toSecondOfDay() * 1000
        return millis.toLong()
    }


    override fun getItemCount(): Int {
        return list.size
    }

}