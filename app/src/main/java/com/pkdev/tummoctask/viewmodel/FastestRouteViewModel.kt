package com.pkdev.tummoctask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pkdev.tummoctask.model.RouteResponse
import com.pkdev.tummoctask.repo.FastestRouteRepo
import java.io.InputStream

class FastestRouteViewModel: ViewModel() {

    var routeResponse = MutableLiveData<RouteResponse>()

    private var routeRepo = FastestRouteRepo()

    fun getRoutes(jsonFile: InputStream) {
        routeResponse = routeRepo.getRoutes(jsonFile)
    }

}