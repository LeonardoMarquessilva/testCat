package com.example.testcat.data.response

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("data")
    val data: List<Data>,
    val status: Int,
    val success: Boolean
)
