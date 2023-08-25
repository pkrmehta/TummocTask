package com.pkdev.tummoctask.model


import com.google.gson.annotations.SerializedName

data class Route(
    val busRouteDetails: BusRouteDetails,
    val destinationLat: Double,
    val destinationLong: Double,
    val destinationTime: List<String>,
    val destinationTitle: String,
    val distance: Double,
    val duration: String,
    val fare: Double,
    val medium: String,
    val rideEstimation: Any,
    val routeName: String,
    val sourceLat: Double,
    val sourceLong: Double,
    val sourceTime: List<String>,
    val sourceTitle: String,
    val trails: List<Trail>
)