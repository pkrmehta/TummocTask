package com.pkdev.tummoctask.model


import com.google.gson.annotations.SerializedName

data class RouteResponseItem(
    val routeTitle: String,
    val routes: List<Route>,
    val totalDistance: Double,
    val totalDuration: String,
    val totalFare: Double
)