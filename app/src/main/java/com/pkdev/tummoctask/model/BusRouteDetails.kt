package com.pkdev.tummoctask.model


import com.google.gson.annotations.SerializedName

data class BusRouteDetails(
    val routeDescription: String,
    val routeId: Any,
    val routeName: String,
    val routeNumber: String
)