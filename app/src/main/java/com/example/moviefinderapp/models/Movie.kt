package com.example.moviefinderapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("poster_path")
    val poster: String?,

    @SerializedName("release_date")
    val release: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("vote_average")
    val vote_average: String?,

    @SerializedName("key")
    val key: String?,

    @SerializedName("adult")
    val adult: String?


) : Parcelable {
    constructor() : this("", "", "", "", "", "", "","")
}