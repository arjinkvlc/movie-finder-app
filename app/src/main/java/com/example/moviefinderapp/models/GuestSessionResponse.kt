package com.example.moviefinderapp.models

import com.google.gson.annotations.SerializedName

data class GuestSessionResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("guest_session_id")
    val guestSessionId: String
    )