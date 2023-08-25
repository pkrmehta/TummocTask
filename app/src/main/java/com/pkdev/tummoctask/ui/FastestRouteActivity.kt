package com.pkdev.tummoctask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pkdev.tummoctask.R
import com.pkdev.tummoctask.adapter.RouteAdapter
import com.pkdev.tummoctask.databinding.ActivityFastestRouteBinding
import com.pkdev.tummoctask.viewmodel.FastestRouteViewModel

class FastestRouteActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityFastestRouteBinding
    private lateinit var routeViewModel: FastestRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFastestRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        routeViewModel = ViewModelProvider(this)[FastestRouteViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()

        val jsonFile = assets.open("json/response.json")
        routeViewModel.getRoutes(jsonFile)

        binding.routeRv.layoutManager = LinearLayoutManager(this)
        binding.btnBack.setOnClickListener(this)
        setObserver()
    }



    private fun setObserver() {
        routeViewModel.routeResponse.observe(this){response ->
            binding.routeRv.adapter = RouteAdapter(response,this)
        }
    }

    override fun onClick(clickedView: View?) {
        when(clickedView?.id){
            binding.btnBack.id -> {
                onBackPressed()
            }
        }
    }
}