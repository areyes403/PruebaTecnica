package com.example.pruebatecnica.core_feature.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("errors")
    val errors: Map<String, List<String>>?,
    @SerializedName("message")
    val message: String? // The given data was invalid.
)