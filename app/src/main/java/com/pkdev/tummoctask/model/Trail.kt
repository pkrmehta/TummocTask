package com.pkdev.tummoctask.model


import com.google.gson.annotations.SerializedName

data class Trail(
    val distance: Double,
    val fareStage: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val seq: Int,
    val time: Any
)