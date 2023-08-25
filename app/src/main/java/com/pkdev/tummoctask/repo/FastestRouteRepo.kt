package com.pkdev.tummoctask.repo

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.pkdev.tummoctask.model.RouteResponse
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class FastestRouteRepo {

    fun getRoutes(jsonFile: InputStream): MutableLiveData<RouteResponse> {
        val br = BufferedReader(InputStreamReader( jsonFile,"UTF-8"))
        val response: RouteResponse = Gson().fromJson(br, RouteResponse::class.java)
        return MutableLiveData(response)
    }

}