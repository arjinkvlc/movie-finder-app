package com.example.moviefinderapp.models

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("value") val value: Double
)